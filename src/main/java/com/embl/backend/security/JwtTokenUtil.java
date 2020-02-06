package com.embl.backend.security;

import com.embl.backend.entity.Roles;
import com.embl.backend.entity.User;
import com.embl.backend.util.constants.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.Serializable;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Valid and generate token.
 */
@Component
public class JwtTokenUtil implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public String getUsernameFromToken(final String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(final String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(final String token, final Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(final String token) {
        return Jwts.parser().setSigningKey(Constants.SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(final String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(final User user) {
        return doGenerateToken(user);
    }

    private String doGenerateToken(final User user) {
        SignatureAlgorithm signatureAlgorithm  = SignatureAlgorithm.HS256;
        final Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put(Constants.SCOPES, user.getRoles().stream().map(Roles::getRole).collect(Collectors.toList()));

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(Constants.SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        return Jwts.builder().setClaims(claims).setIssuer(Constants.EMBL).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Constants.ACCESS_TOKEN_VALIDITY_SECONDS * 1000))
                .signWith(signatureAlgorithm, signingKey).compact();
    }

    public Boolean validateToken(final String token, final UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
