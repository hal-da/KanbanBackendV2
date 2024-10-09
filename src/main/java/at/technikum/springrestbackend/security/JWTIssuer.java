package at.technikum.springrestbackend.security;

import at.technikum.springrestbackend.model.Role;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@RequiredArgsConstructor
@Component
public class JWTIssuer {

    private final JwtProperties jwtProperties;

    public String issueToken(String id, String email, Role role) {
        return JWT.create()
                .withSubject(id)
                .withExpiresAt(Instant.now().plus(Duration.of(1, ChronoUnit.DAYS)))
                .withClaim("email", email)
                .withClaim("role", role.toString())
                .sign(Algorithm.HMAC256(jwtProperties.getSecretKey()));
    }
}
