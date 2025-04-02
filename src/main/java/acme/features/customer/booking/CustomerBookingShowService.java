
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
public class CustomerBookingShowService extends AbstractGuiService<Customer, Booking> {

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

		super.getResponse().setAuthorised(customerId == booking.getCustomer().getId());
	}

	@Override
	public void load() {
		Booking booking;
		int id = super.getRequest().getData("id", int.class);
		booking = this.repository.findBookingById(id);

		super.getBuffer().addData(booking);
	}

	@Override
	public void unbind(final Booking booking) {
		Dataset dataset;
		SelectChoices typeTravelClasses = SelectChoices.from(TypeTravelClass.class, booking.getTravelClass());
		Collection<Flight> publishFlights = this.repository.findAllPublishFlights();
		Collection<Flight> publishFutureFlights = publishFlights.stream().filter(f -> MomentHelper.isBefore(booking.getPurchaseMoment(), f.getScheduledDeparture())).toList();
		SelectChoices flightChoices = SelectChoices.from(publishFutureFlights, "id", booking.getFlight());

		dataset = super.unbindObject(booking, "locatorCode", "purchaseMoment", "travelClass", "price", "lastNibble", "publish", "id");
		dataset.put("travelClasses", typeTravelClasses);
		dataset.put("flights", flightChoices);

		super.getResponse().addData(dataset);
	}

}
