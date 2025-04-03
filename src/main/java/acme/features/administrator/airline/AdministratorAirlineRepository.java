
package acme.features.administrator.airline;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.airline.Airline;

@Repository
public interface AdministratorAirlineRepository extends AbstractRepository {

	@Query("select a from Airline a where a.id = :airlineId")
	Airline findAirlineById(int airlineId);

	@Query("select a from Airline a")
	Collection<Airline> findAllAirlines();
}
