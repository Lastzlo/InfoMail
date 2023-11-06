package com.infopulse.infomail.controllers.rest;

import com.infopulse.infomail.dto.securityRequests.RegistrationRequest;
import com.infopulse.infomail.services.registration.RegistrationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@SecurityRequirement(name = "Authorization")
@RequestMapping(path = "api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

	private final RegistrationService registrationService;

	@PostMapping
	public ResponseEntity<?> register(@Valid @RequestBody RegistrationRequest request) {
		log.info("Received new registration request: {}", request);
		registrationService.register(request);
		return ResponseEntity.ok().build();
	}

	@GetMapping(path = "confirm")
	public ResponseEntity<?> confirm(@RequestParam("token") String token) {
		registrationService.confirmToken(token);
		return ResponseEntity.ok().build();
	}

	@GetMapping(path = "reject")
	public ResponseEntity<String> reject(@RequestParam("token") String token) {
		return ResponseEntity.ok(registrationService
				.rejectToken(token));
	}

}
