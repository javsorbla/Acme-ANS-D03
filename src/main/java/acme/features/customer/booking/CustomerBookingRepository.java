
package acme.features.customer.booking;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.booking.Booking;
import acme.entities.booking.BookingRecord;
import acme.entities.flight.Flight;
import acme.entities.passenger.Passenger;

@Repository
public interface CustomerBookingRepository extends AbstractRepository {

	@Query("select b from Booking b where b.customer.id =:customerId")
	Collection<Booking> findBookingsByCustomerId(int customerId);

	@Query("select b from Booking b where b.id = :id")
	Booking findBookingById(int id);

	@Query("select f from Flight f where f.publish = true")
	Collection<Flight> findAllPublishFlights();

	@Query("select f from Flight f")
	Collection<Flight> findAllFlights();

	@Query("select b from Booking b where b.locatorCode= :locatorCode")
	Booking findBookingByLocatorCode(String locatorCode);

	@Query("SELECT br.passenger FROM BookingRecord br WHERE br.booking.id = :bookingId")
	Collection<Passenger> findAllPassengersByBookingId(int bookingId);

	@Query("select br from BookingRecord br where br.booking.id = :bookingId")
	Collection<BookingRecord> findAllBookingRecordByBookingId(int bookingId);
}
