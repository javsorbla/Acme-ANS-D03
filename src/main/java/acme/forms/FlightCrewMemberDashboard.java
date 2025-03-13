
package acme.forms;

import java.util.List;
import java.util.Map;

import acme.client.components.basis.AbstractForm;
import acme.entities.flightassignment.CurrentStatus;
import acme.entities.flightassignment.FlightAssignment;
import acme.realms.flightcrewmember.FlightCrewMember;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlightCrewMemberDashboard extends AbstractForm {

	// Serialisation version --------------------------------------------------
	private static final long				serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------
	List<String>							lastFiveDestinations;

	Integer									legsWithLowSeverityActivityLog;  // from 0 to 3

	Integer									legsWithMediumSeverityActivityLog; // from 4 to 7

	Integer									legsWithHighSeverityActivityLog;  // from 8 to 10

	List<FlightCrewMember>					membersAssignedInLastLeg;

	Map<CurrentStatus, FlightAssignment>	flightAssignmentGroupedByStatus;

	Double									averageFlightAssignmentInLastMonth;

	Integer									minimunFlightAssignmentInLastMonth;

	Integer									maximumFlightAssignmentInLastMonth;

	Double									flightAssignmentStandardDeviationInLastMonth;
}
