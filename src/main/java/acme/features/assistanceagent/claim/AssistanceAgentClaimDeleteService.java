
package acme.features.assistanceagent.claim;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.claims.Claim;
import acme.entities.claims.ClaimType;
import acme.entities.leg.Leg;
import acme.entities.trackingLogs.TrackingLog;
import acme.realms.assistanceagent.AssistanceAgent;

@GuiService
public class AssistanceAgentClaimDeleteService extends AbstractGuiService<AssistanceAgent, Claim> {

	//Internal state ---------------------------------------------

	@Autowired
	private AssistanceAgentClaimRepository repository;

	//AbstractGuiService interface -------------------------------


	@Override
	public void authorise() {
		boolean status;
		Claim claim;
		int id;
		AssistanceAgent assistanceAgent;

		id = super.getRequest().getData("id", int.class);
		claim = this.repository.findClaimById(id);
		assistanceAgent = claim == null ? null : claim.getAssistanceAgent();
		status = super.getRequest().getPrincipal().hasRealm(assistanceAgent) && (claim == null || claim.getPublish() == false);
		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {
		Claim claim;
		int id;

		id = super.getRequest().getData("id", int.class);
		claim = this.repository.findClaimById(id);

		super.getBuffer().addData(claim);
	}

	@Override
	public void bind(final Claim claim) {
		super.bindObject(claim, "registrationMoment", "passengerEmail", "description", "type", "leg");
	}

	@Override
	public void validate(final Claim claim) {
		;
	}

	@Override
	public void perform(final Claim claim) {
		Collection<TrackingLog> trackingLogs;

		trackingLogs = this.repository.findTrackingLogsByClaimId(claim.getId());
		this.repository.deleteAll(trackingLogs);
		this.repository.delete(claim);
	}

	@Override
	public void unbind(final Claim claim) {
		Collection<Leg> legs;
		SelectChoices typesChoices;
		SelectChoices legsChoices;
		Dataset dataset;

		typesChoices = SelectChoices.from(ClaimType.class, claim.getType());
		legs = this.repository.findAllLeg();
		legsChoices = SelectChoices.from(legs, "flightNumber", claim.getLeg());

		dataset = super.unbindObject(claim, "registrationMoment", "passengerEmail", "description", "type", "publish");
		dataset.put("types", typesChoices);
		dataset.put("leg", legsChoices.getSelected().getKey());
		dataset.put("legs", legsChoices);

		super.getResponse().addData(dataset);
	}
}
