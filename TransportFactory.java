package main.java;

public interface TransportFactory {
    Transport createInstance(String brand, int size) throws VehicleException;
}
