
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
public class ActivityLogPublishService extends AbstractGuiService<FlightCrewMember, ActivityLog> {

	// Internal state ---------------------------------------------------------
	@Autowired
	private ActivityLogRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		int flightCrewMemberId;
		FlightAssignment flightAssignment;
		ActivityLog activityLog;

		flightCrewMemberId = super.getRequest().getPrincipal().getActiveRealm().getId();
		masterId = super.getRequest().getData("masterId", int.class);
		flightAssignment = this.repository.findFlightAssignmentById(masterId);
		activityLog = this.repository.findActivityLogById(masterId);

		status = !activityLog.isPublish() && flightAssignment != null && flightAssignment.isPublish() && activityLog.getActivityLogAssignment().getFlightAssignmentCrewMember().getId() == flightCrewMemberId;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		ActivityLog activityLog;
		int masterId;

		masterId = super.getRequest().getData("masterId", int.class);
		activityLog = this.repository.findActivityLogById(masterId);

		super.getBuffer().addData(activityLog);
	}

	@Override
	public void bind(final ActivityLog activityLog) {
		super.bindObject(activityLog, "registrationMomenr", "incidentType", "description", "severityLevel", "activityLogAssignment");
	}

	@Override
	public void perform(final ActivityLog activityLog) {
		activityLog.setPublish(true);
		this.repository.save(activityLog);
	}

	@Override
	public void unbind(final ActivityLog activityLog) {
		Dataset dataset;

		SelectChoices flightAssignmentChoice;
		Collection<FlightAssignment> flightAssignments;

		flightAssignments = this.repository.findAllFlightAssignments();
		flightAssignmentChoice = SelectChoices.from(flightAssignments, "id", activityLog.getActivityLogAssignment());

		dataset = super.unbindObject(activityLog, "registrationMoment", "incidentType", "description", "severityLevel", "activityLogAssignment");
		dataset.put("flightAssignmentChoice", flightAssignmentChoice);
		dataset.put("masterId", super.getRequest().getData("masterId", int.class));

		super.getResponse().addData(dataset);
	}
}
