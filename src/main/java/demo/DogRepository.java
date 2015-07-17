package demo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * This repository uses JPA to store information about dogs.
 * This repository supports add dog, find dog by ID (search), get all dogs, delete a dog by ID and update dog by ID.
 */
@EnableTransactionManagement
@EnableJpaRepositories
public interface DogRepository extends JpaRepository<Dogs, Long> {
	
	//If ID matches a dog ID in the database, update all fields of that dog and return the updated dog.
	@Modifying
	@Query(value="update Dogs set weight=:weight, heartbeat=:heartbeat, temperature=:temp, name=:name, lat=:lat, lon=:lon where id=:Id")
	@Transactional
	void updateByID(@Param("Id") Long Id, @Param("weight") int weight, @Param("heartbeat") int heartbeat, @Param("temp") int temperature, @Param("name") String name, @Param("lat") double lat, @Param("lon") double lon);
	
	@Modifying
	@Transactional
	void deleteByid(Long id);
	
	Dogs findByid(Long id);
}
