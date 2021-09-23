package in.dipali.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import in.dipali.entities.CountryMasterEntiy;

public interface CountryRepo extends JpaRepository<CountryMasterEntiy, Serializable> {

}
