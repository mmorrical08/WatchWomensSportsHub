package learn.wwsh.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@ConditionalOnWebApplication
@Configuration
public class SecurityConfig {

    private final JwtConverter converter;

    public SecurityConfig(JwtConverter converter) {
        this.converter = converter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationConfiguration config) throws Exception {
        // Disable CSRF as we're not using HTML forms
        http.csrf(AbstractHttpConfigurer::disable);

        // Allow CORS related requests
        http.cors(Customizer.withDefaults());

        // Configure authorization rules
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/authenticate").permitAll()
                .requestMatchers("/refresh_token").authenticated()
                .requestMatchers("/create_account").permitAll()
                .requestMatchers(HttpMethod.GET, "/sport", "/sport/*").permitAll()
                .requestMatchers(HttpMethod.GET, "/team", "/team/*",
                        "/athlete", "/athlete/*"
                        ).permitAll()
                .requestMatchers(HttpMethod.GET, "/api/favorites/*").hasAnyAuthority( "USER")
                .requestMatchers(HttpMethod.POST, "/api/favorites/*").hasAnyAuthority("USER")
                .requestMatchers(HttpMethod.DELETE, "/api/favorites/*").hasAnyAuthority("USER")
                .requestMatchers(HttpMethod.POST, "/athlete/add",
                        "/team/add").hasAnyAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/team/*", "/athlete/*").hasAnyAuthority( "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/team/*", "/athlete/*").hasAnyAuthority("ADMIN")
                .requestMatchers("/**").denyAll()
        );

        // Add JWT filter
        http.addFilter(new JwtRequestFilter(manager(config), converter));

        // Configure session management to be stateless
        http.sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    public AuthenticationManager manager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}