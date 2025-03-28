
package acme.features.flightcrewmember.flightassignment;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.flightassignment.FlightAssignment;
import acme.entities.leg.Leg;
import acme.realms.flightcrewmember.FlightCrewMember;

@Repository
public interface FlightAssignmentRepository extends AbstractRepository {

	@Query("SELECT fa FROM FlightAssignment fa WHERE fa.flightAssignmentLeg.arrival < CURRENT_TIMESTAMP")
	Collection<FlightAssignment> findCompletedFlightAssignments();

	@Query("SELECT fa FROM FlightAssignment fa WHERE fa.flightAssignmentLeg.departure > CURRENT_TIMESTAMP")
	Collection<FlightAssignment> findPlannedFlightAssignments();

	@Query("SELECT fa FROM FlightAssignment fa WHERE fa.flightAssignmentCrewMember.id = :flightCrewMemberId AND fa.flightAssignmentLeg.arrival < CURRENT_TIMESTAMP")
	Collection<FlightAssignment> findCompletedFlightAssignmentsByMemberId(final int flightCrewMemberId);

	@Query("SELECT fa FROM FlightAssignment fa WHERE fa.flightAssignmentCrewMember.id = :flightCrewMemberId AND fa.flightAssignmentLeg.departure > CURRENT_TIMESTAMP")
	Collection<FlightAssignment> findPlannedFlightAssignmentsByMemberId(final int flightCrewMemberId);

	@Query("SELECT fa FROM FlightAssignment fa WHERE fa.id = :id")
	FlightAssignment findFlightAssignmentById(int id);

	@Query("SELECT l FROM Leg l")
	Collection<Leg> findAllLegs();

	@Query("SELECT fcm FROM FlightCrewMember fcm")
	Collection<FlightCrewMember> findAllFlightCrewMembers();

	@Query("SELECT l FROM Leg l WHERE l.id = :legId")
	Leg findLegById(int legId);

	@Query("SELECT fcm FROM FlightCrewMember fcm WHERE fcm.id = :flightCrewMemberId")
	FlightCrewMember findFlightCrewMemeberById(int flightCrewMemberId);

}
