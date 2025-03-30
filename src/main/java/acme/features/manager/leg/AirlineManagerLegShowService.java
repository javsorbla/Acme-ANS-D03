
package acme.features.manager.leg;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.aircraft.Aircraft;
import acme.entities.airport.Airport;
import acme.entities.leg.Leg;
import acme.entities.leg.LegStatus;
import acme.realms.manager.AirlineManager;

@GuiService
public class AirlineManagerLegShowService extends AbstractGuiService<AirlineManager, Leg> {
	//Internal state ----------------------------------------------------------

	@Autowired
	private AirlineManagerLegRepository repository;

	//AbstractGuiService state ----------------------------------------------------------


	@Override
	public void authorise() {
		Leg leg;
		int legId;
		int managerId;
		boolean status;

		legId = super.getRequest().getData("id", int.class);
		leg = this.repository.findLegById(legId);
		managerId = super.getRequest().getPrincipal().getActiveRealm().getId();
		status = leg != null && leg.getFlight().getManager().getId() == managerId;

		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {
		Leg leg;
		int id;

		id = super.getRequest().getData("id", int.class);
		leg = this.repository.findLegById(id);

		super.getBuffer().addData(leg);

	}

	@Override
	public void unbind(final Leg leg) {
		SelectChoices status;
		SelectChoices deployedAircraft;
		SelectChoices departureAirport;
		SelectChoices arrivalAirport;
		Collection<Aircraft> aircrafts;
		Collection<Airport> airports;

		Dataset dataset;

		aircrafts = this.repository.findAllAircrafts();
		airports = this.repository.findAllAirports();
		status = SelectChoices.from(LegStatus.class, leg.getStatus());
		deployedAircraft = SelectChoices.from(aircrafts, "registrationNumber", leg.getDeployedAircraft());
		departureAirport = SelectChoices.from(airports, "iataCode", leg.getDepartureAirport());
		arrivalAirport = SelectChoices.from(airports, "iataCode", leg.getArrivalAirport());

		dataset = super.unbindObject(leg, "flightNumber", "departure", "arrival", "publish");
		dataset.put("durationInHours", leg.getDurationInHours());
		dataset.put("status", status);
		dataset.put("deployedAircraft", deployedAircraft);
		dataset.put("departureAirport", departureAirport);
		dataset.put("arrivalAirport", arrivalAirport);

		super.getResponse().addData(dataset);
	}
}
