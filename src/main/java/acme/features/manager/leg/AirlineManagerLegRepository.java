
package acme.features.manager.leg;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.aircraft.Aircraft;
import acme.entities.airport.Airport;
import acme.entities.flight.Flight;
import acme.entities.leg.Leg;
import acme.entities.leg.LegStatus;
import acme.realms.manager.AirlineManager;

@Repository
public interface AirlineManagerLegRepository extends AbstractRepository {

	@Query("select l from Leg l where l.flight.id = :flightId order by l.departure asc")
	Collection<Leg> findAllLegsByFlightId(int flightId);

	@Query("select m from AirlineManager m where m.id = :managerId")
	AirlineManager findManagerById(int managerId);

	@Query("select f from Flight f where f.id = :flightId")
	Flight findFlightById(int flightId);

	@Query("select l from Leg l where l.id = :legId")
	Leg findLegById(int legId);

	@Query("select a from Aircraft a")
	Collection<Aircraft> findAllAircrafts();

	@Query("select ap from Airport ap")
	Collection<Airport> findAllAirports();

	@Query("select count(l.deployedAircraft) from Leg l where l.deployedAircraft.id = :aircraftId and l.publish = true and l.status != :status and ((l.departure >= :departure and l.departure <= :arrival) or (l.arrival >= :departure and l.arrival <= :arrival))")
	Integer findNumberOfLegsDeployingSameAircraft(Date departure, Date arrival, LegStatus status, Integer aircraftId);
}
