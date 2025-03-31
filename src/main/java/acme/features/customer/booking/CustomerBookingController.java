
package acme.features.customer.booking;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.controllers.AbstractGuiController;
import acme.client.controllers.GuiController;
import acme.entities.booking.Booking;
import acme.realms.customer.Customer;

@GuiController
public class CustomerBookingController extends AbstractGuiController<Customer, Booking> {

	//Internal state --------------------------------------------------------------

	@Autowired
	protected CustomerBookingListService	listService;

	@Autowired
	protected CustomerBookingShowService	showService;

	@Autowired
	private CustomerBookingCreateService	createService;

	//Constructors ----------------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
	}
}
