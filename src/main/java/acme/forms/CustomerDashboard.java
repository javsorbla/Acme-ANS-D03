
package acme.forms;

import java.util.List;
import java.util.Map;

import acme.client.components.basis.AbstractForm;
import acme.client.components.datatypes.Money;
import acme.entities.booking.TypeTravelClass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDashboard extends AbstractForm {

	// Serialisation version --------------------------------------------------
	private static final long		serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	List<String>					lastFiveDestinations;

	Money							spentBookingMoney;

	Map<TypeTravelClass, Integer>	bookingsNumberByTravelClass;

	Money							bookingTotalCost;

	Money							bookingAverageCost;

	Money							bookingMinimumCost;

	Money							bookingMaximumCost;

	Money							bookingStandardDeviationCost;

	Integer							bookingTotalPassengers;

	Double							bookingAveragePassengers;

	Integer							bookingMinimumPassengers;

	Integer							bookingMaximumPassengers;

	Double							bookingStandardDeviationPassengers;
}
