/*
 * Money.java
 *
 * Copyright (C) 2012-2025 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.datatypes;

import javax.persistence.Embeddable;

import acme.client.components.basis.AbstractDatatype;
import acme.client.components.validation.ValidCurrency;
import acme.client.components.validation.ValidNumber;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Money extends AbstractDatatype {

	// Serialisation version --------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@ValidNumber(integer = 10, fraction = 2)
	private Double				amount;

	@ValidCurrency
	private String				currency;

	// Object interface -------------------------------------------------------


	@Override
	public String toString() {
		StringBuilder result;

		result = new StringBuilder();
		result.append("<<");
		result.append(this.currency);
		result.append(" ");
		result.append(String.format("%.2f", this.amount));
		result.append(">>");

		return result.toString();
	}

}
