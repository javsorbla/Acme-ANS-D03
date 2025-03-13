
package acme.entities.leg;

import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface LegRepository extends AbstractRepository {

	@Query("select l from Leg l where l.flightNumber = :flightNumber")
	Leg findLegByFlightNumber(String flightNumber);

	@Query("select max(l.arrival) from Leg l where l.flightNumber = :flightId and l.status != CANCELLED")
	Date findLatestNotCancelledArrivalDate(int flightId);
}
