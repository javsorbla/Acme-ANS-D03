
package acme.constraints;

import java.text.Normalizer;

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
			super.state(context, uniqueAssistanceAgent, "employeeCode", "acme.validation.assistanceagent.employeeCode.duplicated.message");

			boolean containsInitials;
			DefaultUserIdentity identity = agent.getIdentity();
			//Normalizo nombre y apellidos para eliminar tildes y demás (no es necesario pasar a minúsculas, se ignorará en la comprobación)
			String normalizedName = Normalizer.normalize(identity.getName(), Normalizer.Form.NFD).replaceAll("\\p{M}", "");
			String normalizedSurname = Normalizer.normalize(identity.getSurname(), Normalizer.Form.NFD).replaceAll("\\p{M}", "");
			char nameFirstLetter = normalizedName.charAt(0);
			char surnameFirstLetter = normalizedSurname.charAt(0);
			String initials = "" + nameFirstLetter + surnameFirstLetter;
			// Solución sin el framework helper
			//containsInitials = manager.getIdentifier().charAt(0) == nameFirstLetter && manager.getIdentifier().charAt(1) == surnameFirstLetter;
			containsInitials = StringHelper.startsWith(agent.getEmployeeCode(), initials, true); //Checks if identifier starts with the 2 initials
			super.state(context, containsInitials, "identifier", "acme.validation.assistanceagent.employeeCode.message");
		}

		result = !super.hasErrors(context);
		return result;
	}
}
