package com.example.meetingrooms.Utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
/**Component class housing values and methods for jwt filtering**/
@Component
public class JwtUtil implements Serializable {
    //acts as a verification
    private static final long serialVersionID = -2550185165626007488L;

    /*
    This determines how long until JWT validity expires.
    The user will be logged out.
    Days * Hours * Minutes * Seconds
    */
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    //secret value defined in application.properties (used as key value)
    @Value("{jwt.secret}")
    private String secret;

    //gets the username using token and returning user information
    public String getUsernameFromToken(String token){
        return getClaimFromToken(token, Claims::getSubject);
    }
    //returns the time token was issued
    public Date getIssuedAtDateFromToken(String token){
        return getClaimFromToken(token, Claims::getIssuedAt);
    }
    //returns the time the token expires
    public Date getExpirationDateFromToken(String token){
        return getClaimFromToken(token, Claims::getExpiration);
    }
    //gives claim to token
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver){
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    //using a token it returns a claims of token with secret key
    private Claims getAllClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
    //checks if the token is expired
    private Boolean isTokenExpired(String token){
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
    //returns if the token expiration should be ignored
    private Boolean ignoreTokenExpiration(String token){
        return false;
    }
    //generates a new token
    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }
    //sets up what type of token, when it is issued, when it expires, and what type of signature it has
    private String doGenerateToken(Map<String, Object> claims, String subject){
        return Jwts.builder().setClaims(claims).setSubject(subject)
                .setIssuedAt(new Date (System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();

    }
    //returns if a token can be refreshed
    public Boolean canTokenBeRefreshed(String token){
        return (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }
    //validates the token to confirm it is correct and not expired
    public Boolean validateToken(String token, UserDetails userDetails){
        final String username = getUsernameFromToken(token);
        return(username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
