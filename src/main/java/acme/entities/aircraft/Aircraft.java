
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
	@ValidString(min = 1, max = 50)
	@Automapped
	private String				model;

	@Mandatory
	@ValidString(min = 1, max = 50)
	@Column(unique = true)
	private String				registrationNumber;

	@Mandatory
	@ValidNumber(min = 1, max = 255, integer = 3) // el máximo es raro pero así se ha establecido en el foro
	@Automapped
	private int					capacity;

	@Mandatory
	@ValidNumber(min = 2000, max = 50000)
	@Automapped
	private double				cargoWeight; // es integer

	@Mandatory
	@Valid
	@Automapped
	private AircraftStatus		status;

	// @Optional Attributes -------------------------------------------------------------

	@Optional
	@ValidString
	@Automapped
	private String				details;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Airline				airline;
}
