package main.java;

public class Main {
    public static void main(String[] args) throws VehicleException {
        Transport defaultTransport = VehicleUtils.createInstance("Lada", 2);
        System.out.println("Фабрика по умолчанию создала: " + defaultTransport.getClass().getSimpleName());
        VehicleUtils.printAllModels(defaultTransport);

        VehicleUtils.setTransportFactory(new MotorcycleFactory());
        Transport motorcycle = VehicleUtils.createInstance("Yamaha", 3);
        System.out.println("После смены фабрики создан: " + motorcycle.getClass().getSimpleName());
        VehicleUtils.printAllModels(motorcycle);
        VehicleUtils.printAllPrices(motorcycle);
    }
}
