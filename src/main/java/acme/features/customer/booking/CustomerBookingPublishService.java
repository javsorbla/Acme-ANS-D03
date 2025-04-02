
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
import acme.entities.passenger.Passenger;
import acme.realms.customer.Customer;

@GuiService
public class CustomerBookingPublishService extends AbstractGuiService<Customer, Booking> {

	//Internal state ---------------------------------------------

	@Autowired
	private CustomerBookingRepository repository;

	//AbstractGuiService interface -------------------------------


	@Override
	public void authorise() {
		boolean status = super.getRequest().getPrincipal().hasRealmOfType(Customer.class);
		super.getResponse().setAuthorised(status);

		int customerId = super.getRequest().getPrincipal().getActiveRealm().getId();
		int bookingId = super.getRequest().getData("id", int.class);
		Booking booking = this.repository.findBookingById(bookingId);

		//super.getResponse().setAuthorised(customerId == booking.getCustomer().getId());
		super.getResponse().setAuthorised(booking != null && !booking.isPublish() && booking.getCustomer().getId() == customerId);
	}

	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		Booking booking = this.repository.findBookingById(id);

		super.getBuffer().addData(booking);
	}

	@Override
	public void bind(final Booking booking) {
		super.bindObject(booking, "flight", "locatorCode", "travelClass", "price", "lastNibble");
	}

	@Override
	public void validate(final Booking booking) {
		boolean atLeastOnePassenger;
		boolean allPassengerPublished;
		Collection<Passenger> bookingPassengers;

		bookingPassengers = this.repository.findAllPassengersByBookingId(booking.getId());
		atLeastOnePassenger = !bookingPassengers.isEmpty();
		allPassengerPublished = bookingPassengers.stream().allMatch(b -> b.isPublish());

		super.state(atLeastOnePassenger, "*", "acme.validation.booking.publish-no-passengers");
		super.state(allPassengerPublished, "*", "acme.validation.booking.publish-passengers-not-published");
	}

	@Override
	public void perform(final Booking booking) {
		booking.setPurchaseMoment(MomentHelper.getCurrentMoment());
		booking.setPublish(true);
		this.repository.save(booking);
	}

	@Override
	public void unbind(final Booking booking) {
		Dataset dataset;
		SelectChoices typeTravelClasses;
		typeTravelClasses = SelectChoices.from(TypeTravelClass.class, booking.getTravelClass());

		Collection<Flight> flights = this.repository.findAllPublishFlights();
		SelectChoices flightChoices = SelectChoices.from(flights, "id", booking.getFlight());

		dataset = super.unbindObject(booking, "flight", "locatorCode", "purchaseMoment", "travelClass", "price", "lastNibble", "publish", "id");
		dataset.put("travelClasses", typeTravelClasses);
		dataset.put("flights", flightChoices);
		super.getResponse().addData(dataset);
	}

}
