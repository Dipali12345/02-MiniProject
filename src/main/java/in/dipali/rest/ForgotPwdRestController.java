package in.dipali.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import in.dipali.service.UserService;
@RestController
public class ForgotPwdRestController {
	@Autowired
	private UserService userService;
	@GetMapping("/forgotpwd")
	public String forgotPwd(@PathVariable String email) {
		String status = userService.forgotPwd(email);
		return status;
	}
}
