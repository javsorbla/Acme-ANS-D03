
package acme.features.flightcrewmember.flightassignment;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.flightassignment.FlightAssignment;

@Repository
public interface FlightAssignmentRepository extends AbstractRepository {

	@Query("SELECT fa FROM FlightAssignment fa WHERE fa.flightAssignmentLeg.status = acme.entities.leg.LegStatus.LANDED")
	Collection<FlightAssignment> findCompletedFlightAssignments();

	@Query("SELECT fa FROM FlightAssignment fa WHERE fa.flightAssignmentLeg.departure > CURRENT_TIMESTAMP")
	Collection<FlightAssignment> findUpcomingFlightAssignments();

	@Query("SELECT fa FROM FlightAssignment fa WHERE fa.id = :id")
	FlightAssignment findFlightAssignmentById(int id);
}
