package in.dipali.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import in.dipali.entities.UserAccountEntity;

public interface UserAccountRepo extends JpaRepository<UserAccountEntity,Serializable> {
	public UserAccountEntity findByEmailAndPassword(String email,String password);
	
	public UserAccountEntity findByemail(String email);
	
}
