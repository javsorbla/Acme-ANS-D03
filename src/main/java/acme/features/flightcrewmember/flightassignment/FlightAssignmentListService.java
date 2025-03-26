
package acme.features.flightcrewmember.flightassignment;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractGuiService;
import acme.entities.flightassignment.CurrentStatus;
import acme.entities.flightassignment.Duty;
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
		Collection<FlightAssignment> flightAssignments;

		flightAssignments = this.repository.findCompletedFlightAssignments();

		super.getBuffer().addData(flightAssignments);

	}

	@Override
	public void unbind(final FlightAssignment flightAssignment) {
		Dataset dataset;
		SelectChoices duty;
		SelectChoices currentStatus;

		duty = SelectChoices.from(Duty.class, flightAssignment.getDuty());
		currentStatus = SelectChoices.from(CurrentStatus.class, flightAssignment.getCurrentStatus());

		dataset = super.unbindObject(flightAssignment, "duty", "lastUpdateMoment", "currentStatus");
		dataset.put("duty", duty);
		dataset.put("currentStatus", currentStatus);

		super.addPayload(dataset, flightAssignment, "duty");

		super.getResponse().addData(dataset);
	}
}
