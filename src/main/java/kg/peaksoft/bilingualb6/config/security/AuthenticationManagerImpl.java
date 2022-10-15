package kg.peaksoft.bilingualb6.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AuthenticationManagerImpl implements AuthenticationManager {

    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());

        if (!passwordEncoder.matches(authentication.getCredentials().toString(), userDetails.getPassword())) {
            throw new BadCredentialsException("bad credentials");
        }

        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
    }
}