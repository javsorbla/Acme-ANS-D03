
package acme.constraints;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.helpers.MomentHelper;
import acme.client.helpers.StringHelper;
import acme.entities.leg.Leg;
import acme.entities.leg.LegRepository;

public class LegValidator extends AbstractValidator<ValidLeg, Leg> {

	// Internal State ----------------------------------------------------

	@Autowired
	private LegRepository repository;

	// Initialiser -------------------------------------------------------


	@Override
	public void initialise(final ValidLeg annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Leg leg, final ConstraintValidatorContext context) {

		assert context != null;

		boolean result;

		if (leg == null)
			super.state(context, false, "*", "javax.validation.constraints.NotNull.message");
		else {
			if (leg.getDeparture() != null) {
				boolean departureIsFuture = MomentHelper.isPresentOrFuture(leg.getDeparture());
				super.state(context, departureIsFuture, "departure", "acme.validation.leg.departure.message");

				if (leg.getArrival() != null) {
					boolean arrivalIsAfterDeparture;
					//Using delta = 1 minute and considering duration is a derived attribute
					//Solution using framework, but is using ChronoUnit correct?
					Date minMoment = MomentHelper.deltaFromMoment(leg.getDeparture(), 1, ChronoUnit.MINUTES);
					//Date minMoment = new Date(leg.getDeparture().getTime() + 1 * 60 * 1000); //without framework
					arrivalIsAfterDeparture = MomentHelper.isAfterOrEqual(leg.getArrival(), minMoment);
					super.state(context, arrivalIsAfterDeparture, "arrival", "acme.validation.leg.arrival.message");
				}
			}

			if (leg.getFlightNumber() != null) {

				if (leg.getDeployedAircraft() != null) {
					boolean containsIATA;
					String iataFromAirline = leg.getDeployedAircraft().getAirline().getIataCode();
					containsIATA = StringHelper.startsWith(leg.getFlightNumber(), iataFromAirline, false); //must be in upper case
					super.state(context, containsIATA, "flightNumber", "acme.validation.leg.flight.number.message");
				}

				boolean uniqueLeg;
				Leg existingLeg;

				existingLeg = this.repository.findLegByFlightNumber(leg.getFlightNumber());
				uniqueLeg = existingLeg == null || existingLeg.equals(leg);
				super.state(context, uniqueLeg, "flightNumber", "acme.validation.leg.flight.number.duplicated.message");
			}

			if (leg.getArrivalAirport() != null && leg.getDepartureAirport() != null) {
				boolean differentAirports;

				differentAirports = !leg.getDepartureAirport().equals(leg.getArrivalAirport());
				super.state(context, differentAirports, "departureAirport", "acme.validation.leg.airport.sameAirport");
				super.state(context, differentAirports, "arrivalAirport", "acme.validation.leg.airport.sameAirport");
			}

		}

		result = !super.hasErrors(context);
		return result;

	}
}
