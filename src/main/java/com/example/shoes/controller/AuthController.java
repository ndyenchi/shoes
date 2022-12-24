package com.example.shoes.controller;

import com.example.shoes.common.ERole;
import com.example.shoes.common.JwtUtils;
import com.example.shoes.common.StatusCode;
import com.example.shoes.dto.*;
import com.example.shoes.entity.Role;
import com.example.shoes.entity.User;
import com.example.shoes.repository.RoleRepository;
import com.example.shoes.repository.UserRepository;
import com.example.shoes.response.GeneralApiAResponse;
import com.example.shoes.service.impl.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpClient;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public GeneralApiAResponse<?> authenticateUser(@Validated @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return new GeneralApiAResponse(StatusCode.SUCCESS, HttpStatus.OK,
				new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(),userDetails.getEmail(), roles));
	}
	@PostMapping("/signup")
	public GeneralApiAResponse<?> registerUser(@Validated @RequestBody SignupRequest signUpRequest) {

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new GeneralApiAResponse<>(StatusCode.EMAIL_EXSITED, HttpStatus.BAD_REQUEST,null);
        }
        User user = new User(signUpRequest.getusername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<Role> roles = new HashSet<>();

		Optional<Role>userRole = roleRepository.findByName(ERole.USER);
		if(!userRole.isPresent()){
			return new GeneralApiAResponse<>(StatusCode.ROLE_NOT_FOUND, HttpStatus.BAD_REQUEST,null);
		}
		roles.add(userRole.get());
        user.setRoles(roles);
		Date today = Calendar.getInstance().getTime();
		user.setCreateDate(today);
        userRepository.save(user);
		return new GeneralApiAResponse<>(StatusCode.SUCCESS, HttpStatus.OK,user);
	}
}
