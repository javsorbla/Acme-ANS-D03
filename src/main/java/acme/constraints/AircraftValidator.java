
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.entities.aircraft.Aircraft;
import acme.entities.aircraft.AircraftRepository;

public class AircraftValidator extends AbstractValidator<ValidAircraft, Aircraft> {

	// Internal state ---------------------------------------------------------

	@Autowired
	AircraftRepository repository;

	// Initialiser ------------------------------------------------------------


	@Override
	public void initialise(final ValidAircraft annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Aircraft aircraft, final ConstraintValidatorContext context) {
		// HINT: value can be null
		assert context != null;

		boolean result;

		if (aircraft != null && aircraft.getRegistrationNumber() != null) {
			boolean uniqueAircraft;
			Aircraft existingAircraft;

			existingAircraft = this.repository.findAircraftByRegistrationNumber(aircraft.getRegistrationNumber());
			uniqueAircraft = existingAircraft == null || existingAircraft.equals(aircraft);
			super.state(context, uniqueAircraft, "registrationNumber", "acme.validation.aircraft.registration.number.duplicated.message");

		}

		result = !super.hasErrors(context);
		return result;
	}
}
