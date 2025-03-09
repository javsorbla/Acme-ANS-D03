
package acme.entities.flight;

import javax.persistence.Entity;
import javax.persistence.Transient;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.datatypes.Money;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoney;
import acme.client.components.validation.ValidString;
import acme.client.helpers.SpringHelper;
import acme.entities.leg.Leg;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Flight extends AbstractEntity {

	// Serialisation version --------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------

	@Mandatory
	@ValidString(min = 1, max = 50)
	@Automapped // unico?
	private String				tag;

	@Mandatory
	//@Valid
	@Automapped
	private boolean				selfTransfer;

	@Mandatory
	@ValidMoney //By default -> (min = 0.00, max = 1000000.00)
	@Automapped
	private Money				cost;

	@Optional
	@ValidString
	@Automapped
	private String				description;

	@Mandatory
	//@Valid
	@Automapped
	private boolean				publish; // Attribute needed for future deliverables

	//Derived attributes-------------------------------------------------


	@Transient //Necesario?
	private Leg getFirstLeg() {
		Leg firstLeg;
		FlightRepository flightRepository;

		flightRepository = SpringHelper.getBean(FlightRepository.class);
		firstLeg = flightRepository.getFlightFirstLeg(this.getId());
		return firstLeg;
	}

	// Relationships -----------------------------------------------------

	//@Mandatory
	//@Valid
	//@ManyToOne(optional = false)
	//private AirlineManager manager;
}
