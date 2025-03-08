
package acme.realms.flightcrewmember;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import acme.client.components.basis.AbstractRole;
import acme.client.components.datatypes.Money;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoney;
import acme.client.components.validation.ValidNumber;
import acme.client.components.validation.ValidString;
import acme.entities.aircraft.Aircraft;
import acme.entities.airline.Airline;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class FlightCrewMember extends AbstractRole {

	// Serialisation version --------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Mandatory Attributes -------------------------------------------------------------

	@Mandatory
	@ValidString(pattern = "^[A-Z]{2,3}\\d{6}$") // min y max ya vienen en el pattern?
	@Column(unique = true)
	private String				employeeCode;

	@Mandatory
	@ValidString(pattern = "^\\+?\\d{6,15}$") // min y max ya vienen en el pattern?
	@Automapped // puede ser Column(unique=true)
	private String				phoneNumber;

	@Mandatory
	@ValidString(min = 1) // min=1 porque no puede estar vacio, no hace falta max = 255 porque es el por defecto
	@Automapped
	private String				languageSkills;

	@Mandatory
	@Valid
	@Automapped
	private AvailabilityStatus	availabilityStatus;

	@Mandatory
	@ValidMoney(min = 0.00, max = 1000000.00)
	@Automapped
	private Money				salary;

	// Optional Attributes -------------------------------------------------------------

	@Optional
	@ValidNumber(min = 0, max = 120, fraction = 0) // fraction puede que no haga falta?
	@Automapped
	private Integer				experienceYears;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------
	@Mandatory
	@Valid
	@ManyToOne(optional = false) // segun el foro es una relacion
	private Airline				flightCrewMemberAirline;

	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Aircraft			flightCrewMemberAircraft; // sobra?

}
