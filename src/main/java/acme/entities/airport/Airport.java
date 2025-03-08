
package acme.entities.airport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidEmail;
import acme.client.components.validation.ValidString;
import acme.client.components.validation.ValidUrl;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Airport extends AbstractEntity {
	// Serialisation version --------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------

	@Mandatory
	@ValidString(min = 1, max = 50) // min a 0 por default, se pone uno distinto?
	@Automapped
	private String				name;

	@Mandatory
	@ValidString(pattern = "^[A-Z]{3}$") //Should check min?
	@Column(unique = true)
	private String				iataCode;

	@Mandatory
	@Valid
	@Automapped
	private OperationalScope	operationalScope;

	@Mandatory
	@ValidString(max = 50) // min=1 porque es mandatory?
	@Automapped
	private String				city;

	@Mandatory
	@ValidString(max = 50) // min=1 porque es mandatory?
	@Automapped
	private String				country;

	@Optional
	@ValidUrl
	@Column(unique = true) // De verdad tiene que ser unico?
	private String				website;

	@Optional
	@ValidEmail // min=0 por ser opcional, por defecto es 1
	@Column(unique = true)
	private String				email;

	@Optional
	@ValidString(pattern = "^\\+?\\d{6,15}$")
	@Column(unique = true)
	private String				contactPhone;

	//Runways?

	//Derived attributes-------------------------------------------------

	// Relationships -----------------------------------------------------
}
