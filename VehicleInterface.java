package main.java;

public interface VehicleInterface {
    String getBrand();
    void setBrand(String brand);

    String[] getAllModelNames();
    float[] getAllModelPrices();
    float getModelPrice(String modelName) throws VehicleException, VehicleError;
    void updateModelPrice(String modelName, float newPrice) throws VehicleException;


    void addModel(String modelName, float modelPrice) throws VehicleException, VehicleError;
    void updateModelName(String oldName, String newName) throws VehicleException;
    void removeModel(String modelName) throws VehicleException;

    int getModelCount();
}