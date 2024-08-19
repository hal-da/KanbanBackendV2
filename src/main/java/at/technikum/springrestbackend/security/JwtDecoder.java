package at.technikum.springrestbackend.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtDecoder {

    private final JwtProperties jwtProperties;

    public DecodedJWT decodeToken(String token) {
        return JWT.require(jwtProperties.getAlgorithm())
                .build()
                .verify(token);
    }
}
