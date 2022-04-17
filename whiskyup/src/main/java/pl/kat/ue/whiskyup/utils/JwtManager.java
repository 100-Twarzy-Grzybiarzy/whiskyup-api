package pl.kat.ue.whiskyup.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Map;

@Component
public class JwtManager {

    private final SecretKey jwtSecretKey;

    public JwtManager(@Value("${jwt.secret.key}") String jwtSecretKey) {
        this.jwtSecretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(jwtSecretKey));
    }

    public String buildJwt(String value) {
        return Jwts.builder()
                .setClaims(Map.of("cursor", value))
                .signWith(jwtSecretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public String parseJwt(String jwtToken) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecretKey)
                .build()
                .parseClaimsJws(jwtToken)
                .getBody()
                .get("cursor", String.class);
    }
}