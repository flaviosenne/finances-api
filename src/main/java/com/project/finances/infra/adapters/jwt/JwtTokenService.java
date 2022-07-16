package com.project.finances.infra.adapters.jwt;

import com.project.finances.app.usecases.user.ports.TokenProtocol;
import com.project.finances.app.usecases.user.auth.dto.ResponseLoginDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtTokenService implements TokenProtocol {

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    @Value("${security.jwt.token.expire-in}")
    private String expireIn;

    @Override
    public ResponseLoginDto generateToken(String id){
        Claims claims = Jwts.claims().setSubject(id);

        Date today = new Date();
        Date validate = new Date(today.getTime() + Long.parseLong(this.expireIn));

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(today)
                .setExpiration(validate)
                .signWith(SignatureAlgorithm.HS512, this.secretKey)
                .compact();

        return ResponseLoginDto.builder().expireIn(validate).token(token).build();
    }

    @Override
    public String decodeToken(String token){
        return Jwts.parser()
                .setSigningKey(this.secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");

        return bearerToken != null && bearerToken.startsWith("Bearer ") ?
                bearerToken.substring(7): null;
    }

    @Override
    public boolean tokenIsValid(String token){
        try{
            Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token);
            return true;
        }catch (JwtException | IllegalArgumentException e){
            return false;
        }
    }
}
