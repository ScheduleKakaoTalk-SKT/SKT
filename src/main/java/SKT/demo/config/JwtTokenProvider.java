package SKT.demo.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secret.key}")
    private String secretKey;
    private final Long accessTokenValidTime = 1000L * 60 * 60 * 6;
    private final Long refreshTokenValidTime = 2 * 24 * 60 * 60 * 1000L;
    private static final String AUTHORITIES_KEY = "Authorization";
    private static final String BEARER_TYPE = "Bearer ";
    private final UserDetailsService userDetailsService;

    public String generateAccessToken(Authentication authentication) {
        Claims claims = Jwts.claims().setSubject(authentication.getName());

        Date now = new Date();
        Date expiresIn = new Date(now.getTime() + accessTokenValidTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiresIn)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String generateRefreshToken(Authentication authentication) {
        Claims claims = Jwts.claims().setSubject(authentication.getName());

        Date now = new Date();
        Date expiresIn = new Date(now.getTime() + refreshTokenValidTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiresIn)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader(AUTHORITIES_KEY);
        if (bearerToken != null && bearerToken.startsWith(BEARER_TYPE)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public Boolean validateAccessToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            throw new RuntimeException();
            //TODO throw new MemberException(INVALID_TOKEN);
        }
    }
    public Authentication getAuthenticationByAccessToken(String access_token) {
        String userPrincipal = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(access_token).getBody().getSubject();
        UserDetails userDetails = userDetailsService.loadUserByUsername(userPrincipal);

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getPayload(String token) {
        String[] tokenParts = token.split("\\.");
        if (tokenParts.length == 3) {
            String encodedPayload = tokenParts[1];
            byte[] decodedBytes = Base64.getUrlDecoder().decode(encodedPayload);
            return new String(decodedBytes);
        } else {
            throw new RuntimeException();
      //TODO      throw new UserException(INVALID_TOKEN);
        }
    }

    public boolean validateRefreshToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            throw new RuntimeException();
            //TODO throw new UserException(INVALID_TOKEN);
        }
    }

    public Long getExpiration(String token) {
        // accessToken 남은 유효시간
        Date expiration = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getExpiration();
        // 현재 시간
        Long now = new Date().getTime();
        return (expiration.getTime() - now);
    }
}
