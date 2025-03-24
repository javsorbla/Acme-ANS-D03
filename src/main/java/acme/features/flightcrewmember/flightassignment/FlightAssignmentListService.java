
package acme.features.flightcrewmember.flightassignment;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.services.AbstractGuiService;
import acme.entities.flightassignment.FlightAssignment;
import acme.realms.flightcrewmember.FlightCrewMember;

public class FlightAssignmentListService extends AbstractGuiService<FlightCrewMember, FlightAssignment> {

	//Internal state ---------------------------------------------

	@Autowired
	private FlightAssignmentRepository repository;

	//AbstractGuiService interface -------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<FlightAssignment> completedFlightAssignment;

		completedFlightAssignment = this.repository.findCompletedFlightAssignments();

		super.getBuffer().addData(completedFlightAssignment);

		Collection<FlightAssignment> upcomingFlightAssignment;

		upcomingFlightAssignment = this.repository.findUpcomingFlightAssignments();

		super.getBuffer().addData(upcomingFlightAssignment);
	}

	@Override
	public void unbind(final FlightAssignment flightAssignment) {
		Dataset dataset;

		dataset = super.unbindObject(flightAssignment, "duty", "lastUpdateMoment", "currentStatus");

		super.addPayload(dataset, flightAssignment, "duty");

		super.getResponse().addData(dataset);
	}
}
