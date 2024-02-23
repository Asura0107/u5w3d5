package u5w3d5.u5w3d5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import u5w3d5.u5w3d5.dto.LoginResponseDTO;
import u5w3d5.u5w3d5.dto.NewUserDTO;
import u5w3d5.u5w3d5.dto.UserLoginDTO;
import u5w3d5.u5w3d5.entities.User;
import u5w3d5.u5w3d5.services.AuthService;


@RestController
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	private AuthService authService;
	

	@PostMapping("/login")
	public LoginResponseDTO login(@RequestBody UserLoginDTO payload) {
		return new LoginResponseDTO(authService.GenerateToken(payload));
	}

	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public User saveUser(@RequestBody NewUserDTO newUser) {
		return this.authService.saveUser(newUser);
	}
}
