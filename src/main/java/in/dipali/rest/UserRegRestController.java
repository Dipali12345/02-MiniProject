package in.dipali.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.dipali.binding.UserForm;
import in.dipali.service.UserService;

@RestController
public class UserRegRestController {
	@Autowired
	private UserService userService;

	@GetMapping("/countries")
	public Map<Integer, String> countries() {
		return userService.getCountries();
	}

	@GetMapping("/states/{countryId}")
	public Map<Integer, String> states(@PathVariable Integer countryId) {
		return userService.getStates(countryId);
	}

	@GetMapping("/cities/{stateId}")
	public Map<Integer, String> cities(@PathVariable Integer stateId) {
		return userService.getCities(stateId);
	}

	@GetMapping("/emailcheck/{email}")
	public String emailCheck(@PathVariable String email) {
		boolean emailUnique = userService.emailUnique(email);
		if (emailUnique)
			return "UNIQUE";
		return "DUPLICATE";
	}

	@PostMapping("/saveuser")
	public String saveUser(@RequestBody UserForm userForm) {
		boolean saveUser = userService.saveUser(userForm);
		if (saveUser) {
			return "SUCCESS";
		}

		return "FAIL";
	}

}
