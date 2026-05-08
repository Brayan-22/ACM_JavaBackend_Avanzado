package com.acm.seguridad.seguridad_avanzada_acm.controllers;

import com.acm.seguridad.seguridad_avanzada_acm.models.Role;
import com.acm.seguridad.seguridad_avanzada_acm.models.User;
import com.acm.seguridad.seguridad_avanzada_acm.payload.JwtResponse;
import com.acm.seguridad.seguridad_avanzada_acm.payload.LoginRequest;
import com.acm.seguridad.seguridad_avanzada_acm.payload.MessageResponse;
import com.acm.seguridad.seguridad_avanzada_acm.payload.SignupRequest;
import com.acm.seguridad.seguridad_avanzada_acm.repositories.RoleRepository;
import com.acm.seguridad.seguridad_avanzada_acm.repositories.UserRepository;
import com.acm.seguridad.seguridad_avanzada_acm.security.JwtUtils;
import dev.samstevens.totp.code.CodeVerifier;
import dev.samstevens.totp.code.DefaultCodeGenerator;
import dev.samstevens.totp.code.DefaultCodeVerifier;
import dev.samstevens.totp.code.HashingAlgorithm;
import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.qr.QrGenerator;
import dev.samstevens.totp.qr.ZxingPngQrGenerator;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.secret.SecretGenerator;
import dev.samstevens.totp.time.SystemTimeProvider;
import dev.samstevens.totp.time.TimeProvider;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Qualifier;
import static dev.samstevens.totp.util.Utils.getDataUriForImage;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired(required = false)
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired(required = false)
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        if (authenticationManager == null || jwtUtils == null) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("JWT feature not available in current profile."));
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        return ResponseEntity.ok(new JwtResponse(jwt, loginRequest.getUsername()));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName("USER")
                    .orElseGet(() -> roleRepository.save(new Role("USER")));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                Role currentRole = roleRepository.findByName(role)
                        .orElseGet(() -> roleRepository.save(new Role(role)));
                roles.add(currentRole);
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/2fa/generate")
    public ResponseEntity<?> generate2faSecret(@RequestParam String username) throws QrGenerationException {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("User not found"));
        }

        SecretGenerator secretGenerator = new DefaultSecretGenerator();
        String secret = secretGenerator.generate();

        user.setSecret2fa(secret);
        userRepository.save(user);

        QrData data = new QrData.Builder()
                .label(user.getEmail())
                .secret(secret)
                .issuer("AvanzadaACM")
                .algorithm(HashingAlgorithm.SHA1)
                .digits(6)
                .period(30)
                .build();

        QrGenerator generator = new ZxingPngQrGenerator();
        byte[] imageData = generator.generate(data);
        String mimeType = generator.getImageMimeType();

        return ResponseEntity.ok(getDataUriForImage(imageData, mimeType));
    }

    @PostMapping("/2fa/verify")
    public ResponseEntity<?> verify2fa(@RequestParam String username, @RequestParam String code) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null || user.getSecret2fa() == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("User or 2FA secret not found"));
        }

        TimeProvider timeProvider = new SystemTimeProvider();
        DefaultCodeGenerator codeGenerator = new DefaultCodeGenerator();
        CodeVerifier verifier = new DefaultCodeVerifier(codeGenerator, timeProvider);

        boolean successful = verifier.isValidCode(user.getSecret2fa(), code);

        if (successful) {
            user.set2faEnabled(successful);
            userRepository.save(user);
            return ResponseEntity.ok(new MessageResponse("2FA Verification Successful"));
        } else {
            return ResponseEntity.badRequest().body(new MessageResponse("Invalid 2FA Code"));
        }
    }
}
