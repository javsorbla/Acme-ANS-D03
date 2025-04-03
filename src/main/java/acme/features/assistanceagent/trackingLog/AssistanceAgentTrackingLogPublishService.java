
package acme.features.assistanceagent.trackingLog;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.claims.Claim;
import acme.entities.trackingLogs.TrackingLog;
import acme.entities.trackingLogs.TrackingLogStatus;
import acme.realms.assistanceagent.AssistanceAgent;

@GuiService
public class AssistanceAgentTrackingLogPublishService extends AbstractGuiService<AssistanceAgent, TrackingLog> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AssistanceAgentTrackingLogRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		TrackingLog trackingLog;
		int trackingLogId;
		int agentId;
		boolean status;

		trackingLogId = super.getRequest().getData("id", int.class);
		trackingLog = this.repository.findTrackingLogById(trackingLogId);
		agentId = super.getRequest().getPrincipal().getActiveRealm().getId();
		status = trackingLog != null && !trackingLog.isPublish() && trackingLog.getClaim().getAssistanceAgent().getId() == agentId;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		TrackingLog trackingLog;
		int trackingLogId;

		trackingLogId = super.getRequest().getData("id", int.class);
		trackingLog = this.repository.findTrackingLogById(trackingLogId);

		super.getBuffer().addData(trackingLog);
	}

	@Override
	public void bind(final TrackingLog trackingLog) {
		super.bindObject(trackingLog, "lastUpdateMoment", "step", "resolutionPercentage", "status", "resolution");
	}

	@Override
	public void validate(final TrackingLog trackingLog) {
		boolean status;

		Claim claim = trackingLog.getClaim();
		status = claim != null && claim.isPublish(); //solo se publican si el claim esta publicado? 

		super.state(status, "*", "acme.validation.trackingLog.unpublished.message");
	}
	@Override
	public void perform(final TrackingLog trackingLog) {
		trackingLog.setPublish(true);
		this.repository.save(trackingLog);
	}

	@Override
	public void unbind(final TrackingLog trackingLog) {
		SelectChoices statusChoices;
		Dataset dataset;

		statusChoices = SelectChoices.from(TrackingLogStatus.class, trackingLog.getStatus());

		dataset = super.unbindObject(trackingLog, "lastUpdateMoment", "step", "resolutionPercentage", "status", "resolution", "publish");
		dataset.put("claimId", trackingLog.getClaim().getId());
		dataset.put("status", statusChoices);

		super.getResponse().addData(dataset);
	}

}
