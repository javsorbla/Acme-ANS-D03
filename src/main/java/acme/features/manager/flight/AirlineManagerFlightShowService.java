
package acme.features.manager.flight;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.airline.Airline;
import acme.entities.flight.Flight;
import acme.realms.manager.AirlineManager;

@GuiService
public class AirlineManagerFlightShowService extends AbstractGuiService<AirlineManager, Flight> {
	//Internal state ----------------------------------------------------------

	@Autowired
	private AirlineManagerFlightRepository repository;

	//AbstractGuiService state ----------------------------------------------------------


	@Override
	public void authorise() {
		Flight flight;
		int flightId;
		int managerId;
		boolean status;

		flightId = super.getRequest().getData("id", int.class);
		flight = this.repository.findFlightById(flightId);
		managerId = super.getRequest().getPrincipal().getActiveRealm().getId();
		status = flight != null && flight.getManager().getId() == managerId;

		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {
		Flight flight;
		int id;

		id = super.getRequest().getData("id", int.class);
		flight = this.repository.findFlightById(id);

		super.getBuffer().addData(flight);

	}

	@Override
	public void unbind(final Flight flight) {
		Dataset dataset;
		SelectChoices choicesAirline;
		Collection<Airline> airlines;

		airlines = this.repository.findAllAirlines();
		choicesAirline = SelectChoices.from(airlines, "iataCode", flight.getAirline());

		dataset = super.unbindObject(flight, "tag", "requiresSelfTransfer", "cost", "description", "publish");
		dataset.put("scheduledDeparture", flight.getScheduledDeparture());
		dataset.put("scheduledArrival", flight.getScheduledArrival());
		dataset.put("originCity", flight.getOriginCity());
		dataset.put("destinationCity", flight.getDestinationCity());
		dataset.put("numberOfLayovers", flight.getNumberOfLayovers());
		dataset.put("airlines", choicesAirline);

		super.getResponse().addData(dataset);
	}
}
