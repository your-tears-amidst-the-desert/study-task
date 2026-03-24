package main.java;

public class MotorcycleFactory implements TransportFactory {
    @Override
    public Transport createInstance(String brand, int size) throws VehicleException {
        return new Motorcycle(brand, size);
    }
}
