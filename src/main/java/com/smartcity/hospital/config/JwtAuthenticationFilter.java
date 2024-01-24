package com.smartcity.hospital.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.smartcity.hospital.helper.JwtUtil;
import com.smartcity.hospital.services.CustomUserDetailsService;

import io.jsonwebtoken.ExpiredJwtException;

/*
JWT Authentication filter is basically the class which will check if the request sent by user has a valid JWT Token or not. If it has a 
valid JWT Token then only further part of the code will be executed else it will return from there only.
*/
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    @Autowired
    private CustomUserDetailsService customUserDetailService;

    @Autowired
    private JwtUtil jwtUtilHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        try {
            
            String jwtToken = extractJwtFromRequest(request);
            if(StringUtils.hasText(jwtToken) && jwtUtilHelper.validateToken(jwtToken)){
                //UserDetails userDetails = new User(jwtUtil.extractUserName(jwtToken),"",jwtUtil.g);
                UserDetails userDetails = customUserDetailService.loadUserByUsername(jwtUtilHelper.extractUsername(jwtToken));
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                // After setting the Authentication in the context, we specify
				// that the current user is authenticated. So it passes the
				// Spring Security Configurations successfully.
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            }else{
                System.out.println("Cannot set the security context");
            }

        } catch (ExpiredJwtException e) {
            String isRefreshToken = request.getHeader("isRefreshToken"); // either true or false
            String requestPath = request.getRequestURL().toString(); // request url i.e. http://localhost:8080/api/refresh-token
            
            if(isRefreshToken.equalsIgnoreCase("true") && requestPath.contains("refresh-token")){
                allowForRefreshToken(e, request);
            }

        }catch(BadCredentialsException b){

            request.setAttribute("Bad Credentials",b );
        
        }catch(Exception e){
        
            e.printStackTrace();
        
        }
        filterChain.doFilter(request, response);
    }

    private void allowForRefreshToken(ExpiredJwtException e, HttpServletRequest request) {
        // create a UsernamePasswordAuthenticationToken with null values.
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(null,null,null);
        // After setting the Authentication in the context, we specify
		// that the current user is authenticated. So it passes the
		// Spring Security Configurations successfully.
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        // Set the claims so that in controller we will be using it to create
		// new JWT
        request.setAttribute("claims", e.getClaims());

    }


    private String extractJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")){
            return bearerToken.substring(7);
        }
        return null;
    }

}