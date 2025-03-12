
package acme.constraints;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.ConstraintValidatorContext;

import acme.client.components.validation.AbstractValidator;
import acme.client.helpers.MomentHelper;
import acme.client.helpers.StringHelper;

public class PromotionCodeValidator extends AbstractValidator<ValidPromotionCode, String> {
	// Internal state ---------------------------------------------------------

	// Initialiser ------------------------------------------------------------

	@Override
	public void initialise(final ValidPromotionCode annotation) {
		assert annotation != null;
	}

	// AbstractValidator interface --------------------------------------------

	@Override
	public boolean isValid(final String promoCode, final ConstraintValidatorContext context) {
		// HINT: value can be null
		assert context != null;

		boolean result;

		if (!StringHelper.isBlank(promoCode)) { //Si la cadena está vaciá no hay posibilidad de error, ya que es opcional
			boolean matchesPattern;

			matchesPattern = StringHelper.matches(promoCode, "^[A-Z]{4}-[0-9]{2}$");
			super.state(context, matchesPattern, "promoCode", "acme.validation.service.promo.code");

			boolean containsCurrentYear;

			//Solution that works for any Date format, avoiding errors as long as the years is depicted as yyyy
			SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
			Date currentDate = MomentHelper.getCurrentMoment();
			String yearDigits = yearFormat.format(currentDate).substring(2); //Obtains only the year and then takes last 2 digits
			containsCurrentYear = StringHelper.endsWith(promoCode, yearDigits, false);
			super.state(context, containsCurrentYear, "promoCode", "acme.validation.service.promo.code");
		}

		result = !super.hasErrors(context);
		return result;
	}
}
