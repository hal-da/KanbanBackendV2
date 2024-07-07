package at.technikum.springrestbackend.security;

import com.auth0.jwt.algorithms.Algorithm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("security.jwt")
public class JwtProperties {
    private String secretKey;

    public Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secretKey);
    }
}
