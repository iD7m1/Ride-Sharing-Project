package interfaces;
/**
 * A private ride with exactly one rider.
 */
public interface IPrivateRide extends IRide {

	// Returns the rider of this ride.
	IRider getRider();
}
