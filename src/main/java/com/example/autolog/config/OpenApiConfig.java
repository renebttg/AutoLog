package com.example.autolog.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

/**
 * @author Rene
 */

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Rene",
                        email = "renebattaglia@hotmail.com"

                ),
                description = "AutoLog Api documentation",
                title = "AutoLog Api",
                version = "1.0",
                license = @License(
                        name = "APACHE LICENSE, VERSION 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0"
                ),
                termsOfService = "Terms of service"
        ),
        servers = {
               @Server(
                       description = "Local ENV",
                       url = "http://localhost:8080"
               )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT Token Service",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
