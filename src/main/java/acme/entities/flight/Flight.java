
package acme.entities.flight;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.datatypes.Money;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoney;
import acme.client.components.validation.ValidString;

public class Flight extends AbstractEntity {

	// Serialisation version --------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------

	@Mandatory
	@ValidString(min = 1, max = 50)
	@Automapped // unico?
	String						tag;

	@Mandatory
	//@Valid
	@Automapped
	boolean						selfTransfer;

	@Mandatory
	@ValidMoney //By default -> (min = 0.00, max = 1000000.00)
	@Automapped
	Money						cost;

	@Optional
	@ValidString
	@Automapped
	String						description;

	//@Mandatory
	//@Valid
	//@Automapped
	//private boolean				publish; // Attribute needed for future deliverables

	//Derived attributes-------------------------------------------------

	// Relationships -----------------------------------------------------

	//@Mandatory
	//@Valid
	//@ManyToOne(optional = false)
	//private AirlineManager manager;
}
