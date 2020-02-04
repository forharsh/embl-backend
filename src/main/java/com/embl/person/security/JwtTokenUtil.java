package com.embl.person.security;

import com.embl.person.entity.Roles;
import com.embl.person.entity.User;
import com.embl.person.util.constants.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.embl.person.util.constants.Constants.EMBL;
import static com.embl.person.util.constants.Constants.SCOPES;

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
        return Jwts.parser().setSigningKey(Constants.SIGNING_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(final String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(final User user) {
        return doGenerateToken(user);
    }

    private String doGenerateToken(final User user) {

        final Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put(SCOPES, user.getRoles().stream().map(Roles::getRole).collect(Collectors.toList()));

        return Jwts.builder().setClaims(claims).setIssuer(EMBL).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + Constants.ACCESS_TOKEN_VALIDITY_SECONDS * 1000))
                .signWith(SignatureAlgorithm.HS256, Constants.SIGNING_KEY).compact();
    }

    public Boolean validateToken(final String token, final UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
