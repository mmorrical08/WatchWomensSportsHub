package learn.wwsh.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import learn.wwsh.models.AppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@Component
public class JwtConverter {

    // 1. Signing key
    private final SecretKey key = Jwts.SIG.HS256.key().build();
    // 2. "Configurable" constants
    private final String ISSUER = "wwsh";
    private final int EXPIRATION_MINUTES = 15;
    private final int EXPIRATION_MILLIS = EXPIRATION_MINUTES * 60 * 1000;

    public String getTokenFromUser(AppUser user) {

        List<String> authorities = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return Jwts.builder()
                .issuer(ISSUER)
                .subject(user.getUsername())
                .claim("appUserId", user.getAppUserId())
                .claim("authorities", authorities)
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_MILLIS))
                .signWith(key)
                .compact();
    }

    public AppUser getUserFromToken(String token) {

        if (token == null || !token.startsWith("Bearer ")) {
            return null;
        }

        try {
            Jws<Claims> jws = Jwts.parser()
                    .requireIssuer(ISSUER)
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token.substring(7));

            int appUserId = jws.getPayload().get("appUserId", Integer.class);
            String username = jws.getPayload().getSubject();
            List<String> authorities = (List<String>) jws.getPayload().get("authorities", List.class);

            return new AppUser(appUserId, username, "", true, authorities);
        } catch (JwtException e) {
            // 5. JWT failures are modeled as exceptions.
            System.out.println(e);
        }

        return null;
    }
}