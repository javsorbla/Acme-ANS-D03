
package acme.features.manager.leg;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.aircraft.Aircraft;
import acme.entities.airport.Airport;
import acme.entities.flight.Flight;
import acme.entities.leg.Leg;
import acme.entities.leg.LegStatus;
import acme.realms.manager.AirlineManager;

@GuiService
public class AirlineManagerLegCreateService extends AbstractGuiService<AirlineManager, Leg> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private AirlineManagerLegRepository repository;


	// AbstractGuiService interface -------------------------------------------
	@Override
	public void authorise() {
		Flight flight;
		int flightId;
		int managerId;
		boolean status;

		flightId = super.getRequest().getData("flightId", int.class);
		flight = this.repository.findFlightById(flightId);
		managerId = super.getRequest().getPrincipal().getActiveRealm().getId();
		status = flight != null && !flight.isPublish() && flight.getManager().getId() == managerId;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Leg leg;
		int flightId;
		Flight flight;

		flightId = super.getRequest().getData("flightId", int.class);
		flight = this.repository.findFlightById(flightId);

		leg = new Leg();
		leg.setFlight(flight);
		leg.setPublish(false);

		super.getBuffer().addData(leg);
	}

	@Override
	public void bind(final Leg leg) {
		super.bindObject(leg, "flightNumber", "departure", "arrival", "status", "departureAirport", "arrivalAirport", "deployedAircraft");
	}

	@Override
	public void validate(final Leg leg) {
		;
	}

	@Override
	public void perform(final Leg leg) {
		this.repository.save(leg);
	}

	@Override
	public void unbind(final Leg leg) {
		SelectChoices choicesStatuses;
		SelectChoices choicesAircrafts;
		SelectChoices choicesDepartureAirport;
		SelectChoices choicesArrivalAirport;
		Collection<Aircraft> aircrafts;
		Collection<Airport> airports;
		int flightId;

		Dataset dataset;

		choicesStatuses = SelectChoices.from(LegStatus.class, leg.getStatus());
		aircrafts = this.repository.findAllAircrafts();
		choicesAircrafts = SelectChoices.from(aircrafts, "registrationNumber", leg.getDeployedAircraft());

		airports = this.repository.findAllAirports();
		choicesDepartureAirport = SelectChoices.from(airports, "iataCode", leg.getDepartureAirport());
		choicesArrivalAirport = SelectChoices.from(airports, "iataCode", leg.getArrivalAirport());

		flightId = super.getRequest().getData("flightId", int.class);

		dataset = super.unbindObject(leg, "flightNumber", "departure", "arrival");
		dataset.put("statuses", choicesStatuses);
		dataset.put("departureAirports", choicesDepartureAirport);
		dataset.put("arrivalAirports", choicesArrivalAirport);
		dataset.put("aircrafts", choicesAircrafts);
		dataset.put("flightId", flightId);

		super.getResponse().addData(dataset);
	}
}
