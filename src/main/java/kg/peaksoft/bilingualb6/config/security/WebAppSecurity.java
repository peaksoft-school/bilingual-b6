package kg.peaksoft.bilingualb6.config.security;

import kg.peaksoft.bilingualb6.config.security.jwt.JwtTokenVerifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class WebAppSecurity {

    private final JwtTokenVerifier jwtTokenVerifier;

    public WebAppSecurity(JwtTokenVerifier jwtTokenVerifier) {
        this.jwtTokenVerifier = jwtTokenVerifier;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors()
                .and()
                .csrf().disable()
                .authorizeHttpRequests(
                        (auth) -> auth.antMatchers("/swagger",
                                "/v3/api-docs/**",
                                "/swagger-ui/index.html").permitAll()
                                .anyRequest().permitAll());
        httpSecurity.addFilterBefore(jwtTokenVerifier, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}