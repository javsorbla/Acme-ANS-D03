
package acme.features.flightcrewmember.activitylog;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.activitylog.ActivityLog;
import acme.entities.flightassignment.FlightAssignment;
import acme.realms.flightcrewmember.FlightCrewMember;

@GuiService
public class ActivityLogShowService extends AbstractGuiService<FlightCrewMember, ActivityLog> {

	//Internal state ---------------------------------------------

	@Autowired
	private ActivityLogRepository repository;

	//AbstractGuiService interface -------------------------------


	@Override
	public void authorise() {
		ActivityLog activityLog;
		int activityLogId;
		int flightCrewMemberId;
		boolean status;

		activityLogId = super.getRequest().getData("id", int.class);
		activityLog = this.repository.findActivityLogById(activityLogId);
		flightCrewMemberId = super.getRequest().getPrincipal().getActiveRealm().getId();

		status = activityLog != null && activityLog.getActivityLogAssignment().getFlightAssignmentCrewMember().getId() == flightCrewMemberId;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		ActivityLog activityLog;
		int activityLogId;

		activityLogId = super.getRequest().getData("id", int.class);

		activityLog = this.repository.findActivityLogById(activityLogId);

		super.getBuffer().addData(activityLog);
	}

	@Override
	public void unbind(final ActivityLog activityLog) {
		Dataset dataset;

		SelectChoices flightAssignmentChoice;
		Collection<FlightAssignment> flightAssignments;

		flightAssignments = this.repository.findAllFlightAssignments();
		flightAssignmentChoice = SelectChoices.from(flightAssignments, "id", activityLog.getActivityLogAssignment());

		dataset = super.unbindObject(activityLog, "registrationMoment", "incidentType", "description", "severityLevel", "publish", "activityLogAssignment");
		dataset.put("flightAssignmentChoice", flightAssignmentChoice);

		super.getResponse().addData(dataset);
	}
}
