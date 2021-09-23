package in.dipali.repositories;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.dipali.entities.CityMasterEntity;

public interface CityRepo extends JpaRepository<CityMasterEntity, Serializable> {
	public List<CityMasterEntity> findByStateId(Integer stateId);
}
