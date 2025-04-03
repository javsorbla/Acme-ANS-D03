
package acme.features.technician.dashboard;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Dataset;
import acme.client.components.principals.Principal;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.aircraft.Aircraft;
import acme.entities.maintenanceRecord.MaintenanceRecord;
import acme.entities.maintenanceRecord.MaintenanceRecordStatus;
import acme.forms.TechnicianDashboard;
import acme.realms.technician.Technician;

@Service
@GuiService
public class TechnicianDashboardShowService extends AbstractGuiService<Technician, TechnicianDashboard> {

	//Internal state ----------------------------------------------------------

	@Autowired
	private TechnicianDashboardRepository repository;

	//AbstractGuiService state ----------------------------------------------------------


	@Override
	public void authorise() {
		boolean authorised = false;
		Principal principal = super.getRequest().getPrincipal();
		int userAccountId = principal.getAccountId();

		// Buscar al técnico que está actualmente logueado
		Technician technician = this.repository.findOneTechnicianByUserAccoundId(userAccountId);

		if (technician != null)
			// Verificar si el técnico está registrado en el sistema
			authorised = true;

		super.getResponse().setAuthorised(authorised);
	}

	@Override
	public void load() {
		final TechnicianDashboard dashboard = new TechnicianDashboard();

		Principal principal;
		int userAccountId;
		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		final Technician technician = this.repository.findOneTechnicianByUserAccoundId(userAccountId);

		if (technician != null) {

			// NumOfMaintenanceRecordsByStatus
			final Map<String, Integer> numOfRecordsByStatus = new HashMap<>();
			Integer pendingRecords = 0;
			Integer inProgressRecords = 0;
			Integer completedRecords = 0;

			if (technician.getId() != 0) {
				pendingRecords = this.repository.countMaintenanceRecordsByStatus(technician.getId(), MaintenanceRecordStatus.PENDING).orElse(0);
				inProgressRecords = this.repository.countMaintenanceRecordsByStatus(technician.getId(), MaintenanceRecordStatus.IN_PROGRESS).orElse(0);
				completedRecords = this.repository.countMaintenanceRecordsByStatus(technician.getId(), MaintenanceRecordStatus.COMPLETED).orElse(0);
			}

			numOfRecordsByStatus.put("PENDING", pendingRecords);
			numOfRecordsByStatus.put("IN_PROGRESS", inProgressRecords);
			numOfRecordsByStatus.put("COMPLETED", completedRecords);
			dashboard.setNumberOfRecordsGroupedByStatus(numOfRecordsByStatus);

			// Maintenance record with the nearest inspection due date
			MaintenanceRecord nearestRecord = null;
			Date nearestRecordDate = null;
			if (technician.getId() != 0) {
				nearestRecord = this.repository.findNearestInspectionRecordsByTechnicianId(technician.getId()).stream().findFirst().orElse(null);
				if (nearestRecord != null)
					nearestRecordDate = nearestRecord.getNextInspectionDate();
			}
			dashboard.setNearestInspectionMaintenanceRecord(nearestRecordDate);

			// Obtener todos los Aircraft asociados a MaintenanceRecords del Technician
			List<Aircraft> allAircrafts = null;
			if (technician.getId() != 0)
				allAircrafts = this.repository.findTopFiveAircraftsByTechnicianId(technician.getId());

			List<Aircraft> topFiveAircrafts = allAircrafts != null && !allAircrafts.isEmpty() ? allAircrafts.stream().limit(5).toList() : List.of();
			dashboard.setTopFiveAircrafts(topFiveAircrafts);

			// Estadísticas de costes estimados de maintenance records 
			dashboard.setAverageEstimatedCost(this.repository.findAverageEstimatedCost(technician.getId()) != null ? this.repository.findAverageEstimatedCost(technician.getId()) : 0.0);
			dashboard.setDeviationEstimatedCost(this.repository.findDeviationEstimatedCost(technician.getId()) != null ? this.repository.findDeviationEstimatedCost(technician.getId()) : 0.0);
			dashboard.setMinEstimatedCost(this.repository.findMinEstimatedCost(technician.getId()) != null ? this.repository.findMinEstimatedCost(technician.getId()) : 0.0);
			dashboard.setMaxEstimatedCost(this.repository.findMaxEstimatedCost(technician.getId()) != null ? this.repository.findMaxEstimatedCost(technician.getId()) : 0.0);

			// Estadísticas de duración estimada 
			dashboard.setAverageEstimatedDuration(this.repository.findAverageEstimatedDuration(technician.getId()) != null ? this.repository.findAverageEstimatedDuration(technician.getId()) : 0.0);
			dashboard.setDeviationEstimatedDuration(this.repository.findDeviationEstimatedDuration(technician.getId()) != null ? this.repository.findDeviationEstimatedDuration(technician.getId()) : 0.0);
			dashboard.setMinEstimatedDuration(this.repository.findMinEstimatedDuration(technician.getId()) != null ? this.repository.findMinEstimatedDuration(technician.getId()) : 0.0);
			dashboard.setMaxEstimatedDuration(this.repository.findMaxEstimatedDuration(technician.getId()) != null ? this.repository.findMaxEstimatedDuration(technician.getId()) : 0.0);

			super.getBuffer().addData(dashboard);
		}

	}

	@Override
	public void unbind(final TechnicianDashboard object) {

		// Convertimos el Map de maintenanceStatus a un SelectChoices
		SelectChoices maintenanceStatusChoices = new SelectChoices();

		if (object.getNumberOfRecordsGroupedByStatus() != null)
			object.getNumberOfRecordsGroupedByStatus().forEach((status, count) -> {
				// Agregamos cada estado con su conteo como una opción al SelectChoices
				boolean isSelected = false; // Establece esta variable según la lógica que necesites
				maintenanceStatusChoices.add(status.toString(), count.toString(), isSelected);
			});

		// Convertimos la lista de Aircraft a un String separado por "/"
		String formattedAircrafts = object.getTopFiveAircrafts().stream().map(Aircraft::getId)  // Mantiene los IDs como Integer
			.map(String::valueOf)  // Convertimos cada Integer a String solo al momento de concatenar
			.reduce((a, b) -> a + " / " + b).orElse("No aircrafts found");

		// Crear el dataset para pasar los datos al JSP
		Dataset dataset = super.unbindObject(object, "nearestInspectionMaintenanceRecord", "averageEstimatedCost", "deviationEstimatedCost", "minEstimatedCost", "maxEstimatedCost", "averageEstimatedDuration", "deviationEstimatedDuration",
			"minEstimatedDuration", "maxEstimatedDuration");

		// Insertar las opciones convertidas en el dataset
		dataset.put("maintenanceStatus", maintenanceStatusChoices);

		// Añadir la lista formateada como un String al dataset
		dataset.put("topFiveAircrafts", formattedAircrafts);

		// Añadir el mapa de estados al dataset (Para gráfico Chart.js)
		dataset.put("numberOfRecordsGroupedByStatus", object.getNumberOfRecordsGroupedByStatus());

		// Añadir el dataset al response
		super.getResponse().addData(dataset);
	}
}
