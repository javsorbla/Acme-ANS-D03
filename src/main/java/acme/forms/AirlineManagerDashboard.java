
package acme.forms;

import java.util.Map;

import acme.client.components.basis.AbstractForm;
import acme.entities.airport.Airport;
import acme.entities.leg.LegStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AirlineManagerDashboard extends AbstractForm {

	// Serialisation version --------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	Integer						rankingByExperience;
	Integer						yearsToRetire; //Retirement at 65
	Double						ratioOfOnTimeLegs;
	Double						ratioOfDelayedLegs;
	Airport						mostPopularAirport;
	Airport						leastPopularAirport;
	Map<LegStatus, Integer>		numberOfLegsByStatus; //Map not supported?
	Double						averageFlightCost;
	Integer						minFlightCost;
	Integer						maxFlightCost;
	Double						flightCostStandardDeviation;
	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------
}
