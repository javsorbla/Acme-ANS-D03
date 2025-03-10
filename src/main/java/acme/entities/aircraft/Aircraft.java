
package acme.entities.aircraft;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidNumber;
import acme.client.components.validation.ValidString;
import acme.entities.airline.Airline;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Aircraft extends AbstractEntity {

	// Serialisation version --------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Mandatory Attributes -------------------------------------------------------------

	@Mandatory
	@ValidString(max = 50) // min=1 porque es obligatorio?
	@Automapped
	private String				model;

	@Mandatory
	@ValidString(max = 50) // min=1 porque es obligatorio?
	@Column(unique = true)
	private String				registrationNumber;

	@Mandatory
	@ValidNumber // debe ser min=1, fraction=0
	@Automapped
	private Integer				capacity;

	@Mandatory
	@ValidNumber(min = 2000, max = 50000) // fraction=0?
	@Automapped
	private double				cargoWeight; // es integer

	@Mandatory
	@Valid
	@Automapped
	private AircraftStatus		status;

	// @Optional Attributes -------------------------------------------------------------

	@Optional
	@ValidString(max = 255) // sobra max=255
	@Automapped
	private String				details;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Airline				airline;
}
