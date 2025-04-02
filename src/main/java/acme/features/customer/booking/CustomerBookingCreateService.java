
package acme.features.customer.booking;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.booking.Booking;
import acme.entities.booking.TypeTravelClass;
import acme.entities.flight.Flight;
import acme.realms.customer.Customer;

@GuiService
public class CustomerBookingCreateService extends AbstractGuiService<Customer, Booking> {

	//Internal state ---------------------------------------------

	@Autowired
	private CustomerBookingRepository repository;

	//AbstractGuiService interface -------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Customer customer = (Customer) super.getRequest().getPrincipal().getActiveRealm();
		Booking booking;

		booking = new Booking();
		booking.setPurchaseMoment(MomentHelper.getCurrentMoment());
		booking.setPublish(false);
		booking.setCustomer(customer);

		super.getBuffer().addData(booking);
	}

	@Override
	public void bind(final Booking booking) {
		super.bindObject(booking, "flight", "locatorCode", "travelClass", "price", "lastNibble");
	}

	@Override
	public void validate(final Booking booking) {

	}

	@Override
	public void perform(final Booking booking) {
		this.repository.save(booking);
	}

	@Override
	public void unbind(final Booking booking) {
		Dataset dataset;
		SelectChoices travelClasses = SelectChoices.from(TypeTravelClass.class, booking.getTravelClass());
		Collection<Flight> publishFlights = this.repository.findAllPublishFlights();
		Collection<Flight> publishFutureFlights = publishFlights.stream().filter(f -> MomentHelper.isBefore(booking.getPurchaseMoment(), f.getScheduledDeparture())).toList();
		SelectChoices flightChoices = SelectChoices.from(publishFutureFlights, "id", booking.getFlight());

		dataset = super.unbindObject(booking, "flight", "locatorCode", "travelClass", "lastNibble", "publish", "id");
		dataset.put("travelClasses", travelClasses);
		dataset.put("flights", flightChoices);

		super.getResponse().addData(dataset);

	}
}
