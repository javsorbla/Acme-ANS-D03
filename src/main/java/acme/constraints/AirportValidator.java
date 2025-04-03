
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.entities.airport.Airport;
import acme.entities.airport.AirportRepository;

public class AirportValidator extends AbstractValidator<ValidAirport, Airport> {

	// Internal state ---------------------------------------------------------

	@Autowired
	AirportRepository repository;

	// Initialiser ------------------------------------------------------------


	@Override
	public void initialise(final ValidAirport annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Airport airport, final ConstraintValidatorContext context) {
		// HINT: value can be null
		assert context != null;

		boolean result;

		if (airport != null && airport.getIataCode() != null) {
			boolean uniqueAirport;
			Airport existingAirport;

			existingAirport = this.repository.findAirportByIataCode(airport.getIataCode());
			uniqueAirport = existingAirport == null || existingAirport.equals(airport);
			super.state(context, uniqueAirport, "iataCode", "acme.validation.airport.iata.code.duplicated.message");

		}

		result = !super.hasErrors(context);
		return result;
	}
}
