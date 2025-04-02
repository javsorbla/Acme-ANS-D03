
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.entities.airline.Airline;
import acme.entities.airline.AirlineRepository;

public class AirlineValidator extends AbstractValidator<ValidAirline, Airline> {

	// Internal state ---------------------------------------------------------

	@Autowired
	AirlineRepository repository;

	// Initialiser ------------------------------------------------------------


	@Override
	public void initialise(final ValidAirline annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Airline airline, final ConstraintValidatorContext context) {
		// HINT: value can be null
		assert context != null;

		boolean result;

		if (airline != null && airline.getIataCode() != null) {
			boolean uniqueAirline;
			Airline existingAirline;

			existingAirline = this.repository.findAirlineByIataCode(airline.getIataCode());
			uniqueAirline = existingAirline == null || existingAirline.equals(airline);
			super.state(context, uniqueAirline, "iataCode", "acme.validation.airline.iata.code.duplicated.message");

		}

		result = !super.hasErrors(context);
		return result;
	}
}
