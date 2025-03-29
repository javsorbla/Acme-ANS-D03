
package acme.features.flightcrewmember.activitylog;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.activitylog.ActivityLog;
import acme.realms.flightcrewmember.FlightCrewMember;

@GuiService
public class ActivityLogListService extends AbstractGuiService<FlightCrewMember, ActivityLog> {

	//Internal state ---------------------------------------------

	@Autowired
	private ActivityLogRepository repository;

	//AbstractGuiService interface -------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<ActivityLog> activityLogs;
		int flightAssignmentId;

		flightAssignmentId = super.getRequest().getData("id", int.class);

		activityLogs = this.repository.findActivityLogByFlightAssignmentId(flightAssignmentId);

		super.getBuffer().addData(activityLogs);
	}

	@Override
	public void unbind(final ActivityLog activityLog) {
		Dataset dataset;

		dataset = super.unbindObject(activityLog, "registrationMoment", "incidentType", "severityLevel");

		super.addPayload(dataset, activityLog, "publish");

		super.getResponse().addData(dataset);
	}
}
