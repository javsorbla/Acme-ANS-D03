
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
	private ActivityLogListService listService;
	
	//Constructors ----------------------------------------------------------------

	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
	}
}
