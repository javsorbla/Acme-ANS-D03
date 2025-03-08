
package acme.entities.leg;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidNumber;
import acme.client.components.validation.ValidString;
import acme.constraints.ValidLeg;
import acme.entities.aircraft.Aircraft;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@ValidLeg
public class Leg extends AbstractEntity {

	// Serialisation version --------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------

	@Mandatory
	@ValidString(pattern = "^[A-Z]{3}\\d{4}$") //Should check if it contains the IATA code?
	@Column(unique = true)
	private String				flightNumber;

	@Mandatory
	@ValidMoment
	@Temporal(TemporalType.TIMESTAMP)
	private Date				departure; //Should check if departure is before arrival

	@Mandatory
	@ValidMoment
	@Temporal(TemporalType.TIMESTAMP) //After departure
	private Date				arrival;

	@Mandatory
	@ValidNumber(min = 1, max = 1000)
	@Automapped
	private int					duration; // In hours //Derived? -> Time between departure and arrival equals this duration

	@Mandatory
	@Valid
	@Automapped
	private LegStatus			status;

	//@Mandatory
	//@Valid
	//@Automapped
	//private boolean				publish; // Attribute needed for future deliverables

	//Derived attributes-------------------------------------------------

	// Relationships -----------------------------------------------------

	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Aircraft			deployedAircraft;

	// @Mandatory
	// @Valid
	// @ManyToOne(optional = false)
	// private Airport departureAirport

	// @Mandatory
	// @Valid
	// @ManyToOne(optional = false)
	// private Airport arrivalAirport

}
