package main.java;

public class Main {
    public static void main(String[] args) throws VehicleException, CloneNotSupportedException {
        Transport defaultTransport = VehicleUtils.createInstance("Lada", 2);
        System.out.println("Фабрика по умолчанию создала: " + defaultTransport.getClass().getSimpleName());
        VehicleUtils.printAllModels(defaultTransport);

        VehicleUtils.setTransportFactory(new MotorcycleFactory());
        Transport motorcycle = VehicleUtils.createInstance("Yamaha", 3);
        System.out.println("После смены фабрики создан: " + motorcycle.getClass().getSimpleName());
        VehicleUtils.printAllModels(motorcycle);
        VehicleUtils.printAllPrices(motorcycle);

        Car originalCar = new Car("Skoda", 2);
        Car clonedCar = originalCar.clone();
        clonedCar.updateModelName(clonedCar.getAllModelNames()[0], "Clone-Car-Model");
        System.out.println("Проверка deep clone Car:");
        System.out.println("Original: " + originalCar.getAllModelNames()[0]);
        System.out.println("Clone: " + clonedCar.getAllModelNames()[0]);

        Motorcycle originalMotorcycle = new Motorcycle("Honda", 2);
        Motorcycle clonedMotorcycle = originalMotorcycle.clone();
        clonedMotorcycle.updateModelName(clonedMotorcycle.getAllModelNames()[0], "Clone-Moto-Model");
        System.out.println("Проверка deep clone Motorcycle:");
        System.out.println("Original: " + originalMotorcycle.getAllModelNames()[0]);
        System.out.println("Clone: " + clonedMotorcycle.getAllModelNames()[0]);
    }
}
