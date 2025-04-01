
package acme.features.manager.leg;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.aircraft.Aircraft;
import acme.entities.airport.Airport;
import acme.entities.leg.Leg;
import acme.entities.leg.LegStatus;
import acme.realms.manager.AirlineManager;

@GuiService
public class AirlineManagerLegPublishService extends AbstractGuiService<AirlineManager, Leg> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AirlineManagerLegRepository repository;


	// AbstractGuiService interface -------------------------------------------
	@Override
	public void authorise() {
		Leg leg;
		int legId;
		int managerId;
		boolean status;

		legId = super.getRequest().getData("id", int.class);
		leg = this.repository.findLegById(legId);
		managerId = super.getRequest().getPrincipal().getActiveRealm().getId();
		status = leg != null && !leg.isPublish() && leg.getFlight().getManager().getId() == managerId;

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
	public void bind(final Leg leg) {
		super.bindObject(leg, "flightNumber", "departure", "arrival", "status", "departureAirport", "arrivalAirport", "deployedAircraft", "durationInHours");
	}

	@Override
	public void validate(final Leg leg) {
		int flightId;
		boolean notOverlapping;//No solopada con el resto de legs
		boolean aircraftNotUsed; //Avión no usado en otro leg concurrentemente
		// boolean activeAircraft // Avión en estado activo
		Collection<Leg> flightLegs;
		flightId = leg.getFlight().getId();
		flightLegs = this.repository.findAllLegsByFlightId(flightId);
		//No es lo más optimo, con una query como la del aircraft lo sería más
		notOverlapping = leg.getStatus() == LegStatus.CANCELLED //Si el leg está cancelado, da igual que haya overlap 
			|| flightLegs.stream().filter(l -> !l.equals(leg) && l.isPublish() && l.getStatus() != LegStatus.CANCELLED) //Solo importa que no se solape con los ya publicados y no cancelados
				.noneMatch(l -> MomentHelper.isInRange(leg.getDeparture(), l.getDeparture(), l.getArrival()) || MomentHelper.isInRange(leg.getArrival(), l.getDeparture(), l.getArrival()));

		super.state(notOverlapping, "departure", "airline-manager.leg.form.validation.overlapped");
		super.state(notOverlapping, "arrival", "airline-manager.leg.form.validation.overlapped");

		Integer aircraftId;
		aircraftId = leg.getDeployedAircraft() != null ? leg.getDeployedAircraft().getId() : null;
		Integer numberOfLegsDeployingAircraft = this.repository.findNumberOfLegsDeployingSameAircraft(leg.getDeparture(), leg.getArrival(), LegStatus.CANCELLED, aircraftId);
		aircraftNotUsed = leg.getStatus() == LegStatus.CANCELLED || numberOfLegsDeployingAircraft == 0; //Si el leg se ha cancelado no importa que se asigne un aircraft usado

		super.state(aircraftNotUsed, "deployedAircraft", "airline-manaeg.leg.form.validation.used-aircraft");
	}

	@Override
	public void perform(final Leg leg) {
		leg.setPublish(true);
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

		Dataset dataset;

		choicesStatuses = SelectChoices.from(LegStatus.class, leg.getStatus());
		aircrafts = this.repository.findAllAircrafts();
		choicesAircrafts = SelectChoices.from(aircrafts, "registrationNumber", leg.getDeployedAircraft());

		airports = this.repository.findAllAirports();
		choicesDepartureAirport = SelectChoices.from(airports, "iataCode", leg.getDepartureAirport());
		choicesArrivalAirport = SelectChoices.from(airports, "iataCode", leg.getArrivalAirport());

		dataset = super.unbindObject(leg, "flightNumber", "departure", "arrival", "publish");
		dataset.put("durationInHours", leg.getDurationInHours());
		dataset.put("statuses", choicesStatuses);
		dataset.put("departureAirports", choicesDepartureAirport);
		dataset.put("arrivalAirports", choicesArrivalAirport);
		dataset.put("aircrafts", choicesAircrafts);

		super.getResponse().addData(dataset);
	}
}
