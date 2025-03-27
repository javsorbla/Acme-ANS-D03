
package acme.features.flightcrewmember.flightassignment;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.flightassignment.CurrentStatus;
import acme.entities.flightassignment.Duty;
import acme.entities.flightassignment.FlightAssignment;
import acme.realms.flightcrewmember.FlightCrewMember;

@GuiService
public class FlightAssignmentShowService extends AbstractGuiService<FlightCrewMember, FlightAssignment> {

	//Internal state ----------------------------------------------------------

	@Autowired
	private FlightAssignmentRepository repository;

	//AbstractGuiService state ----------------------------------------------------------


	@Override
	public void authorise() {
		FlightAssignment flightAssignment;
		int flightAssignmentId;

		flightAssignmentId = super.getRequest().getData("id", int.class);
		flightAssignment = this.repository.findFlightAssignmentById(flightAssignmentId);

		FlightCrewMember flightCrewMember = (FlightCrewMember) super.getRequest().getPrincipal().getActiveRealm();

		if (flightCrewMember.equals(flightAssignment.getFlightAssignmentCrewMember()))
			super.getResponse().setAuthorised(true);

	}

	@Override
	public void load() {
		FlightAssignment flightAssignment;
		int flightAssignmentId;

		flightAssignmentId = super.getRequest().getData("id", int.class);
		flightAssignment = this.repository.findFlightAssignmentById(flightAssignmentId);

		super.getBuffer().addData(flightAssignment);
	}

	@Override
	public void unbind(final FlightAssignment flightAssignment) {
		Dataset dataset;
		SelectChoices duty;
		SelectChoices currentStatus;

		duty = SelectChoices.from(Duty.class, flightAssignment.getDuty());
		currentStatus = SelectChoices.from(CurrentStatus.class, flightAssignment.getCurrentStatus());

		dataset = super.unbindObject(flightAssignment, "duty", "lastUpdateMoment", "currentStatus", "remarks");
		dataset.put("duty", duty);
		dataset.put("currentStatus", currentStatus);

		super.addPayload(dataset, flightAssignment, "duty");
		
		super.getResponse().addData(dataset);
	}
}
