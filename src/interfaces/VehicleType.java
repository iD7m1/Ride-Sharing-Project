package interfaces;
/**
 * The set of vehicle types a driver's vehicle may be. (Unchanged from Phase 1.)
 * Like every Java enum, VehicleType implements Comparable<VehicleType>
 * (constants are ordered by their declaration order), so it can be used
 * directly as a BST key.
 */
public enum VehicleType {
    SEDAN,
    LUXURY_SEDAN,
    SUV,
    VAN
}
