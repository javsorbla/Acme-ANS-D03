
package acme.realms.manager;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface AirlineManagerRepository extends AbstractRepository {

	@Query("select m from AirlineManager m where m.identifier = :identifier")
	AirlineManager findManagerByIdentifier(String identifier);

}
