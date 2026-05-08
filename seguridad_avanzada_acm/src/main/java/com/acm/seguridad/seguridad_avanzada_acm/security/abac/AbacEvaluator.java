package com.acm.seguridad.seguridad_avanzada_acm.security.abac;

import com.acm.seguridad.seguridad_avanzada_acm.models.Product;
import com.acm.seguridad.seguridad_avanzada_acm.models.User;
import com.acm.seguridad.seguridad_avanzada_acm.repositories.ProductRepository;
import com.acm.seguridad.seguridad_avanzada_acm.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

@Component("abacEvaluator")
public class AbacEvaluator {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public boolean isOwner(Authentication authentication, Long productId) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        String currentUsername = getUsernameFromAuth(authentication);

        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return false; // If doesn't exist, we can't be owner. Usually 404 handles this independently.
        }

        return currentUsername.equals(product.getOwnerUsername());
    }

    public boolean isInDepartment(Authentication authentication, String requiredDepartment) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        String currentUsername = getUsernameFromAuth(authentication);
        User user = userRepository.findByUsername(currentUsername).orElse(null);

        if (user == null) {
            return false;
        }

        return requiredDepartment.equalsIgnoreCase(user.getDepartment());
    }

    private String getUsernameFromAuth(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else if (principal instanceof DefaultOidcUser) {
            return ((DefaultOidcUser) principal).getPreferredUsername();
        } else {
            return principal.toString(); // For basic authentication Strings or other principals
        }
    }
}
