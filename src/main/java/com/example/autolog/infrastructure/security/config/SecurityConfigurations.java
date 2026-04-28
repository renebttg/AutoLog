package com.example.autolog.infrastructure.security.config;

import com.example.autolog.infrastructure.security.entrypoint.CustomAuthenticationEntryPoint;
import com.example.autolog.infrastructure.security.filter.SecurityFilter;
import com.example.autolog.presentation.handler.CustomAccessDeniedHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 * @author Rene
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfigurations {

    private final SecurityFilter securityFilter;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize

                        // Public frontend/static routes
                        .requestMatchers(
                                HttpMethod.GET,
                                "/",
                                "/index.html",
                                "/favicon.ico",
                                "/manifest.json",
                                "/robots.txt",
                                "/static/**",
                                "/assets/**"
                        ).permitAll()

                        // Public auth routes
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/workshops").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/forgot-password").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/reset-password").permitAll()
                        .requestMatchers(HttpMethod.GET, "/auth/reset-password").permitAll()

                        // Public utility routes
                        .requestMatchers(HttpMethod.GET, "/buscar-endereco/{cep}").permitAll()

                        // Swagger/OpenAPI routes
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        // User management
                        .requestMatchers(HttpMethod.POST, "/users").hasAnyRole("OWNER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/users").hasAnyRole("OWNER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/users/{id}").hasAnyRole("OWNER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/users/{id}").hasAnyRole("OWNER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/users/{id}").hasRole("OWNER")

                        // Customers
                        .requestMatchers(HttpMethod.POST, "/customers").hasAnyRole("OWNER", "ADMIN", "EMPLOYEE")
                        .requestMatchers(HttpMethod.GET, "/customers").hasAnyRole("OWNER", "ADMIN", "EMPLOYEE")
                        .requestMatchers(HttpMethod.GET, "/customers/{id}").hasAnyRole("OWNER", "ADMIN", "EMPLOYEE")
                        .requestMatchers(HttpMethod.PUT, "/customers/{id}").hasAnyRole("OWNER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/customers/{id}").hasAnyRole("OWNER", "ADMIN")

                        // Vehicles
                        .requestMatchers(HttpMethod.POST, "/vehicles").hasAnyRole("OWNER", "ADMIN", "EMPLOYEE")
                        .requestMatchers(HttpMethod.GET, "/vehicles").hasAnyRole("OWNER", "ADMIN", "EMPLOYEE")
                        .requestMatchers(HttpMethod.GET, "/vehicles/{id}").hasAnyRole("OWNER", "ADMIN", "EMPLOYEE")
                        .requestMatchers(HttpMethod.PUT, "/vehicles/{id}").hasAnyRole("OWNER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/vehicles/{id}").hasAnyRole("OWNER", "ADMIN")

                        // Maintenances
                        .requestMatchers(HttpMethod.POST, "/maintenances").hasAnyRole("OWNER", "ADMIN", "EMPLOYEE")
                        .requestMatchers(HttpMethod.GET, "/maintenances").hasAnyRole("OWNER", "ADMIN", "EMPLOYEE")
                        .requestMatchers(HttpMethod.GET, "/maintenances/{id}").hasAnyRole("OWNER", "ADMIN", "EMPLOYEE")
                        .requestMatchers(HttpMethod.PUT, "/maintenances/{id}").hasAnyRole("OWNER", "ADMIN", "EMPLOYEE")
                        .requestMatchers(HttpMethod.DELETE, "/maintenances/{id}").hasAnyRole("OWNER", "ADMIN")

                        // Parts
                        .requestMatchers(HttpMethod.POST, "/parts").hasAnyRole("OWNER", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/parts").hasAnyRole("OWNER", "ADMIN", "EMPLOYEE")
                        .requestMatchers(HttpMethod.GET, "/parts/{id}").hasAnyRole("OWNER", "ADMIN", "EMPLOYEE")
                        .requestMatchers(HttpMethod.PUT, "/parts/{id}").hasAnyRole("OWNER", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/parts/{id}").hasAnyRole("OWNER", "ADMIN")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of(
                "http://localhost:3000",
                "http://localhost:4200",
                "http://localhost:5173"
        ));

        configuration.setAllowedMethods(List.of(
                "GET",
                "POST",
                "PUT",
                "PATCH",
                "DELETE",
                "OPTIONS"
        ));

        configuration.setAllowedHeaders(List.of(
                "Authorization",
                "Content-Type",
                "Accept",
                "Origin"
        ));

        configuration.setExposedHeaders(List.of(
                "Authorization"
        ));

        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}
