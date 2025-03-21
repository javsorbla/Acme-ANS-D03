
package acme.entities.booking;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.datatypes.Money;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidMoney;
import acme.constraints.ValidBooking;
import acme.constraints.ValidLastNibble;
import acme.constraints.ValidLocatorCode;
import acme.entities.flight.Flight;
import acme.realms.customer.Customer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@ValidBooking
public class Booking extends AbstractEntity {
	// Serialisation version --------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------

	@Mandatory
	@ValidLocatorCode
	@Column(unique = true)
	private String				locatorCode;

	@Mandatory
	@ValidMoment(past = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date				purchaseMoment;

	@Mandatory
	@Valid
	@Automapped
	private TypeTravelClass		travelClass;

	@Mandatory
	@ValidMoney(min = 0.00, max = 1000000.0)
	@Automapped
	private Money				price;

	@Optional
	@ValidLastNibble
	@Automapped
	private String				lastNibble;

	@Mandatory
	//@Valid
	@Automapped
	private boolean				publish; // Attribute needed for future deliverables

	//Derived attributes-------------------------------------------------

	// Relationships -----------------------------------------------------

	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Flight				flightId;

	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Customer			customerId;

}
