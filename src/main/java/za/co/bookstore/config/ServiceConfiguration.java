package za.co.bookstore.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import java.text.SimpleDateFormat;

@Configuration
@EnableWebSecurity
public class ServiceConfiguration {
    private static final String[] AUTH_WHITELIST = { "/", "/v2/api-docs","/v3/api-docs/**", "/swagger-resources", "/swagger-resources/**",
            "/configuration/ui", "/configuration/security", "/swagger-ui.html","/swagger-ui/**", "/api/v1/swagger.json",
            "/h2-console/**", "/actuator/**" };
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Disable CSRF protection
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/api/v1/books").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/books/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/books/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/books/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/v1/books").permitAll()
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        .anyRequest().permitAll()
                );
        return http.build();
    }

}
