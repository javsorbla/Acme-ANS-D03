
package acme.features.technician.maintenanceRecord;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.maintenanceRecord.MaintenanceRecord;

@Repository
public interface TechnicianMaintenanceRecordRepository extends AbstractRepository {

	@Query("SELECT m FROM MaintenanceRecord m")
	Collection<MaintenanceRecord> findAllMaintenanceRecords();

	@Query("SELECT m FROM MaintenanceRecord m WHERE m.technician.id = :technicianId ")
	Collection<MaintenanceRecord> findAllMaintenanceRecordByTechnicianId(final int technicianId);

	@Query("SELECT m FROM MaintenanceRecord m WHERE m.id = :id")
	MaintenanceRecord findMaintenanceRecordById(int id);

}
