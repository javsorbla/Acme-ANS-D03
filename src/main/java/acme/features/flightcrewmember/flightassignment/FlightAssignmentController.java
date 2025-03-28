
package acme.features.flightcrewmember.flightassignment;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.controllers.AbstractGuiController;
import acme.client.controllers.GuiController;
import acme.entities.flightassignment.FlightAssignment;
import acme.realms.flightcrewmember.FlightCrewMember;

@GuiController
public class FlightAssignmentController extends AbstractGuiController<FlightCrewMember, FlightAssignment> {

	//Internal state --------------------------------------------------------------

	@Autowired
	private FlightAssignmentListCompletedService	listCompletedService;

	@Autowired
	private FlightAssignmentListPlannedService		listPlannedService;

	@Autowired
	private FlightAssignmentShowService				showService;

	@Autowired
	private FlightAssignmentCreateService			createService;

	@Autowired
	private FlightAssignmentUpdateService			updateService;

	//Constructors ----------------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addCustomCommand("list-completed", "list", this.listCompletedService);
		super.addCustomCommand("list-planned", "list", this.listPlannedService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
	}
}
