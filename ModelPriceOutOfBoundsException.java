package main.java;

public class ModelPriceOutOfBoundsException extends VehicleError {
    public ModelPriceOutOfBoundsException() {
        super("Цена модели не может быть отрицательной или слишком большой!");
    }

    public ModelPriceOutOfBoundsException(float price) {
        super("Недопустимая цена: " + price + ". Цена должна быть от 0 до " + Float.MAX_VALUE);
    }
}