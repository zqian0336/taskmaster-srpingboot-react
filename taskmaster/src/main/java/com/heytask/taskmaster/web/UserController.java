package com.heytask.taskmaster.web;


import com.heytask.taskmaster.entity.User;
import com.heytask.taskmaster.payload.JWTLoginSucessReponse;
import com.heytask.taskmaster.payload.LoginRequest;
import com.heytask.taskmaster.security.JwtTokenProvider;
import com.heytask.taskmaster.security.SecurityConstants;
import com.heytask.taskmaster.services.MapValidErrorService;
import com.heytask.taskmaster.services.UserService;
import com.heytask.taskmaster.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MapValidErrorService mapValidErrorService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result){
        ResponseEntity<?> errorMap = mapValidErrorService.MapValidService(result);
        if(errorMap != null) return errorMap;

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = SecurityConstants.TOKEN_PREFIX +  tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTLoginSucessReponse(true, jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result){
        // Validate passwords match
        userValidator.validate(user,result);

        ResponseEntity<?> errorMap = mapValidErrorService.MapValidService(result);
        if(errorMap!= null) return errorMap;
        User newUser = userService.saveUser(user); //save at repository, return saved item
        return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
    }
    //Save item to respository



}
