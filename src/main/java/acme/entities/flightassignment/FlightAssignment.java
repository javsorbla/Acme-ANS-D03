
package acme.entities.flightassignment;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidString;
// import acme.realms.flightcrewmember.FlightCrewMember;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class FlightAssignment extends AbstractEntity {
	// Serialisation version --------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------

	@Mandatory
	@Valid
	@Automapped
	private Duty				duty;

	@Mandatory
	@ValidMoment(min = "2000/01/01 00:00:00", past = true) // max es hace 1 segundo porque past=true
	@Temporal(TemporalType.TIMESTAMP)
	private Date				lastUpdateMoment;

	@Mandatory
	@Valid
	@Automapped
	private CurrentStatus		currentStatus;

	// Optional Attributes -------------------------------------------------------------

	@Optional
	@ValidString(min = 0) // min=0 porque es opcional, no hace falta max=255 porque es por defecto
	@Automapped
	private String				remarks;

	//Derived attributes-------------------------------------------------

	// Relationships -----------------------------------------------------

	//@Mandatory
	//@Valid
	//@ManyToOne(optional = false)
	//private FlightCrewMember	flightAssignmentCrewMember;

	//@Mandatory
	//@Valid
	//@ManyToOne(optional = false)
	//private Leg					flightAssignmentLeg;
}
