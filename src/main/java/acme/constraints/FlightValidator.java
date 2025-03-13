
package acme.constraints;

import java.util.Date;
import java.util.List;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.helpers.MomentHelper;
import acme.entities.flight.Flight;
import acme.entities.flight.FlightRepository;
import acme.entities.leg.Leg;

public class FlightValidator extends AbstractValidator<ValidFlight, Flight> {
	// Internal State ----------------------------------------------------

	@Autowired
	private FlightRepository repository;

	// Initialiser -------------------------------------------------------


	@Override
	public void initialise(final ValidFlight annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Flight flight, final ConstraintValidatorContext context) {

		assert context != null;

		boolean result;

		if (flight == null)
			super.state(context, false, "*", "javax.validation.constraints.NotNull.message");
		else if (flight.isPublish() == true) {
			boolean correctlyPublished;
			List<Leg> legs = this.repository.getLegsByFlight(flight.getId());
			Integer publishedLegs = this.repository.getPublishedLegsByFlight(flight.getId());
			correctlyPublished = legs.size() > 0 && legs.size() == publishedLegs; //At least one leg and all of them published
			super.state(context, correctlyPublished, "publish", "acme.validation.flight.publish.message");

			if (legs.size() > 1) {
				boolean legsNotOverlapping;
				for (int i = 1; i < legs.size(); i++) {
					Date previousLegArrival = legs.get(i - 1).getArrival();
					Date currentLegDeparture = legs.get(i).getDeparture();
					legsNotOverlapping = MomentHelper.isAfter(currentLegDeparture, previousLegArrival);
					if (!legsNotOverlapping) {
						super.state(context, false, "*", "acme.validation.flight.leg.overlap.message");
						break; //there's no need to keep checking more legs
					}
				}
			}
		}
		result = !super.hasErrors(context);
		return result;

	}
}
