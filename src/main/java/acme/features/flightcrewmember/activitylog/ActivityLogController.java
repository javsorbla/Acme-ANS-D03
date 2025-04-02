
package acme.features.flightcrewmember.activitylog;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.controllers.AbstractGuiController;
import acme.client.controllers.GuiController;
import acme.entities.activitylog.ActivityLog;
import acme.realms.flightcrewmember.FlightCrewMember;

@GuiController
public class ActivityLogController extends AbstractGuiController<FlightCrewMember, ActivityLog> {

	//Internal state --------------------------------------------------------------

	@Autowired
	private ActivityLogListService		listService;

	@Autowired
	private ActivityLogShowService		showService;

	@Autowired
	private ActivityLogCreateService	createService;

	@Autowired
	private ActivityLogUpdateService	updateService;

	@Autowired
	private ActivityLogDeleteService	deleteService;

	@Autowired
	private ActivityLogPublishService	publishService;

	//Constructors ----------------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);
		super.addCustomCommand("publish", "update", this.publishService);
	}
}
