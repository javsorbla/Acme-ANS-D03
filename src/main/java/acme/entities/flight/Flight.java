
package acme.entities.flight;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.datatypes.Money;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoney;
import acme.client.components.validation.ValidString;
import acme.client.helpers.SpringHelper;
import acme.constraints.ValidFlight;
import acme.realms.manager.AirlineManager;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@ValidFlight
public class Flight extends AbstractEntity {

	// Serialisation version --------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------

	@Mandatory
	@ValidString(min = 1, max = 50)
	@Automapped
	private String				tag;

	@Mandatory
	//@Valid
	@Automapped
	private boolean				requiresSelfTransfer;

	@Mandatory
	@ValidMoney //By default -> (min = 0.00, max = 1000000.00)
	@Automapped
	private Money				cost;

	@Optional
	@ValidString
	@Automapped
	private String				description;

	@Mandatory
	//@Valid
	@Automapped
	private boolean				publish;

	//Derived attributes-------------------------------------------------


	@Transient
	public Date getScheduledDeparture() {
		Date departure;
		FlightRepository repository;

		repository = SpringHelper.getBean(FlightRepository.class);
		departure = repository.getScheduledDeparture(this.getId());

		return departure;
	}

	@Transient
	public Date getScheduledArrival() {
		Date arrival;
		FlightRepository repository;

		repository = SpringHelper.getBean(FlightRepository.class);
		arrival = repository.getScheduledArrival(this.getId());

		return arrival;
	}

	@Transient
	public String getOriginCity() {
		String city;
		FlightRepository repository;

		repository = SpringHelper.getBean(FlightRepository.class);
		city = repository.getOriginCity(this.getId());

		return city;
	}

	@Transient
	public String getDestinationCity() {
		String city;
		FlightRepository repository;

		repository = SpringHelper.getBean(FlightRepository.class);
		city = repository.getDestinationCity(this.getId());

		return city;
	}

	@Transient
	public Integer getNumberOfLayovers() {
		Integer legs;
		FlightRepository repository;

		repository = SpringHelper.getBean(FlightRepository.class);
		legs = repository.getNumberOfLegs(this.getId());

		return legs != null && legs > 0 ? legs - 1 : 0;
	}

	// Relationships -----------------------------------------------------


	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private AirlineManager manager;
}
