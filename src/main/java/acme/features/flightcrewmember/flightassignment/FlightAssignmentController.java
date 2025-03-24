
package acme.features.flightcrewmember.flightassignment;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.controllers.AbstractGuiController;
import acme.entities.flightassignment.FlightAssignment;
import acme.realms.flightcrewmember.FlightCrewMember;

public class FlightAssignmentController extends AbstractGuiController<FlightCrewMember, FlightAssignment> {

	//Internal state --------------------------------------------------------------

	@Autowired
	private FlightAssignmentListService listService;

	//Constructors ----------------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
	}
}
