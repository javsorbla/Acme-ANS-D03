
package acme.entities.flight;

import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface FlightRepository extends AbstractRepository {

	@Query("select min(l.departure) from Leg l where l.flight.id = :flightId")
	Date getScheduledDeparture(int flightId);

	@Query("select max(l.arrival) from Leg l where l.flight.id = :flightId")
	Date getScheduledArrival(int flightId);

	@Query("select l.departureAirport.city from Leg l where l.flight.id = :flightId and l.departure = (select min(l2.departure) from Leg l2 where l2.flight.id = :flightId)")
	String getOriginCity(int flightId);

	@Query("select l.arrivalAirport.city from Leg l where l.flight.id = :flightId and l.arrival = (select max(l2.arrival) from Leg l2 where l2.flight.id = :flightId)")
	String getDestinationCity(int flightId);

	@Query("select count(l) from Leg l where l.flight.id = :flightId")
	Integer getNumberOfLegs(int flightId);
}
