package pl.kat.ue.whiskyup.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class JwtManager {

    private final String jwtSecretKey;

    public JwtManager(@Value("${jwt.secret.key:}") String jwtSecretKey) {
        this.jwtSecretKey = jwtSecretKey;
    }

    public String buildJwt(String value) {
        return Jwts.builder()
                .setClaims(Map.of("cursor", value))
                .signWith(SignatureAlgorithm.HS512, TextCodec.BASE64.encode(jwtSecretKey))
                .compact();
    }

    public String parseJwt(String jwtToken) {
        return Jwts.parser()
                .setSigningKey(TextCodec.BASE64.encode(jwtSecretKey))
                .parseClaimsJws(jwtToken)
                .getBody()
                .get("cursor", String.class);
    }
}