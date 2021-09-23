package in.dipali.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in.dipali.binding.UnlockAccForm;
import in.dipali.service.UserService;
@RestController
public class UnlockAccRestController {
	@Autowired
	private UserService userService;
	
	@PostMapping("/unlockAccount")
	public String unlockAcc(@RequestBody UnlockAccForm unlockAccForm) {
		boolean unlockAccount = userService.unlockAccount(unlockAccForm);
		if(unlockAccount) {
			return "Account Unlocked";
		}
		return "Failed to unlock account";
	}

}
