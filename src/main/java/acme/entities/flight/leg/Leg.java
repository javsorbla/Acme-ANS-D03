
package acme.entities.flight.leg;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidNumber;
import acme.client.components.validation.ValidString;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Leg extends AbstractEntity {

	// Serialisation version --------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------

	@Mandatory
	@ValidString(pattern = "^[A-Z]{3}\\d{4}$") //Alternativa: Crear ValidFlightNumber
	@Column(unique = true)
	private String				flightNumber;

	@Mandatory
	@ValidMoment
	@Temporal(TemporalType.TIMESTAMP)
	private Date				departure;

	@Mandatory
	@ValidMoment
	@Temporal(TemporalType.TIMESTAMP)
	private Date				arrival;

	@Mandatory
	@ValidNumber(min = 0)
	@Automapped
	private double				duration; // In hours

	@Mandatory
	@Valid
	@Automapped
	private LegStatus			status;

	@Mandatory
	//@Valid
	@Automapped
	private boolean				publish;

	//Derived attributes

	// Relationships -----------------------------------------------------

	// @Mandatory
	// @Valid
	// @ManyToOne(optional = false)
	// Airport departureAirport

	// @Mandatory
	// @Valid
	// @ManyToOne(optional = false)
	// @JoinColumn(name = "departure_airport_id", nullable = false)
	// Airport arrivalAirport

	// @Mandatory
	// @Valid
	// @ManyToOne(optional = false)
	// @JoinColumn(name = "arrival_airport_id", nullable = false)
	// Aircraft deployedAircraft

}
