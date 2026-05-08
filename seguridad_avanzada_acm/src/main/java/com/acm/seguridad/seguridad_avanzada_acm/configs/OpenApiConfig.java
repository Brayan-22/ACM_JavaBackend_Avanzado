package com.acm.seguridad.seguridad_avanzada_acm.configs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class OpenApiConfig {

        @Bean
        @Profile("basic")
        public OpenAPI customOpenAPIBasic() {
                return new OpenAPI()
                                .info(new Info().title("Seguridad Avanzada - Basic Auth API").version("1.0.0"))
                                .addSecurityItem(new SecurityRequirement().addList("basicScheme"))
                                .components(new Components()
                                                .addSecuritySchemes("basicScheme", new SecurityScheme()
                                                                .type(SecurityScheme.Type.HTTP)
                                                                .scheme("basic")));
        }

        @Bean
        @Profile("jwt")
        public OpenAPI customOpenAPIJwt() {
                return new OpenAPI()
                                .info(new Info().title("Seguridad Avanzada - JWT API").version("1.0.0"))
                                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                                .components(new Components()
                                                .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                                                .type(SecurityScheme.Type.HTTP)
                                                                .scheme("bearer")
                                                                .bearerFormat("JWT")));
        }

        @Bean
        @Profile({ "openid", "sso" })
        public OpenAPI customOpenAPIOAuth2() {
                return new OpenAPI()
                                .info(new Info().title("Seguridad Avanzada - OAuth2/OpenID API").version("1.0.0")
                                                .description("Nota: La autenticacion OpenID es gestionada por Spring Security mediante redireccion del navegador."));
        }

        @Bean
        @Profile("2fa")
        public OpenAPI customOpenAPITwoFactor() {
                return new OpenAPI()
                                .info(new Info().title("Seguridad Avanzada - 2FA API").version("1.0.0")
                                                .description("Nota: Este mEtodo utiliza Form Login localmente. Despues de verificar el 2FA e iniciar sesion, el navegador mantiene la cookie de sesion."));
        }
}
