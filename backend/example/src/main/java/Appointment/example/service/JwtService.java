package Appointment.example.service;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import Appointment.example.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
 
@Service
public class JwtService {
	
	private final String SECRET_KEY="0a69c4f6f349f35836511e36dd6348cb21035541693ab8dca8fe4c1166039cde";
	
	public String generateToken(User user) {
		String token=Jwts.builder().subject(user.getUsername()).issuedAt(new Date(System.currentTimeMillis())).expiration(new Date(System.currentTimeMillis()+24*60*60*1000)).signWith(getSigninKey()).compact();
		return token;
	}
	
	private SecretKey getSigninKey() {
		byte[] key_bytes=Decoders.BASE64URL.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(key_bytes);
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts.parser().verifyWith(getSigninKey()).build().parseSignedClaims(token).getPayload();
		}
	
	private <T> T extractClaim(String token,Function<Claims,T> resolver) {
		Claims claims=extractAllClaims(token);
		return resolver.apply(claims);
	}
	
	public String extractUsername(String token) {
		return extractClaim(token,Claims::getSubject);
	}
	
	public Boolean isValid(String token, UserDetails user) {
		String username=extractUsername(token);
		return username.equals(user.getUsername()) && !isTokenExpired(token);
	}
	
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	public Date extractExpiration(String token) {
		return extractClaim(token,Claims::getExpiration);
	}
	
	
	
	

}
