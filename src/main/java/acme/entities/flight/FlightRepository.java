
package acme.entities.flight;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.leg.Leg;

@Repository
public interface FlightRepository extends AbstractRepository {

	@Query("select count(l) from Leg l where l.flight.id = :flightId")
	Integer getNumberOfLegs(int flightId);

	@Query("select l from Leg l where l.flight.id = :flightId order by l.departure asc")
	List<Leg> getLegsByFlight(int flightId);

	@Query("select count(l) from Leg l where l.flight.id = :flightId and l.publish = true")
	Integer getPublishedLegsByFlight(int flightId);
}
