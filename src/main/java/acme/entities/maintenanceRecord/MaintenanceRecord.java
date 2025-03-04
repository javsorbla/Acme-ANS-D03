
package acme.entities.maintenanceRecord;

import java.util.Date;

import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidNumber;
import acme.client.components.validation.ValidString;
import acme.entities.aircraft.Aircraft;

public class MaintenanceRecord extends AbstractEntity {
	// Serialisation version --------------------------------------------------

	private static final long		serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Mandatory
	@ValidMoment
	@Temporal(TemporalType.TIMESTAMP)
	private Date					moment;

	@Mandatory
	@Valid
	@Automapped
	private MaintenanceRecordStatus	status;

	@Mandatory
	@ValidMoment
	@Temporal(TemporalType.TIMESTAMP)
	private Date					nextInspectionDate;

	@Mandatory
	@ValidNumber(min = 0)
	@Automapped
	private Integer					estimatedCost;

	// @Optional Attributes -------------------------------------------------------------

	@Optional
	@ValidString(max = 255)
	@Automapped
	private String					notes;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

	@Mandatory
	@Valid
	@ManyToOne
	private Aircraft				aircraft;
}
