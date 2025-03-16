
package acme.features.administrator.airport;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.principals.Administrator;
import acme.client.controllers.AbstractGuiController;
import acme.client.controllers.GuiController;
import acme.entities.airport.Airport;

@GuiController
public class AdministratorAirportController extends AbstractGuiController<Administrator, Airport> {

	//Internal state --------------------------------------------------------------

	@Autowired
	private AdministratorAirportListService		listService;

	@Autowired
	private AdministratorAirportShowService		showService;

	@Autowired
	private AdministratorAirportCreateService	createService;

	//Constructors ----------------------------------------------------------------


	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
	}

}
