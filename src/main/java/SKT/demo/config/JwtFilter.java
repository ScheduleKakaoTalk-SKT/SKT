package SKT.demo.config;

import SKT.demo.exception.error.UserException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static SKT.demo.exception.message.UserErrorMessage.INVALID_TOKEN;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProvider.resolveToken(request);
        try {
            if (token != null && jwtTokenProvider.validateAccessToken(token)) {
                Authentication auth = jwtTokenProvider.getAuthenticationByAccessToken(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (UserException e) {
            SecurityContextHolder.clearContext();
            throw new UserException(INVALID_TOKEN);
        }

        filterChain.doFilter(request, response);
    }
}
