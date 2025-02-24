
package acme.entities.airline;

import java.util.Date;

import javax.persistence.Entity;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidUrl;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Airline extends AbstractEntity {

	// Serialisation version --------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Mandatory Attributes -------------------------------------------------------------

	@Mandatory
	private String				name;

	@Mandatory
	private String				iataCode;

	@Mandatory
	@ValidUrl
	private String				webSite;

	@Mandatory
	private AirlineType			type;

	@Mandatory
	@ValidMoment(past = true)
	private Date				foundationMoment;

	// @Optional Attributes -------------------------------------------------------------

	@Optional
	private String				email;

	@Optional
	private String				phone;

	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------

}
