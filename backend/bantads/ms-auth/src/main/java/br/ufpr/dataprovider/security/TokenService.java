package br.ufpr.dataprovider.security;

import br.ufpr.core.domain.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(Usuario usuario){

      Key key = Keys.hmacShaKeyFor(secret.getBytes());

      return Jwts.builder()
        .setSubject(usuario.getUserId())
        .claim("email", usuario.getEmail())
        .claim("role", usuario.getTipoUsuario())
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis()+ expiration))
        .signWith(key, SignatureAlgorithm.HS256)
        .compact();

    }


}
