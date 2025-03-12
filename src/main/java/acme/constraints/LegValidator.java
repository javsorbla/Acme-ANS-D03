
package acme.constraints;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.validation.ConstraintValidatorContext;

import acme.client.components.validation.AbstractValidator;
import acme.client.helpers.MomentHelper;
import acme.entities.leg.Leg;

public class LegValidator extends AbstractValidator<ValidLeg, Leg> {

	// Internal State ----------------------------------------------------

	// Initialiser -------------------------------------------------------

	@Override
	public void initialise(final ValidLeg annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Leg leg, final ConstraintValidatorContext context) {

		assert context != null;

		boolean result;

		if (leg == null || leg.getArrival() == null || leg.getDeparture() == null)
			super.state(context, false, "*", "javax.validation.constraints.NotNull.message");
		else {
			boolean departureIsFuture = MomentHelper.isPresentOrFuture(leg.getDeparture());
			super.state(context, departureIsFuture, "departure", "acme.validation.leg.departure.message");

			boolean arrivalIsAfterDeparture;
			//Using delta = 1 minute and considering duration is a derived attribute
			//Solution using framework, but is using ChronoUnit correct?
			Date minMoment = MomentHelper.deltaFromMoment(leg.getDeparture(), 1, ChronoUnit.MINUTES);
			//Date minMoment = new Date(leg.getDeparture().getTime() + 1 * 60 * 1000); //without framework
			arrivalIsAfterDeparture = MomentHelper.isAfterOrEqual(leg.getArrival(), minMoment);
			super.state(context, arrivalIsAfterDeparture, "arrival", "acme.validation.leg.arrival.message");
		}

		result = !super.hasErrors(context);
		return result;

	}

}
