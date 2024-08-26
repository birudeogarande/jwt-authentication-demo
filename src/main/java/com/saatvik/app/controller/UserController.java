package com.saatvik.app.controller;

import com.saatvik.app.dto.AuthRequest;
import com.saatvik.app.entity.UserInfo;
import com.saatvik.app.service.JwtService;
import com.saatvik.app.service.UserInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

/**
 *  API for user crud operation.
 */
@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserInfoService service;

     private final JwtService jwtService;


    private final AuthenticationManager authenticationManager;

    /**
     * @param service {@link UserInfoService}
     * @param jwtService {@link JwtService}
     * @param authenticationManager {@link AuthenticationManager}
     */
    public UserController(UserInfoService service, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.service = service;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    /**
     * @return String
     */
    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    /**
     * @param userInfo {@link UserInfoService}
     * @return String
     */
    @PostMapping("/addNewUser")
    public String addNewUser(@RequestBody UserInfo userInfo) {
        return service.addUser(userInfo);
    }

    /**
     * @return String
     */
    @GetMapping("/user/userProfile")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String userProfile() {
        return "Welcome to User Profile";
    }

    @GetMapping("/admin/adminProfile")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String adminProfile() {
        return "Welcome to Admin Profile";
    }

    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password())
        );
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.username());
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }

    @DeleteMapping("/deleteUser/{email}")
    public ResponseEntity<Void> deleteUser(@PathVariable String email){
        service.deleteUser(email);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();

    }

}
