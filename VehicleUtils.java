package main.java;

public class VehicleUtils {
    public static double getAverageModelPrice(VehicleInterface vehicle) {
        float[] prices = vehicle.getAllModelPrices();
        if (prices.length == 0) return 0.0;

        double sum = 0;
        for (float price : prices) {
            sum += price;
        }
        return sum / prices.length;
    }

    public static void printAllModels(VehicleInterface vehicle) {
        String[] models = vehicle.getAllModelNames();
        System.out.println("Модели транспортного средства '" + vehicle.getBrand() + "':");
        for (int i = 0; i < models.length; i++) {
            System.out.println((i + 1) + ". " + models[i]);
        }
    }

    public static void printAllPrices(VehicleInterface vehicle) {
        float[] prices = vehicle.getAllModelPrices();
        String[] models = vehicle.getAllModelNames();

        System.out.println("Цены моделей транспортного средства '" + vehicle.getBrand() + "':");
        for (int i = 0; i < prices.length; i++) {
            System.out.println(models[i] + ": " + prices[i] + " руб.");
        }
    }
}