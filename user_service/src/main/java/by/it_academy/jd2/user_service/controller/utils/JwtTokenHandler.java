package by.it_academy.jd2.user_service.controller.utils;

import by.it_academy.jd2.user_service.config.properties.JWTProperty;
import by.it_academy.jd2.user_service.core.enums.EUserRole;
import by.it_academy.jd2.user_service.core.exceptions.NoTokenException;
import by.it_academy.jd2.user_service.model.UserEntity;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class JwtTokenHandler {

    private final JWTProperty property;

    public JwtTokenHandler(JWTProperty property) {
        this.property = property;
    }

    public String generateAccessToken(UserEntity user) {

        return generateAccessToken(user.getUuid(),user.getRole());
    }

    public String generateAccessToken(UUID uuid, EUserRole role) {

        return Jwts.builder()
                .setSubject(uuid.toString())
                .setIssuer(property.getIssuer())
                .setIssuedAt(new Date())
                .claim("role", role.name())
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7)))
                .signWith(SignatureAlgorithm.HS512, property.getSecret())
                .compact();
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
