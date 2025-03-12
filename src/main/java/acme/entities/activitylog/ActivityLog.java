
package acme.entities.activitylog;

import java.util.Date;

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
import acme.constraints.ValidActivityLog;
import acme.entities.flightassignment.FlightAssignment;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@ValidActivityLog
public class ActivityLog extends AbstractEntity {

	// Serialisation version --------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Mandatory
	@ValidMoment(past = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date				registrationMoment;

	@Mandatory
	@ValidString(min = 1, max = 50) // min=1 porque es obligatorio
	@Automapped
	private String				incidentType;

	@Mandatory
	@ValidString(min = 1) // min=1 porque es obligatorio, no hace falta max=255 porque es por defecto
	@Automapped
	private String				description;

	@Mandatory
	@ValidNumber(min = 0, max = 10, fraction = 0) // fraction=0 para que no tenga decimales
	@Automapped
	private Integer				severityLevel;

	@Mandatory
	//@Valid
	@Automapped
	private Boolean				publish;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------
	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private FlightAssignment	activityLogAssignment;
}
