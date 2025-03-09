
package acme.entities.flight;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.leg.Leg;

@Repository
public interface FlightRepository extends AbstractRepository {

	@Query("select l from Leg l where l.flight.id = :flightId and l.departure = (select min(leg.departure) from Leg leg where leg.flight.id = :flightId)")
	Leg getFlightFirstLeg(int flightId);

}
