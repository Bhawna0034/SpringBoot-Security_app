package com.bhawna.SpringBoot.Security_app.services;

import com.bhawna.SpringBoot.Security_app.dtos.LoginDto;
import com.bhawna.SpringBoot.Security_app.dtos.SignupDto;
import com.bhawna.SpringBoot.Security_app.dtos.UserDto;
import com.bhawna.SpringBoot.Security_app.entities.User;
import com.bhawna.SpringBoot.Security_app.exceptions.ResourceNotFoundException;
import com.bhawna.SpringBoot.Security_app.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + username + " not found"));

    }

    public User getUserById(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id" + userId + " not found"));
    }

    public UserDto signup(SignupDto signupDto) {
        Optional<User> user = userRepository.findByEmail(signupDto.getEmail());
        if(user.isPresent()){
            throw new BadCredentialsException("User with email already exists " + signupDto.getEmail());
        }

        User toBeCreated = modelMapper.map(signupDto, User.class);
        toBeCreated.setPassword(passwordEncoder.encode(toBeCreated.getPassword()));

        User savedUser = userRepository.save(toBeCreated);
        return modelMapper.map(savedUser, UserDto.class);
    }


}
