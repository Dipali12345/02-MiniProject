package in.dipali.service;

import java.util.Map;

import in.dipali.binding.LoginForm;
import in.dipali.binding.UnlockAccForm;
import in.dipali.binding.UserForm;

public interface UserService {

	public String loginCheck(LoginForm loginForm);

	public Map<Integer, String> getCountries();

	public Map<Integer, String> getStates(Integer countryId);

	public Map<Integer, String> getCities(Integer stateId);

	public boolean emailUnique(String email);

	public boolean saveUser(UserForm userForm);

	public boolean isTempPwdValid(String email, String tempPwd);

	public boolean unlockAccount(UnlockAccForm unlockAccForm);

	public String forgotPwd(String emailId);

}
