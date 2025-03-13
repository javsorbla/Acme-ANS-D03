
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.helpers.StringHelper;
import acme.entities.booking.Booking;
import acme.entities.booking.BookingRepository;

public class BookingValidator extends AbstractValidator<ValidBooking, Booking> {

	// Internal state ---------------------------------------------------------

	@Autowired
	BookingRepository repository;

	// Initialiser ------------------------------------------------------------


	@Override
	public void initialise(final ValidBooking annotation) {
		assert annotation != null;
	}

	// AbstractValidator interface --------------------------------------------

	@Override
	public boolean isValid(final Booking booking, final ConstraintValidatorContext context) {
		// HINT: value can be null
		assert context != null;

		boolean result;

		if (booking == null || booking.getLocatorCode() == null || booking.getLocatorCode() == null)
			super.state(context, false, "*", "javax.validation.constraints.NotNull.message");
		else if (StringHelper.isBlank(booking.getLocatorCode()))
			super.state(context, false, "identifier", "javax.validation.constraints.NotBlank.message");
		else {

			boolean uniqueBooking;
			Booking existingBooking;

			existingBooking = this.repository.findBookingByLocatorCode(booking.getLocatorCode());
			uniqueBooking = existingBooking == null || existingBooking.equals(booking);
			super.state(context, uniqueBooking, "identifier", "acme.validation.booking.locator.code.duplicated.message");

		}

		result = !super.hasErrors(context);
		return result;
	}
}
