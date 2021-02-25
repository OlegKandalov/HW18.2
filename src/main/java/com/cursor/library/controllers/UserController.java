package com.cursor.library.controllers;

import com.cursor.library.JwtUtil.JwtCreateToken;
import com.cursor.library.services.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtCreateToken jwtToken;
    private final AuthenticationManager authenticationManager;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<String> createAuthToken(@RequestBody AuthenticationRequest auth) throws AccessDeniedException {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(auth.getUsername(), auth.getPassword()));
        UserDetails userDetails = userService.login(auth.getUsername(), auth.getPassword());

        final String jwt = jwtToken.generateToken(userDetails);

        return ResponseEntity.ok(jwt);
    }

    @RequestMapping(value = "/dummy", method = RequestMethod.POST)
    public ResponseEntity<String> createAuthToken() {
        return ResponseEntity.ok("user");
    }

    @Data
    public static class AuthenticationRequest implements Serializable {
        private String username;
        private String password;
    }
}
