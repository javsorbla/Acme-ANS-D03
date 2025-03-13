
package acme.entities.booking;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.validation.Mandatory;
import acme.entities.passenger.Passenger;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class BookingRecord extends AbstractEntity {
	// Serialisation version --------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------

	// Relationships -----------------------------------------------------

	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Booking				bookingId;

	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Passenger			passengerId;

}
