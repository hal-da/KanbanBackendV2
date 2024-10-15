package at.technikum.springrestbackend.security;

import at.technikum.springrestbackend.model.Role;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JwtToPrincipalConverter {
    public UserPrincipal convert(DecodedJWT decodedJWT) {
        return UserPrincipal.builder()
                .userId(decodedJWT.getSubject())
                .email(decodedJWT.getClaim("email").asString())
                .role(Role.valueOf(decodedJWT.getClaim("role").asString()))
                .authorities(extractRoles(decodedJWT))
                .build();
    }

    private List<SimpleGrantedAuthority> extractRoles(DecodedJWT decodedJWT) {
        var claim = decodedJWT.getClaim("roles");
        if(claim.isNull() || claim.isMissing()) {
            return List.of();
        }

        return claim.asList(SimpleGrantedAuthority.class);
    }
}