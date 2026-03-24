package main.java;

public class NoSuchModelNameException extends VehicleException {
    public NoSuchModelNameException() {
        super("Модель с таким именем не найдена!");
    }

    public NoSuchModelNameException(String modelName) {
        super("Модель с таким именем '" + modelName + "' не найдена!");
    }
}