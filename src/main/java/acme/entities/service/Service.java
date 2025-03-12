
package acme.entities.service;

import javax.persistence.Column;
import javax.persistence.Entity;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.datatypes.Money;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoney;
import acme.client.components.validation.ValidNumber;
import acme.client.components.validation.ValidString;
import acme.client.components.validation.ValidUrl;
import acme.constraints.ValidPromotionCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Service extends AbstractEntity {
	// Serialisation version --------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------

	@Mandatory
	@ValidString(min = 1, max = 50)
	@Automapped
	private String				name;

	@Mandatory
	@ValidUrl
	@Automapped
	private String				pictureUrl;

	@Mandatory
	@ValidNumber(min = 1, max = 100, integer = 3, fraction = 2)
	@Automapped
	private double				averageDwellTime;

	@Optional
	@ValidPromotionCode
	@Column(unique = true)
	private String				promoCode;

	@Optional
	@ValidMoney(min = 1.0, max = 1000000.0)
	@Automapped
	private Money				discountAmount; //Tipo Money, tal y como se nos indicó en el follow-up del 11/03/2025

	//Derived attributes-------------------------------------------------

	// Relationships -----------------------------------------------------
}
