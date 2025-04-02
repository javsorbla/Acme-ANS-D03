
package acme.features.assistanceagent.claim;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.claims.Claim;
import acme.entities.claims.ClaimType;
import acme.entities.leg.Leg;
import acme.realms.assistanceagent.AssistanceAgent;

@GuiService
public class AssistanceAgentClaimPublishService extends AbstractGuiService<AssistanceAgent, Claim> {

	//Internal state ---------------------------------------------

	@Autowired
	private AssistanceAgentClaimRepository repository;

	//AbstractGuiService interface -------------------------------


	@Override
	public void authorise() {
		Claim claim;
		int claimId;
		int agentId;
		boolean status;

		claimId = super.getRequest().getData("id", int.class);
		claim = this.repository.findClaimById(claimId);
		agentId = claim == null ? null : super.getRequest().getPrincipal().getActiveRealm().getId();
		status = claim != null && !claim.isPublish() && claim.getAssistanceAgent().getId() == agentId;

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

	//Esta validacion es que para publicar un claim todos sus atributos tienen que estar correctos
	//los tracking logs aqui no afectan?
	@Override
	public void validate(final Claim claim) {
		;
	}

	@Override
	public void perform(final Claim claim) {
		claim.setPublish(true);
		this.repository.save(claim);
	}

	//CUANDO SE ARREGLE EL BUG TEMPORAL SE PODRAN USAR ALGUNA DE LAS LINEAS COMENTADAS 
	@Override
	public void unbind(final Claim claim) {
		Dataset dataset;
		Collection<Leg> legs;
		SelectChoices typesChoices;
		SelectChoices legsChoices;

		Date actualMoment;

		actualMoment = MomentHelper.getCurrentMoment();

		typesChoices = SelectChoices.from(ClaimType.class, claim.getType());
		//legs = this.repository.findAllPublishedLegsBefore(actualMoment);
		//legs = this.repository.findAllPublishedLegs();
		legs = this.repository.findAllLeg();
		legsChoices = SelectChoices.from(legs, "flightNumber", claim.getLeg());

		dataset = super.unbindObject(claim, "registrationMoment", "passengerEmail", "description", "type", "leg", "publish");
		dataset.put("types", typesChoices);
		dataset.put("legs", legsChoices);

		super.getResponse().addData(dataset);
	}

}
