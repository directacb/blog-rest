package com.dev.blog.helper;

import java.security.Key;
import java.security.SignatureException;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.dev.blog.models.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenUtil {
	
	
	
	// Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	   private final String jwtSecret  ="fPWjuXCuoljDRR9gdCtNC8vTuQuBUIvOM2fy+mCK3Oc=";
	   private final SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	   private final String jwtIssuer = "example.com";
	
//	public JwtTokenUtil() {
//		System.out.println(Encoders.BASE64.encode(key.getEncoded()));
//	}
	
	   public String generateToken(User user) {
		   return Jwts.builder()
				   	  .setSubject(user.getId() + ","+user.getEmail())
				   	  .setIssuer(jwtIssuer)
				   	  .setIssuedAt(new Date())
				   	  .setExpiration(new Date(System.currentTimeMillis()+(60*60*1000))) // 1 hour expiration time
				   	  .signWith(key)
				   	  .compact();
	   }
	   
	   public String getUserId(String token) {
		   Claims claims = Jwts.parserBuilder()
				   				.setSigningKey(key)
				   				.build()
				   				.parseClaimsJws(token)
				   				.getBody();
		   return claims.getSubject().split(",")[0];
	   }
	
	   public String getUsername(String token) {
		   Claims claims = Jwts.parserBuilder()
	   				.setSigningKey(key)
	   				.build()
	   				.parseClaimsJws(token)
	   				.getBody();
         return claims.getSubject().split(",")[1];
	   }
	   public Date getExpirationDate(String token) {
		   Claims claims = Jwts.parserBuilder()
	   				.setSigningKey(key)
	   				.build()
	   				.parseClaimsJws(token)
	   				.getBody();
        return claims.getExpiration();
	   }
		
	
	public boolean validate(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJwt(token);
			return true;
		}catch (MalformedJwtException ex) {
			System.out.println("Invalid JWT token "+ex.getMessage());
		}catch (ExpiredJwtException ex)
		{
			System.out.println("Expired JWT token "+ex.getMessage());
		}
		catch (UnsupportedJwtException ex)
		{
			System.out.println("Unsupported JWT token "+ex.getMessage());
		}
		catch (IllegalArgumentException ex)
		{
			System.out.println("JWT claims string is emptly "+ex.getMessage());
		}
		return false;
	}
	
	

}
