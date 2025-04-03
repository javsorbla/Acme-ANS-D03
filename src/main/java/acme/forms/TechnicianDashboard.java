
package acme.forms;

import java.util.Date;
import java.util.List;
import java.util.Map;

import acme.client.components.basis.AbstractForm;
import acme.entities.aircraft.Aircraft;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TechnicianDashboard extends AbstractForm {

	// Serialisation Identifier
	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	// Number of maintenance records grouped by their status
	Map<String, Integer>		numberOfRecordsGroupedByStatus;

	// Maintenance record with the nearest inspection due date
	private Date				nearestInspectionMaintenanceRecord;

	// Top five aircrafts with the highest number of tasks in their maintenance records
	private List<Aircraft>		topFiveAircrafts;

	// Statistics on the estimated cost of maintenance records in the last year
	private double				averageEstimatedCost;
	private double				deviationEstimatedCost;
	private double				minEstimatedCost;
	private double				maxEstimatedCost;

	// Statistics on the estimated duration of tasks in which the technician is involved
	private double				averageEstimatedDuration;
	private double				deviationEstimatedDuration;
	private double				minEstimatedDuration;
	private double				maxEstimatedDuration;
}
