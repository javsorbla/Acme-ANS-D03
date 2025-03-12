
package acme.constraints;

import java.util.Date;

import javax.validation.ConstraintValidatorContext;

import acme.client.components.validation.AbstractValidator;
import acme.client.helpers.MomentHelper;
import acme.entities.activitylog.ActivityLog;

public class ActivityLogValidator extends AbstractValidator<ValidActivityLog, ActivityLog> {

	// Internal state ---------------------------------------------------------

	// Initialiser ------------------------------------------------------------

	@Override
	public void initialise(final ValidActivityLog annotation) {
		assert annotation != null;
	}

	// AbstractValidator interface --------------------------------------------
	@Override
	public boolean isValid(final ActivityLog activityLog, final ConstraintValidatorContext context) {

		assert context != null;

		boolean result;

		if (activityLog == null || activityLog.getActivityLogAssignment() == null || activityLog.getActivityLogAssignment().getFlightAssignmentLeg() == null || activityLog.getActivityLogAssignment().getFlightAssignmentLeg().getArrival() == null)
			super.state(context, false, "*", "javax.validation.constraints.NotNull.message");
		else {
			boolean registrationMomentIsAfterArrivalLeg;
			Date minRegistrationMoment = new Date(activityLog.getActivityLogAssignment().getFlightAssignmentLeg().getArrival().getTime());
			registrationMomentIsAfterArrivalLeg = MomentHelper.isAfterOrEqual(activityLog.getRegistrationMoment(), minRegistrationMoment);
			super.state(context, registrationMomentIsAfterArrivalLeg, "registrationMoment", "acme.validation.activitylog.registrationmoment.message");
		}

		result = !super.hasErrors(context);
		return result;
	}

}
