package u5w3d5.u5w3d5.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import u5w3d5.u5w3d5.entities.Events;
import u5w3d5.u5w3d5.entities.User;
import u5w3d5.u5w3d5.services.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UsersController {

	@Autowired
	private UserService usersService;

	@GetMapping
	public Page<User> getAllUsers(@RequestParam(defaultValue = "0") int page,
								  @RequestParam(defaultValue = "10") int size,
								  @RequestParam(defaultValue = "id") String orderBy
	) {
		return this.usersService.getUsers(page, size, orderBy);
	}

	@GetMapping("/me")
	public User getProfile(@AuthenticationPrincipal User currentAuthenticatedUser) {
		return currentAuthenticatedUser;
	}

//	@GetMapping("/me/myevent")
//	public List<Events> getEvent(@AuthenticationPrincipal User currentAuthenticatedUser) {
//		return currentAuthenticatedUser.getEvents();
//	}

	@PutMapping("/me")
	public User getMeAndUpdate(@AuthenticationPrincipal User currentAuthenticatedUser, @RequestBody User updatedUser) {
		return this.usersService.findByIdAndUpdate(currentAuthenticatedUser.getId(), updatedUser);
	}

	@DeleteMapping("/me")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void getMeAndDelete(@AuthenticationPrincipal User currentAuthenticatedUser) {
		this.usersService.findByIdAndDelete(currentAuthenticatedUser.getId());
	}
	@PostMapping("/me/add/event")
	public void aggiungiEvento(@RequestParam UUID eventId,@AuthenticationPrincipal User currentAuthenticatedUser){
		this.usersService.reserve(eventId,currentAuthenticatedUser);
	}


	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ORGANIZZATORE')")
	public User findById(@PathVariable UUID id) {
		return this.usersService.findById(id);
	}


	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('ORGANIZZATORE')")
	public User findByIdAndUpdate(@PathVariable UUID id, @RequestBody User updatedUser) {

		return this.usersService.findByIdAndUpdate(id, updatedUser);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('ORGANIZZATORE')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void findByIdAndDelete(@PathVariable UUID id) {
		this.usersService.findByIdAndDelete(id);
	}

}
