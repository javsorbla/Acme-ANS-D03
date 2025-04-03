
package acme.features.assistanceagent.trackingLog;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.claims.Claim;
import acme.entities.trackingLogs.TrackingLog;
import acme.entities.trackingLogs.TrackingLogStatus;
import acme.realms.assistanceagent.AssistanceAgent;

@GuiService
public class AssistanceAgentTrackingLogCreateService extends AbstractGuiService<AssistanceAgent, TrackingLog> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AssistanceAgentTrackingLogRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		Claim claim;
		int claimId;
		int agentId;
		boolean status;

		claimId = super.getRequest().getData("claimId", int.class);
		claim = this.repository.findClaimById(claimId);
		agentId = super.getRequest().getPrincipal().getActiveRealm().getId();
		status = claim != null && !claim.isPublish() && claim.getAssistanceAgent().getId() == agentId;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		TrackingLog trackingLog;
		int claimId;
		Claim claim;
		Date lastUpdateMoment;

		claimId = super.getRequest().getData("claimId", int.class);
		claim = this.repository.findClaimById(claimId);
		lastUpdateMoment = MomentHelper.getCurrentMoment();

		//Hace falta alguno mas? todos?
		trackingLog = new TrackingLog();
		trackingLog.setLastUpdateMoment(lastUpdateMoment);
		trackingLog.setClaim(claim);
		trackingLog.setPublish(false);

		super.getBuffer().addData(trackingLog);
	}

	@Override
	public void bind(final TrackingLog trackingLog) {
		super.bindObject(trackingLog, "lastUpdateMoment", "step", "resolutionPercentage", "status", "resolution");
	}

	@Override
	public void validate(final TrackingLog trackingLog) {
		;
	}

	//Segurohace falta el momento actual?
	@Override
	public void perform(final TrackingLog trackingLog) {
		Date lastUpdateMoment;

		lastUpdateMoment = MomentHelper.getCurrentMoment();
		trackingLog.setLastUpdateMoment(lastUpdateMoment);
		this.repository.save(trackingLog);
	}

	@Override
	public void unbind(final TrackingLog trackingLog) {
		SelectChoices statusChoices;
		Dataset dataset;

		statusChoices = SelectChoices.from(TrackingLogStatus.class, trackingLog.getStatus());

		dataset = super.unbindObject(trackingLog, "lastUpdateMoment", "step", "resolutionPercentage", "status", "resolution", "publish");
		dataset.put("readonly", false);
		dataset.put("claimId", super.getRequest().getData("claimId", int.class));
		dataset.put("status", statusChoices);

		super.getResponse().addData(dataset);
	}

}
