package com.example.filters;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.repository.UserDetailsImpl;
import com.example.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	
	private final JwtService jwtservice;
	private final UserDetailsImpl userdetailsservice;
	
	public JwtAuthenticationFilter(JwtService jwt,UserDetailsImpl user) {
		this.jwtservice=jwt;
		this.userdetailsservice=user;
	}
	
	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
			throws ServletException, IOException {
		
		String authheader=request.getHeader("authToken");
		if(authheader==null || authheader.startsWith("Bearer")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		String token=authheader.substring(7);
		String username=jwtservice.extractUsername(token);
		if(username!=null || SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails userdetails=userdetailsservice.loadUserByUsername(username);
			if(jwtservice.isValid(token, userdetails)) {
				UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(userdetails,null,userdetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
				
				
			}
		}
		filterChain.doFilter(request, response);
		
	}
	
	

}
