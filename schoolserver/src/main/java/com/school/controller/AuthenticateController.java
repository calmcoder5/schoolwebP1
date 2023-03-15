package com.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.school.config.JwtUtils;
import com.school.model.JwtRequest;
import com.school.model.JwtResponse;
import com.school.service.impl.UserDetailsServiceImpl;

import io.inkstand.security.UserNotFoundException;

@RestController
@CrossOrigin("*")
public class AuthenticateController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    //generate token
    @PostMapping("/generate-token")
    public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception{

        try {

            authenticate(jwtRequest.getUsername(),jwtRequest.getPassword());
            
        } catch (UserNotFoundException e) {

            e.printStackTrace();
            throw new Exception("user Not Found");
        }
        
        //authenticate

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtRequest.getUsername());
        String token = this.jwtUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
        

    }

    private void authenticate(String username, String password) throws Exception{

        try {

            authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(username, password));
            
        } catch (DisabledException e) {
            throw new Exception("USER DISABLE" + e.getMessage());
        } catch(BadCredentialsException e){
            throw new Exception("INVALID CREDENTIAL" + e.getMessage());
        }
        
    }
}