
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
import acme.entities.ActivityLog;
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
	@ValidString(pattern = "^[A-Z]{2-3}\\d{6}$")
	@Column(unique = true)
	private String				employeeCode;

	@Mandatory
	@ValidString(pattern = "^\\+?\\d{6,15}$")
	@Automapped
	private String				phoneNumber;

	@Mandatory
	@ValidString(max = 255)
	@Automapped
	private String				languageSkills;

	@Mandatory
	@Valid
	@Automapped
	private AvailabilityStatus	availabilityStatus;

	@Mandatory
	@Valid
	@Automapped // por confirmar si necesita relacion o no
	private Airline				airline; // a lo mejor se pasa como un string

	@Mandatory
	@ValidMoney
	@Automapped
	private Money				salary;

	// @Optional Attributes -------------------------------------------------------------

	@Optional
	@ValidNumber(fraction = 0)
	@Automapped
	private Integer				experienceYears;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------
	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Aircraft			flightCrewMemberAircraft;

	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private ActivityLog			flightCrewMemberActivityLog;

}
