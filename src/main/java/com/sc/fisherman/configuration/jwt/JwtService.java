package com.sc.fisherman.configuration.jwt;

import com.sc.fisherman.model.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    private final String secretKey = "fishermanfishermanfishermanfishr";

    public String generateToken(UserEntity user) {
        return Jwts.builder()
                .setSubject(user.getMail().concat("-").concat(user.getId().toString()))
                .setIssuedAt(new Date())
                //.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();
    }

    public Claims parseJwt(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getSubjectFromToken(String token) {
        Claims claims = parseJwt(token);
        return claims.getSubject();
    }
}
