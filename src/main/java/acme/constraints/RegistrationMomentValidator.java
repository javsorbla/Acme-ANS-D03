
package acme.constraints;

import java.util.Date;

import javax.validation.ConstraintValidatorContext;

import acme.client.components.validation.AbstractValidator;
import acme.client.helpers.MomentHelper;
import acme.entities.leg.Leg;
import acme.internals.helpers.HibernateHelper;

public class RegistrationMomentValidator extends AbstractValidator<ValidRegistrationMoment, Date> {

	// Internal state ---------------------------------------------------------

	private Date	lowerLimit;
	private Date	upperLimit;

	// Initialiser ------------------------------------------------------------


	@Override
	public void initialise(final ValidRegistrationMoment annotation) {
		assert annotation != null;
		this.upperLimit = MomentHelper.getCurrentMoment();
	}

	// AbstractValidator interface --------------------------------------------
	@Override
	public boolean isValid(final Date value, final ConstraintValidatorContext context) {
		assert context != null;

		if (value == null)
			return false;

		Leg leg = this.getLegFromContext(context);
		if (leg == null || leg.getArrival() == null)
			return false;

		this.lowerLimit = leg.getArrival();

		boolean result = value.after(this.lowerLimit) && value.before(this.upperLimit);
		if (!result)
			HibernateHelper.replaceParameter(context, "placeholder", "acme.validation.range.message", this.lowerLimit.toString(), this.upperLimit.toString());

		return result;
	}

	private Leg getLegFromContext(final ConstraintValidatorContext context) {
		return null;
	}
}
