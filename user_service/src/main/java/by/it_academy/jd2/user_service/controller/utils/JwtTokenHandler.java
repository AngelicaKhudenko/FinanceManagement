package by.it_academy.jd2.user_service.controller.utils;

import by.it_academy.jd2.user_service.config.properties.JWTProperty;
import by.it_academy.jd2.user_service.core.enums.EUserRole;
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
        return generateAccessToken(user.getFio(),user.getUuid(),user.getRole());
    }

    public String generateAccessToken(String name, UUID uuid, EUserRole role) {

        return Jwts.builder()
                .setSubject(name)
                .setIssuer(property.getIssuer())
                .setIssuedAt(new Date())
                .claim("role", role.name())
                .claim("uuid",uuid.toString())
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7)))
                .signWith(SignatureAlgorithm.HS512, property.getSecret())
                .compact();
    }

    public String getName(String token) {

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

    public String getUUID(String token) {

        Claims claims = Jwts.parser()
                .setSigningKey(property.getSecret())
                .parseClaimsJws(token)
                .getBody();

        return (String) claims.get("uuid");
    }


    public boolean validate(String token) {

        try {
            Jwts.parser().setSigningKey(property.getSecret()).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            //logger.error("Invalid JWT signature - {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            //logger.error("Invalid JWT token - {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            //logger.error("Expired JWT token - {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            //logger.error("Unsupported JWT token - {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            //logger.error("JWT claims string is empty - {}", ex.getMessage());
        }

        return false;
    }
}
