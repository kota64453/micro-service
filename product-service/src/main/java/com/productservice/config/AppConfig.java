package com.productservice.config;

import com.productservice.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;
@Configuration
@RequiredArgsConstructor
public class AppConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of(
                "http://localhost:3000",   // React
                "http://localhost:5173"    // Vite
        ));

        configuration.setAllowedMethods(List.of(
                "GET",
                "POST",
                "PUT",
                "DELETE",
                "OPTIONS"
        ));

        configuration.setAllowedHeaders(List.of("*"));

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {

        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers(HttpMethod.GET,"/api/products/**").hasAnyRole("ADMIN","CUSTOMER")
                            .requestMatchers(HttpMethod.PATCH,"/api/products/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.POST,"/api/products/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.PUT,"/api/products/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.DELETE,"/api/products/**").hasRole("ADMIN")
                            .anyRequest().authenticated();
                });

        return httpSecurity.build();
    }


    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
