package com.takehome.stayease.services;

import lombok.extern.slf4j.Slf4j;
import com.takehome.stayease.dtos.AuthResponseDTO;
import com.takehome.stayease.dtos.LoginUserDto;
import com.takehome.stayease.dtos.RegisterUserDto;
import com.takehome.stayease.entities.Role;
import com.takehome.stayease.entities.User;
import com.takehome.stayease.exceptions.BadRequestException;
import com.takehome.stayease.exceptions.ResourceNotFoundException;
import com.takehome.stayease.repositories.UserRepository;
import com.takehome.stayease.security.JwtTokenProvider;
import org.apache.logging.log4j.util.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class UserService {

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtTokenProvider tokenProvider;

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }

    public AuthResponseDTO register(RegisterUserDto registerUserDto) {
        if (userRepository.existsByEmail(registerUserDto.getEmail())) {
            throw new BadRequestException("Email is already in use");
        }

        User user = new User();
        user.setEmail(registerUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
        user.setFirstName(registerUserDto.getFirstName());
        user.setLastName(registerUserDto.getLastName());

        if (registerUserDto.getRole() != null && !registerUserDto.getRole().isEmpty()) {
            try {
                user.setRole(Role.valueOf(registerUserDto.getRole().toUpperCase())); // Ensure case consistency
            } catch (IllegalArgumentException e) {
                log.warn("Invalid role specified: {}, defaulting to USER", registerUserDto.getRole());
                user.setRole(Role.USER);
            }
        } else {
            user.setRole(Role.USER);
        }

        User savedUser = userRepository.save(user);
        log.info("User registered successfully: {}", savedUser.getEmail());

        String token = tokenProvider.generateToken(savedUser.getEmail(), "ROLE_" + savedUser.getRole().name());

        return new AuthResponseDTO(token);
    }    


    public AuthResponseDTO login(LoginUserDto loginRequest) {
        try {
            log.info("Login attempt for email: {}", loginRequest.getEmail());
    
            User user = userRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));

            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
                )
            );
    
            SecurityContextHolder.getContext().setAuthentication(authentication);
    
            String token = tokenProvider.generateToken(user.getEmail(), "ROLE_" + user.getRole().name());
    
            log.info("User logged in successfully: {}", loginRequest.getEmail());
    
            return new AuthResponseDTO(token);
        } catch (AuthenticationException e) {
            log.error("Authentication failed for user: {} - {}", loginRequest.getEmail(), e.getMessage());
            throw new ResourceNotFoundException("Invalid email or password"); 
        } catch (Exception e) {
            log.error("Unexpected error during login: {}", e.getMessage(), e);
            throw new InternalException("Unexpected server error occurred");
        }
    }
    
    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }
    
}