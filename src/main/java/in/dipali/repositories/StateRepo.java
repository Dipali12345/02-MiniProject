package in.dipali.repositories;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.dipali.entities.StateMasterEntiy;

public interface StateRepo extends JpaRepository<StateMasterEntiy, Serializable> {
	public List<StateMasterEntiy> findByCountryId(Integer countryId);
}
