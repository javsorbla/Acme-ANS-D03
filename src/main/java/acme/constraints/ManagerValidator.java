
package acme.constraints;

import java.text.Normalizer;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.principals.DefaultUserIdentity;
import acme.client.components.validation.AbstractValidator;
import acme.client.helpers.StringHelper;
import acme.realms.manager.AirlineManager;
import acme.realms.manager.AirlineManagerRepository;

public class ManagerValidator extends AbstractValidator<ValidManager, AirlineManager> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AirlineManagerRepository repository;

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
			boolean uniqueManager;
			AirlineManager existingManager;

			existingManager = this.repository.findManagerByIdentifier(manager.getIdentifier());
			uniqueManager = existingManager == null || existingManager.equals(manager);
			super.state(context, uniqueManager, "identifier", "acme.validation.airlinemanager.identifier.duplicated.message");

			boolean containsInitials;
			DefaultUserIdentity identity = manager.getIdentity();
			//Normalizo nombre y apellidos para eliminar tildes y demás (no es necesario pasar a minúsculas, se ignorará en la comprobación)
			String normalizedName = Normalizer.normalize(identity.getName(), Normalizer.Form.NFD).replaceAll("\\p{M}", "");
			String normalizedSurname = Normalizer.normalize(identity.getSurname(), Normalizer.Form.NFD).replaceAll("\\p{M}", "");
			char nameFirstLetter = normalizedName.charAt(0);
			char surnameFirstLetter = normalizedSurname.charAt(0);
			String initials = "" + nameFirstLetter + surnameFirstLetter;
			// Solución sin el framework helper
			//containsInitials = manager.getIdentifier().charAt(0) == nameFirstLetter && manager.getIdentifier().charAt(1) == surnameFirstLetter;
			containsInitials = StringHelper.startsWith(manager.getIdentifier(), initials, true); //Checks if identifier starts with the 2 initials
			super.state(context, containsInitials, "identifier", "acme.validation.airlinemanager.identifier.message");
		}

		result = !super.hasErrors(context);
		return result;
	}
}
