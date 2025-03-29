
package acme.features.manager.leg;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.flight.Flight;
import acme.entities.leg.Leg;
import acme.realms.manager.AirlineManager;

@GuiService
public class AirlineManagerLegListService extends AbstractGuiService<AirlineManager, Leg> {
	//Internal state ---------------------------------------------

	@Autowired
	private AirlineManagerLegRepository repository;

	//AbstractGuiService interface -------------------------------


	@Override
	public void authorise() {
		int masterId;
		int managerId;
		Flight flight;
		boolean owned;

		masterId = super.getRequest().getData("masterId", int.class);
		managerId = super.getRequest().getPrincipal().getActiveRealm().getId();
		flight = this.repository.findFlightById(masterId); //Could also be obtained through one of the legs

		owned = flight.getManager().getId() == managerId;

		super.getResponse().setAuthorised(owned);
	}

	@Override
	public void load() {
		Collection<Leg> legs;
		int masterId;

		masterId = super.getRequest().getData("masterId", int.class);

		legs = this.repository.findAllLegsByFlightId(masterId);

		super.getBuffer().addData(legs);
	}

	@Override
	public void unbind(final Leg leg) {
		Dataset dataset;
		int masterId;

		dataset = super.unbindObject(leg, "flightNumber", "departure", "arrival", "status");
		masterId = super.getRequest().getData("masterId", int.class);

		super.addPayload(dataset, leg, "publish");

		super.getResponse().addGlobal("masterId", masterId);

		super.getResponse().addData(dataset);
	}
}
