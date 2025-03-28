
package acme.features.manager.flight;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.services.GuiService;
import acme.entities.flight.Flight;
import acme.realms.manager.AirlineManager;

@GuiService
public class AirlineManagerUpdateService extends AbstractService<AirlineManager, Flight> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AirlineManagerFlightRepository repository;


	// AbstractGuiService interface -------------------------------------------
	@Override
	public void authorise() {
		Flight flight;
		int flightId;
		int managerId;
		boolean owned;

		flightId = super.getRequest().getData("id", int.class);
		flight = this.repository.findFlightById(flightId);
		managerId = super.getRequest().getPrincipal().getActiveRealm().getId();
		owned = flight.getManager().getId() == managerId;

		super.getResponse().setAuthorised(owned);
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
	public void bind(final Flight flight) {
		super.bindObject(flight, "tag", "requiresSelfTransfer", "cost", "description");
	}

	@Override
	public void validate(final Flight flight) {
		if (flight.isPublish())
			super.state(false, "publish", "airline-manager.flight.error.update-published");
	}

	@Override
	public void perform(final Flight flight) {
		assert flight != null;

		this.repository.save(flight);
	}

	@Override
	public void unbind(final Flight flight) {
		Dataset dataset;

		dataset = super.unbindObject(flight, "tag", "requiresSelfTransfer", "cost", "description");

		super.getResponse().addData(dataset);
	}
}
