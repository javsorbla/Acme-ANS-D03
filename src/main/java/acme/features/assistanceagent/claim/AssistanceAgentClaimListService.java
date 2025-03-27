
package acme.features.assistanceagent.claim;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.claims.Claim;
import acme.realms.assistanceagent.AssistanceAgent;

@GuiService
public class AssistanceAgentClaimListService extends AbstractGuiService<AssistanceAgent, Claim> {

	//Internal state ---------------------------------------------

	@Autowired
	private AssistanceAgentClaimRepository repository;

	//AbstractGuiService interface -------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Claim> claims;
		int assistaceAgentId;

		assistaceAgentId = super.getRequest().getPrincipal().getActiveRealm().getId();

		claims = this.repository.findAllClaimsByAssistanceAgentId(assistaceAgentId);

		super.getBuffer().addData(claims);

	}

	@Override
	public void unbind(final Claim claim) {

		Dataset dataset;

		dataset = super.unbindObject(claim, "registrationMoment", "passengerEmail", "description", "type", "publish");

		super.addPayload(dataset, claim, "registrationMoment");

		super.getResponse().addData(dataset);

	}

}
