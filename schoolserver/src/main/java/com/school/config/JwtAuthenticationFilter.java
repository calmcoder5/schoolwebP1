package com.school.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.school.service.impl.UserDetailsServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private JwtUtils JwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
            FilterChain filterChain)
            throws ServletException, IOException{

                final String requestTokenHeader = request.getHeader("Authorized ");
                System.out.println(requestTokenHeader);
                String username=null;
                String jwtToken=null;

                if(requestTokenHeader!=null && requestTokenHeader.startsWith("Bearer ")){

                    //yes

                    jwtToken=requestTokenHeader.substring(6);

                    try {

                        username = this.JwtUtil.extractUsername(jwtToken);
                        
                    } catch( ExpiredJwtException e ){

                        e.printStackTrace();

                        System.out.println("Jwt Token has expired");

                    }catch (Exception e) {

                        e.printStackTrace();
                        System.out.println("Error");
                      
                    }
                    

                }
                else{
                    System.out.println("Invalid token not start with Bearer");
                }

                //validated
                if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){

                    final UserDetails userDetails = this.userDetailsServiceImpl.loadUserByUsername(username);
                    if(this.JwtUtil.validateToken(jwtToken, userDetails)){

                        //token is vaild

                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken= new 
                        UsernamePasswordAuthenticationToken(userDetails,null,userDetails
                        .getAuthorities());

                        usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        
                        SecurityContextHolder.getContext()
                        .setAuthentication(usernamePasswordAuthenticationToken);


                    }else{

                        System.out.println("token is not valid");
                    }

                    filterChain.doFilter(request, response);
                }


    }
    
}
