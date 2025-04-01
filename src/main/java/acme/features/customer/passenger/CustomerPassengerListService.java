
package acme.features.customer.passenger;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.booking.Booking;
import acme.entities.passenger.Passenger;
import acme.realms.customer.Customer;

@GuiService
public class CustomerPassengerListService extends AbstractGuiService<Customer, Passenger> {

	//Internal state --------------------------------------------

	@Autowired
	private CustomerPassengerRepository repository;

	//AbstractGuiService interface -------------------------------


	@Override
	public void authorise() {
		boolean status = super.getRequest().getPrincipal().hasRealmOfType(Customer.class);
		super.getResponse().setAuthorised(status);

		if (!super.getRequest().getData().isEmpty()) {
			int customerId = super.getRequest().getPrincipal().getActiveRealm().getId();
			int bookingId = super.getRequest().getData("bookingId", int.class);
			Booking booking = this.repository.findBookingById(bookingId);

			super.getResponse().setAuthorised(customerId == booking.getCustomer().getId());
		}

	}

	@Override
	public void load() {
		Collection<Passenger> passengers;
		int customerId = super.getRequest().getPrincipal().getActiveRealm().getId();

		if (super.getRequest().getData().isEmpty())
			passengers = this.repository.findAllPassengersByCustomerId(customerId);
		else {
			int bookingId = super.getRequest().getData("bookingId", int.class);
			passengers = this.repository.findPassengersFromBooking(bookingId);
		}

		super.getBuffer().addData(passengers);
	}

	@Override
	public void unbind(final Passenger passenger) {
		Dataset dataset;
		dataset = super.unbindObject(passenger, "fullName", "passportNumber", "dateOfBirth", "publish");

		super.getResponse().addData(dataset);
	}

}
