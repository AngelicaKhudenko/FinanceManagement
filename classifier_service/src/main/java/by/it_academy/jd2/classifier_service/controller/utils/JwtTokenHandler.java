package by.it_academy.jd2.classifier_service.controller.utils;

import by.it_academy.jd2.classifier_service.config.properties.JWTProperty;
import by.it_academy.jd2.classifier_service.core.exceptions.NoTokenException;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenHandler {

    private final JWTProperty property;

    public JwtTokenHandler(JWTProperty property) {
        this.property = property;
    }


    public String getUUID(String token) {

        Claims claims = Jwts.parser()
                .setSigningKey(property.getSecret())
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public Date getExpirationDate(String token) {

        Claims claims = Jwts.parser()
                .setSigningKey(property.getSecret())
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration();
    }

    public String getRole(String token) {

        Claims claims = Jwts.parser()
                .setSigningKey(property.getSecret())
                .parseClaimsJws(token)
                .getBody();

        return (String) claims.get("role");
    }

    public boolean validate(String token) {

        if (token == null || token.isBlank() || token.isEmpty()) {
            throw new NoTokenException();
        }

        Jwts.parser().setSigningKey(property.getSecret()).parseClaimsJws(token);
        return true;
    }
}
