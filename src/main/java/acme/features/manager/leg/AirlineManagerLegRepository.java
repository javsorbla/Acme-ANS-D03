
package acme.features.manager.leg;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.flight.Flight;
import acme.entities.leg.Leg;
import acme.realms.manager.AirlineManager;

@Repository
public interface AirlineManagerLegRepository extends AbstractRepository {

	@Query("select l from Leg l where l.flight.id = :flightId order by l.departure asc")
	Collection<Leg> findAllLegsByFlightId(int flightId);

	@Query("select m from AirlineManager m where m.id = :managerId")
	AirlineManager findManagerById(int managerId);

	@Query("select f from Flight f where f.id = :flightId")
	Flight findFlightById(int flightId);
}
