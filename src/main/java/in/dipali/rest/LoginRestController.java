package in.dipali.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.dipali.binding.LoginForm;
import in.dipali.service.UserService;

@RestController
public class LoginRestController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public String login(@RequestBody LoginForm loginForm) {
		String status = userService.loginCheck(loginForm);
		return status;
	}
}
