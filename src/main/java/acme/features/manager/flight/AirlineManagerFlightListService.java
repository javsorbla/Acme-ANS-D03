
package acme.features.manager.flight;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.flight.Flight;
import acme.realms.manager.AirlineManager;

@GuiService
public class AirlineManagerFlightListService extends AbstractGuiService<AirlineManager, Flight> {

	//Internal state ---------------------------------------------

	@Autowired
	private AirlineManagerFlightRepository repository;

	//AbstractGuiService interface -------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Flight> flights;
		int managerId;

		managerId = super.getRequest().getPrincipal().getActiveRealm().getId();

		flights = this.repository.findAllFlightsByManagerId(managerId);

		super.getBuffer().addData(flights);
	}

	@Override
	public void unbind(final Flight flight) {
		Dataset dataset;

		dataset = super.unbindObject(flight, "tag", "cost", "publish");
		dataset.put("originCity", flight.getOriginCity());
		dataset.put("destinationCity", flight.getDestinationCity());

		super.addPayload(dataset, flight, "description", "numberOfLayovers");

		super.getResponse().addData(dataset);
	}
}
