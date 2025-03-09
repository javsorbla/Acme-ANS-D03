
package acme.entities.flight;

import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.leg.Leg;

@Repository
public interface FlightRepository extends AbstractRepository {

	Leg getFlightFirtLeg(int flightId);

}
