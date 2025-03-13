
package acme.forms;

import java.util.List;

import acme.client.components.basis.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssistanceAgentDashboard extends AbstractForm {

	// Serialisation version --------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	Double						resolvedClaimsRatio;

	Double						rejectedClaimsRatio;

	List<String>				topMonthsWihClaims;

	Double						averageDeviation;

	Integer						minDeviation;

	Integer						maxDeviation;

	Double						standartDeviation;

	Double						averageNumberOfClaimsAssistedLastMonth;

	Integer						minNumberOfClaimsAssistedLastMonth;

	Integer						maxNumberOfClaimsAssistedLastMonth;

	Double						standartNumberOfClaimsAssistedLastMonth;

}
