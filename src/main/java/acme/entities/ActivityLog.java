
package acme.entities;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.ValidNumber;
import acme.client.components.validation.ValidString;
// import acme.entities.leg.Leg;
import acme.constraints.ValidRegistrationMoment;

public class ActivityLog extends AbstractEntity {

	// Serialisation version --------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Mandatory
	@ValidRegistrationMoment
	@Temporal(TemporalType.TIMESTAMP)
	private Date				registrationMoment;

	@Mandatory
	@ValidString(max = 50)
	@Automapped
	private String				incidentType;

	@Mandatory
	@ValidString // no hace falta max=255 porque es por defecto
	@Automapped
	private String				description;

	@Mandatory
	@ValidNumber(min = 0, max = 10, fraction = 0) // fraction=0 para que no tenga decimales
	@Automapped
	private Integer				severityLevel;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------
	//@Mandatory
	//@Valid
	//@ManyToOne(optional = false)
	//private FlightAssignment activityLogAssignment;

	//@Mandatory
	//@Valid
	//@OneToOne(optional = false)
	//private Leg					activityLogLeg;
}
