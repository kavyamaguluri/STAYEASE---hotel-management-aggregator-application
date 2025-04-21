package com.takehome.stayease.controllers;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Optional;
import com.takehome.stayease.dtos.AuthResponseDTO;
import com.takehome.stayease.dtos.LoginUserDto;
import com.takehome.stayease.dtos.RegisterUserDto;
import com.takehome.stayease.entities.User;
import com.takehome.stayease.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> registerUser(@Valid @RequestBody RegisterUserDto registerUserDto) {
        log.info("Received registration request for email: {}", registerUserDto.getEmail());
        return ResponseEntity.ok(userService.register(registerUserDto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> loginUser(
            @Valid @RequestBody LoginUserDto loginUserDto) {
        log.info("Received login request for email: {}", loginUserDto.getEmail());
        return ResponseEntity.ok(userService.login(loginUserDto));
    }

    
}