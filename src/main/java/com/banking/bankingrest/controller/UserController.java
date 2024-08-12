package com.banking.bankingrest.controller;

import com.banking.bankingrest.entity.LoginResponse;
import com.banking.bankingrest.entity.User;
import com.banking.bankingrest.request.user.UserLoginRequest;
import com.banking.bankingrest.request.user.UserRegisterRequest;
import com.banking.bankingrest.services.UserService;
import com.banking.bankingrest.services.jwt.JwtUtils;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(value = "User API", tags = "User")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Validated @RequestBody UserRegisterRequest request) {
        User user = userService.register(request.username(), request.password(), request.email(), request.role());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Validated @RequestBody UserLoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(),
                request.password()));

        final UserDetails userDetails = userService.loadUserByUsername(request.username());

        final String jwt = jwtUtils.generateToken(userDetails);
        return ResponseEntity.ok(new LoginResponse(jwt));
    }
}
