package com.calculator.backendjava.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.http.HttpStatus;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final ClientRegistrationRepository clientRegistrationRepository;

    public SecurityConfig(ClientRegistrationRepository clientRegistrationRepository) {
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(AbstractHttpConfigurer::disable)

            .exceptionHandling(ex -> ex
                .authenticationEntryPoint((req, res, authException) -> {
                    res.setStatus(HttpStatus.UNAUTHORIZED.value());
                })
            )

            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/user/me").authenticated()
                .anyRequest().permitAll()
            )
                .oauth2Login(oauth2 -> {
                    oauth2
                            .authorizationEndpoint(auth -> {
                                OAuth2AuthorizationRequestResolver defaultResolver =
                                        new DefaultOAuth2AuthorizationRequestResolver(
                                                clientRegistrationRepository, "/oauth2/authorization"
                                        );

                                OAuth2AuthorizationRequestResolver customResolver =
                                        new OAuth2AuthorizationRequestResolver() {
                                            @Override
                                            public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
                                                OAuth2AuthorizationRequest req = defaultResolver.resolve(request);
                                                return customize(req);
                                            }

                                            @Override
                                            public OAuth2AuthorizationRequest resolve(HttpServletRequest request, String clientRegistrationId) {
                                                OAuth2AuthorizationRequest req = defaultResolver.resolve(request, clientRegistrationId);
                                                return customize(req);
                                            }

                                            private OAuth2AuthorizationRequest customize(OAuth2AuthorizationRequest req) {
                                                if (req == null) return null;
                                                return OAuth2AuthorizationRequest.from(req)
                                                        .additionalParameters(params -> {
                                                            params.put("prompt", "consent");
                                                            params.put("access_type", "offline");
                                                        })
                                                        .build();
                                            }
                                        };

                                auth.authorizationRequestResolver(customResolver);
                            })
                            .defaultSuccessUrl("http://localhost:4200/profile", true);
                })
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessHandler((req, res, auth) -> {
                    res.setStatus(200);
                })
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            );
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4200"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}