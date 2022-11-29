package kg.peaksoft.bilingualb6.config.security.jwt;

import kg.peaksoft.bilingualb6.entites.AuthInfo;
import kg.peaksoft.bilingualb6.repository.AuthInfoRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class JwtTokenVerifier extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    private final AuthInfoRepository authInfoRepository;

    public static final String AUTHORIZATION = "Authorization";

    public static final String BEARER = "Bearer ";

    public JwtTokenVerifier(JwtUtils jwtUtils, AuthInfoRepository authInfoRepository) {
        this.jwtUtils = jwtUtils;
        this.authInfoRepository = authInfoRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        Optional<String> optionalJwt = getJwtFromRequest(request);

        optionalJwt.ifPresent(token -> {
            String email = jwtUtils.validateJWTToken(token);

            AuthInfo authInfo = authInfoRepository.findByEmail(email)
                    .orElseThrow(
                            () -> new UsernameNotFoundException(
                                    String.format("Please write existing email!")));

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    authInfo, null, authInfo.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        });

        filterChain.doFilter(request, response);
    }

    private Optional<String> getJwtFromRequest(HttpServletRequest request) {

        String bearerToken = request.getHeader(AUTHORIZATION);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER)) {
            return Optional.of(bearerToken.substring(BEARER.length()));
        }

        return Optional.empty();
    }
}