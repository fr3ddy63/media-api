package de.home.media.api.security;

import io.jsonwebtoken.*;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@ApplicationScoped
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth";

    private String secretKey;
    private long tokenValidity;

    @PostConstruct
    public void init() {
        this.secretKey = "02af6a679cac01122bb4ce944b93874c4f94a485d97865c7";
        this.tokenValidity = TimeUnit.HOURS.toMillis(10);
    }

    public String createToken(String username, Set<String> authorities) {
        long now = new Date().getTime();

        return Jwts.builder()
                .setSubject(username)
                .claim(AUTHORITIES_KEY, String.join(",", authorities))
                .signWith(SignatureAlgorithm.HS512, this.secretKey)
                .setExpiration(new Date(now + this.tokenValidity))
                .compact();
    }

    public JWTCredential getCredential(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(this.secretKey)
                .parseClaimsJws(token)
                .getBody();

        Set<String> authorities = Arrays
                .stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .collect(Collectors.toSet());

        return new JWTCredential(claims.getSubject(), authorities);
    }

    public Boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException | ExpiredJwtException e) {
            return false;
        }
    }
}
