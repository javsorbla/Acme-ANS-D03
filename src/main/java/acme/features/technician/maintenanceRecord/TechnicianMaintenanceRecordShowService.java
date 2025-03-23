
package acme.features.technician.maintenanceRecord;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.maintenanceRecord.MaintenanceRecord;
import acme.entities.maintenanceRecord.MaintenanceRecordStatus;
import acme.realms.technician.Technician;

@GuiService
public class TechnicianMaintenanceRecordShowService extends AbstractGuiService<Technician, MaintenanceRecord> {

	//Internal state ----------------------------------------------------------

	@Autowired
	private TechnicianMaintenanceRecordRepository repository;

	//AbstractGuiService state ----------------------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);

	}

	@Override
	public void load() {
		MaintenanceRecord maintenanceRecord;
		int id;

		id = super.getRequest().getData("id", int.class);
		maintenanceRecord = this.repository.findMaintenanceRecordById(id);

		super.getBuffer().addData(maintenanceRecord);

	}

	@Override
	public void unbind(final MaintenanceRecord maintenanceRecord) {

		SelectChoices choices;
		Dataset dataset;

		choices = SelectChoices.from(MaintenanceRecordStatus.class, maintenanceRecord.getStatus());

		dataset = super.unbindObject(maintenanceRecord, "moment", "status", "nextInspectionDate", "estimatedCost", "notes");
		dataset.put("confirmation", false);
		dataset.put("readonly", true);
		dataset.put("statuses", choices);

		super.getResponse().addData(dataset);

	}

}
