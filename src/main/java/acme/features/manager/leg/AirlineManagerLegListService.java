
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
		int flightId;
		int managerId;
		Flight flight;
		boolean status;

		flightId = super.getRequest().getData("flightId", int.class);
		managerId = super.getRequest().getPrincipal().getActiveRealm().getId();
		flight = this.repository.findFlightById(flightId);
		status = flight != null && flight.getManager().getId() == managerId;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Leg> legs;
		int flightId;

		flightId = super.getRequest().getData("flightId", int.class);
		legs = this.repository.findAllLegsByFlightId(flightId);

		super.getBuffer().addData(legs);
	}

	@Override
	public void unbind(final Leg leg) {
		Dataset dataset;

		dataset = super.unbindObject(leg, "flightNumber", "departure", "arrival", "status");

		super.addPayload(dataset, leg, "publish");

		super.getResponse().addData(dataset);
	}

	@Override
	public void unbind(final Collection<Leg> legs) {
		int flightId;
		Flight flight;
		final boolean showCreate;

		flightId = super.getRequest().getData("flightId", int.class);
		flight = this.repository.findFlightById(flightId);
		showCreate = !flight.isPublish() && super.getRequest().getPrincipal().hasRealm(flight.getManager());

		super.getResponse().addGlobal("flightId", flightId);
		super.getResponse().addGlobal("showCreate", showCreate);
	}
}
