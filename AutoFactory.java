package main.java;

public class AutoFactory implements TransportFactory {
    @Override
    public Transport createInstance(String brand, int size) throws VehicleException {
        return new Car(brand, size);
    }
}
