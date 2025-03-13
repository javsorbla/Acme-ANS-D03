
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.principals.DefaultUserIdentity;
import acme.client.components.validation.AbstractValidator;
import acme.client.helpers.StringHelper;
import acme.realms.assistanceagent.AssistanceAgent;
import acme.realms.assistanceagent.AssistanceAgentRepository;

public class AgentValidator extends AbstractValidator<ValidAgent, AssistanceAgent> {

	// Internal state ---------------------------------------------------------

	@Autowired
	AssistanceAgentRepository repository;

	// Initialiser ------------------------------------------------------------


	@Override
	public void initialise(final ValidAgent annotation) {
		assert annotation != null;
	}

	// AbstractValidator interface --------------------------------------------

	@Override
	public boolean isValid(final AssistanceAgent agent, final ConstraintValidatorContext context) {
		// HINT: value can be null
		assert context != null;

		boolean result;

		if (agent == null || agent.getEmployeeCode() == null || agent.getIdentity() == null)
			super.state(context, false, "*", "javax.validation.constraints.NotNull.message");
		else if (StringHelper.isBlank(agent.getEmployeeCode()))
			super.state(context, false, "employeeCode", "javax.validation.constraints.NotBlank.message");
		else {

			boolean uniqueAssistanceAgent;
			AssistanceAgent existingAssistanceAgent;

			existingAssistanceAgent = this.repository.findAgentByEmployeeCode(agent.getEmployeeCode());
			uniqueAssistanceAgent = existingAssistanceAgent == null || existingAssistanceAgent.equals(agent);
			super.state(context, uniqueAssistanceAgent, "employeeCode", "acme.validation.agent.employeeCode.duplicated.message");

			boolean containsInitials;
			DefaultUserIdentity identity = agent.getIdentity();
			char nameFirstLetter = identity.getName().charAt(0);
			char surnameFirstLetter = identity.getSurname().charAt(0);
			String initials = "" + nameFirstLetter + surnameFirstLetter;
			// Solution without using the framework helper
			//containsInitials = agent.getEmployeeCode().charAt(0) == nameFirstLetter && agent.getEmployeeCode().charAt(1) == surnameFirstLetter;
			containsInitials = StringHelper.startsWith(agent.getEmployeeCode(), initials, false); //Checks if identifier starts with the 2 initials
			super.state(context, containsInitials, "employeeCode", "acme.validation.assistanceagent.identifier.message");
		}

		result = !super.hasErrors(context);
		return result;
	}
}
