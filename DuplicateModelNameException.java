package main.java;

public class DuplicateModelNameException extends VehicleException {
    public DuplicateModelNameException() {
        super("Модель с таким именем уже существует!");
    }

    public DuplicateModelNameException(String modelName) {
        super("Модель с именем '" + modelName + "' уже существует!");
    }
}