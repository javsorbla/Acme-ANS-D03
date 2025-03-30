
package acme.features.assistanceagent.claim;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.claims.Claim;
import acme.realms.assistanceagent.AssistanceAgent;

@GuiService
public class AssistanceAgentClaimShowService extends AbstractGuiService<AssistanceAgent, Claim> {

	//Internal state ---------------------------------------------

	@Autowired
	private AssistanceAgentClaimRepository repository;

	//AbstractGuiService interface -------------------------------


	//Seguro?
	@Override
	public void authorise() {
		Claim claim;
		int claimId;
		int clainAgentId;
		int agentId;
		boolean owned;

		claimId = super.getRequest().getData("id", int.class);
		claim = this.repository.findClaimById(claimId);

		agentId = super.getRequest().getPrincipal().getAccountId();
		clainAgentId = claim.getAssistanceAgent().getUserAccount().getId();
		owned = clainAgentId == agentId;

		super.getResponse().setAuthorised(owned);

	}

	@Override
	public void load() {
		Claim claim;
		int claimId;
		Boolean publish;

		claimId = super.getRequest().getData("id", int.class);
		claim = this.repository.findClaimById(claimId);
		publish = claim.getPublish();

		super.getBuffer().addData(claim);

	}

	//Revisar este metodo ya que hay cosas inconclusa 
	@Override
	public void unbind(final Claim claim) {
		Dataset dataset;

		dataset = super.unbindObject(claim, "registrationMoment", "passengerEmail", "description", "type");//como metemos las derivadas?
		//dataset.put("indicator", claim.getIndicator());

		super.addPayload(dataset, claim, "publish");

		super.getResponse().addData(dataset);

	}

}
