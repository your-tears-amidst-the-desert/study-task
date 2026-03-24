package main.java;

public class VehicleUtils {
    private static TransportFactory factory = new AutoFactory();

    public static void setTransportFactory(TransportFactory transportFactory) {
        if (transportFactory == null) {
            throw new IllegalArgumentException("Factory must not be null");
        }
        factory = transportFactory;
    }

    public static Transport createInstance(String name, int size) throws VehicleException {
        return factory.createInstance(name, size);
    }

    public static double getAverageModelPrice(Transport vehicle) {
        float[] prices = vehicle.getAllModelPrices();
        if (prices.length == 0) return 0.0;

        double sum = 0;
        for (float price : prices) {
            sum += price;
        }
        return sum / prices.length;
    }

    public static void printAllModels(Transport vehicle) {
        String[] models = vehicle.getAllModelNames();
        System.out.println("Модели транспортного средства '" + vehicle.getBrand() + "':");
        for (int i = 0; i < models.length; i++) {
            System.out.println((i + 1) + ". " + models[i]);
        }
    }

    public static void printAllPrices(Transport vehicle) {
        float[] prices = vehicle.getAllModelPrices();
        String[] models = vehicle.getAllModelNames();

        System.out.println("Цены моделей транспортного средства '" + vehicle.getBrand() + "':");
        for (int i = 0; i < prices.length; i++) {
            System.out.println(models[i] + ": " + prices[i] + " руб.");
        }
    }
}
