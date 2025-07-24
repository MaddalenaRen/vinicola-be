package it.epicode.vinicola_be.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
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

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public JwtFilter jwtFilter(JwtTool jwtTool) {
        return new JwtFilter(jwtTool);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/public/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/user/**").hasAnyRole("OPERATORE", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/clienti").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/clienti/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/clienti").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/clienti/**").hasAnyRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/ordini").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/ordini/**").hasAnyRole("ADMIN", "OPERATORE")
                        .requestMatchers(HttpMethod.GET, "/ordini").hasAnyRole("ADMIN", "OPERATORE")
                        .requestMatchers(HttpMethod.GET, "/odini/**").hasAnyRole("ADMIN", "OPERATORE")

                        .requestMatchers(HttpMethod.POST, "/lotti-vino").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/lotti-vino/**").hasAnyRole("ADMIN", "OPERATORE")
                        .requestMatchers(HttpMethod.GET, "/lotti-vino").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/lotti-vino/**").hasAnyRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/etichette").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/etichette/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/etichette").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/etichette/**").hasAnyRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/operatori").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/operatori/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/operatori").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/operatori/**").hasAnyRole("ADMIN")




                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:5173", "https://vinicola-mayuabnmj-maddalena-rennellas-projects.vercel.app"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }
}
