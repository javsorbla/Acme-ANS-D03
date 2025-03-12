
package acme.entities.leg;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.ValidMoment;
import acme.constraints.ValidFlightNumber;
import acme.constraints.ValidLeg;
import acme.entities.aircraft.Aircraft;
import acme.entities.airport.Airport;
import acme.realms.AirlineManager;
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
	@ValidFlightNumber
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
	@Valid
	@Automapped
	private LegStatus			status;

	@Mandatory
	//@Valid
	@Automapped
	private boolean				publish; // Attribute needed for future deliverables

	//Derived attributes-------------------------------------------------


	@Transient // javax.persistence
	public Double getDurationInHours() {
		double durationInMs = this.getArrival().getTime() - this.getDeparture().getTime();
		double durationInH = durationInMs / (1000 * 60 * 60);
		return durationInH;
	}

	// Relationships -----------------------------------------------------


	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Aircraft		deployedAircraft;

	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Airport			departureAirport;

	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Airport			arrivalAirport;

	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Flight			flight;

	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private AirlineManager	manager;

}
