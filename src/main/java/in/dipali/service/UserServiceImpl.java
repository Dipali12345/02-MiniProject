package in.dipali.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.Entity;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.dipali.binding.LoginForm;
import in.dipali.binding.UnlockAccForm;
import in.dipali.binding.UserForm;
import in.dipali.entities.CityMasterEntity;
import in.dipali.entities.CountryMasterEntiy;
import in.dipali.entities.StateMasterEntiy;
import in.dipali.entities.UserAccountEntity;
import in.dipali.repositories.CityRepo;
import in.dipali.repositories.CountryRepo;
import in.dipali.repositories.StateRepo;
import in.dipali.repositories.UserAccountRepo;
import in.dipali.utils.EmailUtils;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserAccountRepo userAccountRepo;
	@Autowired
	CountryRepo countryRepo;
	@Autowired
	StateRepo stateRepo;
	@Autowired
	CityRepo cityRepo;
	@Autowired
	private EmailUtils emailUtils;

	@Override
	public String loginCheck(LoginForm loginForm) {
		UserAccountEntity accountEntity = userAccountRepo.findByEmailAndPassword(loginForm.getEmail(),
				loginForm.getPassword());
		if (accountEntity != null) {
			if (accountEntity.getAccStatus() == "LOCKED") {
				return "your account is Locked";
			} else {
				return "Succesfully Login";
			}

		} else {
			return "Invalid credensials";
		}

	}

	@Override
	public Map<Integer, String> getCountries() {
		List<CountryMasterEntiy> countries = countryRepo.findAll();
		Map<Integer, String> countryMap = new HashMap<>();
		countries.forEach(country -> {
			countryMap.put(country.getCountryId(), country.getCountryName());
		});
		return countryMap;
	}

	@Override
	public Map<Integer, String> getStates(Integer countryId) {
		List<StateMasterEntiy> states = stateRepo.findByCountryId(countryId);
		Map<Integer, String> statesMap = new HashMap<>();
		states.forEach(state -> {
			statesMap.put(state.getStateId(), state.getStateName());
		});

		return statesMap;
	}

	@Override
	public Map<Integer, String> getCities(Integer stateId) {
		List<CityMasterEntity> cities = cityRepo.findByStateId(stateId);
		Map<Integer, String> citiesMap = new HashMap<>();
		cities.forEach(city -> {
			citiesMap.put(city.getCityId(), city.getCityName());
		});

		return citiesMap;
	}

	@Override
	public boolean emailUnique(String email) {
		UserAccountEntity user = userAccountRepo.findByemail(email);
		if (user == null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean saveUser(UserForm userForm) {
		userForm.setAccStatus("LOCKED");
		userForm.setPassword(generateTempPassword());
		UserAccountEntity entity = new UserAccountEntity();
		BeanUtils.copyProperties(userForm, entity);
		UserAccountEntity save = userAccountRepo.save(entity);
		String subject="User Registration Successfull | AsokIt";
		String body=getUserRegEmailBody(userForm);
		if (save.getUserId() != null) {
			emailUtils.sendEmail(userForm.getEmail(),subject,body);
			return true;
		}
		return false;

	}

	@Override
	public boolean isTempPwdValid(String email, String tempPwd) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean unlockAccount(UnlockAccForm unlockAccForm) {
		String email = unlockAccForm.getEmail();
		String password = unlockAccForm.getTemPwd();
		UserAccountEntity user = userAccountRepo.findByEmailAndPassword(email, password);
		if (user != null) {
			user.setPassword(unlockAccForm.getNewPwd());
			user.setAccStatus("UNLOCKED");
			userAccountRepo.save(user);
			return true;
		}
		return false;
	}

	@Override
	public String forgotPwd(String emailId) {
		UserAccountEntity user = userAccountRepo.findByemail(emailId);
		if (user != null) {
			String subject="Forgot Password | Ashok IT";
			String body =getForgotpwdEmailBody(user);
			emailUtils.sendEmail(user.getEmail(),subject,body);
			return "Email sent with password";
		}
		return "Enter correct Email Id";
	}

	private String generateTempPassword() {
		String randomAlphanumeric = RandomStringUtils.randomAlphanumeric(6);

		return randomAlphanumeric;
	}

	private String getUserRegEmailBody(UserForm userForm) {
		StringBuffer sb = new StringBuffer();
		String filename = "UNLOCK-ACC-EMAIL-BODY-TEMPLATE.txt";
		List<String> lines = new ArrayList<>();

		try (BufferedReader br = Files.newBufferedReader(Paths.get(filename))) {
			// br returns as strem and convert it into a List
			lines = br.lines().collect(Collectors.toList());

		} catch (IOException e) {

			e.printStackTrace();
		}
		lines.forEach(line -> {
			if (line.contains("{FNAME}")) {
				line.replace("{FNAME}", userForm.getFname());
			}
			if (line.contains("{LNAME}")) {
				line.replace("{LNAME}", userForm.getFname());
			}
			if (line.contains("{TEMP-PWD}")) {
				line.replace("{TEMP-PWD}", userForm.getFname());
			}
			if (line.contains("{EMAIL}")) {
				line.replace("{EMAIL}", userForm.getFname());
			}
			sb.append(line);
		});
		return sb.toString();
	}

	private String getForgotpwdEmailBody(UserAccountEntity entity) {
		StringBuffer sb = new StringBuffer();
		String filename = "RECOVER-PASSWORD-EMAIL-BODY-TEMPALT.txt";
		List<String> lines = new ArrayList<>();

		try (BufferedReader br = Files.newBufferedReader(Paths.get(filename))) {
			// br returns as strem and convert it into a List
			lines = br.lines().collect(Collectors.toList());

		} catch (IOException e) {

			e.printStackTrace();
		}
		lines.forEach(line -> {
			if (line.contains("{FNAME}")) {
				line.replace("{FNAME}", entity.getFname());
			}
			if (line.contains("{LNAME}")) {
				line.replace("{LNAME}", entity.getFname());
			}
			if (line.contains("{PWD}")) {
				line.replace("{PWD}", entity.getFname());
			}

			sb.append(line);
		});

		return sb.toString();
	}

}
