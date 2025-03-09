
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import acme.client.components.principals.DefaultUserIdentity;
import acme.client.components.validation.AbstractValidator;
import acme.client.helpers.StringHelper;
import acme.realms.flightcrewmember.FlightCrewMember;

public class FlightCrewMemberValidator extends AbstractValidator<ValidFlightCrewMember, FlightCrewMember> {

	// Internal state ---------------------------------------------------------

	// Initialiser ------------------------------------------------------------

	@Override
	public void initialise(final ValidFlightCrewMember annotation) {
		assert annotation != null;
	}

	// AbstractValidator interface --------------------------------------------

	@Override
	public boolean isValid(final FlightCrewMember flightCrewMember, final ConstraintValidatorContext context) {
		// HINT: value can be null
		assert context != null;

		boolean result;

		if (flightCrewMember == null || flightCrewMember.getEmployeeCode() == null || flightCrewMember.getIdentity() == null)
			super.state(context, false, "*", "javax.validation.constraints.NotNull.message");
		else if (StringHelper.isBlank(flightCrewMember.getEmployeeCode()))
			super.state(context, false, "identifier", "javax.validation.constraints.NotBlank.message");
		else {
			boolean containsInitials;
			DefaultUserIdentity identity = flightCrewMember.getIdentity();
			char nameFirstLetter = identity.getName().charAt(0);
			char surnameFirstLetter = identity.getSurname().charAt(0);
			String initials = "" + nameFirstLetter + surnameFirstLetter;
			// Solution without using the framework helper
			//containsInitials = flightCrewMember.getEmployeeCode().charAt(0) == nameFirstLetter && flightCrewMember.getEmployeeCode().charAt(1) == surnameFirstLetter;
			containsInitials = StringHelper.startsWith(flightCrewMember.getEmployeeCode(), initials, false); //Checks if identifier starts with the 2 initials
			super.state(context, containsInitials, "identifier", "acme.validation.flightcrewmember.identifier.message");
		}

		result = !super.hasErrors(context);
		return result;
	}
}
