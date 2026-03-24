package main.java;

public class VehicleError extends RuntimeException {
    public VehicleError(String message) {
        super(message);
    }
}