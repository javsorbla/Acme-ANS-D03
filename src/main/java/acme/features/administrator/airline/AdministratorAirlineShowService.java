
package acme.features.administrator.airline;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.principals.Administrator;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.airline.Airline;
import acme.entities.airline.AirlineType;

@GuiService
public class AdministratorAirlineShowService extends AbstractGuiService<Administrator, Airline> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private AdministratorAirlineRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		int airlineId;
		Airline airline;

		airlineId = super.getRequest().getData("id", int.class);
		airline = this.repository.findAirlineById(airlineId);

		super.getBuffer().addData(airline);
	}

	@Override
	public void validate(final Airline airline) {
		;
	}

	@Override
	public void unbind(final Airline airline) {
		SelectChoices choicesAirlineTypes;
		Dataset dataset;

		choicesAirlineTypes = SelectChoices.from(AirlineType.class, airline.getType());

		dataset = super.unbindObject(airline, "name", "iataCode", "type", "foundationMoment", "webSite", "email", "phoneNumber");
		dataset.put("types", choicesAirlineTypes);
		dataset.put("confirmation", false);

		super.getResponse().addData(dataset);

	}
}
