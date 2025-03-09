
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import acme.client.components.principals.DefaultUserIdentity;
import acme.client.components.validation.AbstractValidator;
import acme.client.helpers.StringHelper;
import acme.realms.AirlineManager;

public class ManagerValidator extends AbstractValidator<ValidManager, AirlineManager> {

	// Internal state ---------------------------------------------------------

	// Initialiser ------------------------------------------------------------

	@Override
	public void initialise(final ValidManager annotation) {
		assert annotation != null;
	}

	// AbstractValidator interface --------------------------------------------

	@Override
	public boolean isValid(final AirlineManager manager, final ConstraintValidatorContext context) {
		// HINT: value can be null
		assert context != null;

		boolean result;

		if (manager == null || manager.getIdentifier() == null || manager.getIdentity() == null)
			super.state(context, false, "*", "javax.validation.constraints.NotNull.message");
		else if (StringHelper.isBlank(manager.getIdentifier()))
			super.state(context, false, "identifier", "javax.validation.constraints.NotBlank.message");
		else {
			boolean containsInitials;
			DefaultUserIdentity identity = manager.getIdentity();
			char nameFirstLetter = identity.getName().charAt(0);
			char surnameFirstLetter = identity.getSurname().charAt(0);
			String initials = "" + nameFirstLetter + surnameFirstLetter;
			// Solution without using the framework helper
			//containsInitials = manager.getIdentifier().charAt(0) == nameFirstLetter && manager.getIdentifier().charAt(1) == surnameFirstLetter;
			containsInitials = StringHelper.startsWith(manager.getIdentifier(), initials, false); //Checks if identifier starts with the 2 initials
			super.state(context, containsInitials, "identifier", "acme.validation.airlinemanager.identifier.message");
		}

		result = !super.hasErrors(context);
		return result;
	}
}
