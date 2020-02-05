package com.embl.person.security;

import com.embl.person.util.constants.Constants;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORITIES_KEY = "scopes";

    private final JwtTokenUtil jwtTokenUtil;

    public JwtAuthenticationFilter(final JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void doFilterInternal(final HttpServletRequest req, final HttpServletResponse res, final FilterChain chain)
            throws IOException, ServletException {
        logger.info("================== " + req.getRequestURI());
        final String header = req.getHeader(Constants.HEADER_STRING);
        String username = null;
        String authToken = null;
        if (header != null && header.startsWith(Constants.TOKEN_PREFIX)) {
            authToken = header.replace(Constants.TOKEN_PREFIX, "");
            try {
                username = jwtTokenUtil.getUsernameFromToken(authToken);
            } catch (final IllegalArgumentException e) {
                logger.error("an error occured during getting username from token", e);
            } catch (final ExpiredJwtException e) {
                logger.warn("the token is expired and not valid anymore", e);
            } catch (final SignatureException e) {
                logger.error("Authentication Failed. Username or Password not valid.");
            }
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // To improve performance of application. Here I am not hitting the database
            // instead retrieving from token itself.
            final List<String> scopes = jwtTokenUtil.getClaimFromToken(authToken,
                    claims -> claims.get(AUTHORITIES_KEY, List.class));
            final List<GrantedAuthority> grantedAuthorities = scopes.stream()
                    .map(SimpleGrantedAuthority::new).collect(Collectors.toList());

            final UserDetails userDetails = new User(username, "", grantedAuthorities);
            if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                logger.info("authenticated user " + username + ", in security context");
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(req, res);
    }
}