package interfaces;
/**
 * A rider. Riders are compared by ID.
 */
public interface IRider extends IPerson, Comparable<IRider> {

    // Returns the rider's email address.
    String getEmail();

    // Returns the rider's home city.
    String getHomeCity();

    // Compares riders by ID (negative, zero, or positive).
    @Override
    int compareTo(IRider other);
}
