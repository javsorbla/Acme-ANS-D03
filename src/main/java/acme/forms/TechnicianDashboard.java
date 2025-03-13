
package acme.forms;

import java.util.List;

import acme.client.components.basis.AbstractForm;
import acme.entities.aircraft.Aircraft;
import acme.entities.maintenanceRecord.MaintenanceRecord;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TechnicianDashboard extends AbstractForm {

	// Serialisation Identifier
	private static final long	serialVersionUID	= 1L;

	private Integer				maintenanceStatus;
	private MaintenanceRecord	nearestInspectionMaintenanceRecord;
	private List<Aircraft>		topFiveAircrafts;
	private double				averageEstimatedCost;
	private double				deviationEstimatedCost;
	private double				minEstimatedCost;
	private double				maxEstimatedCost;
	private double				averageEstimatedDuration;
	private double				deviationEstimatedDuration;
	private double				minEstimatedDuration;
	private double				maxEstimatedDuration;
}
