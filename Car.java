package main.java;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Car implements VehicleInterface{
    private String brand;
    private Model[] models;
    private final Set<String> allModelNames = new HashSet<>();

    @Override
    public String getBrand() {
        return brand;
    }

    @Override
    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Model[] getModels() {
        return Arrays.copyOf(models, models.length);
    }

    public class Model {
        private String modelName;
        private float modelPrice;

        public Model(String modelName, float modelPrice) throws VehicleError, VehicleException {
            if (modelName == null || modelName.isEmpty()) throw new NoSuchModelNameException();
            if (modelPrice < 0.0 || modelPrice > Float.MAX_VALUE) throw new ModelPriceOutOfBoundsException();
            if (allModelNames.contains(modelName)) throw new DuplicateModelNameException();

            this.modelName = modelName;
            this.modelPrice = modelPrice;
        }

        public String getModelName() {
            return modelName;
        }

        public void setModelName(String modelName) throws VehicleException {
            if (allModelNames.contains(modelName)) throw new DuplicateModelNameException();
            this.modelName = modelName;
        }

        public float getModelPrice() {
            return modelPrice;
        }

        public void setModelPrice(float modelPrice) throws ModelPriceOutOfBoundsException {
            if (modelPrice < 0.0 || modelPrice > Float.MAX_VALUE) {
                throw new ModelPriceOutOfBoundsException(modelPrice);
            }
            this.modelPrice = modelPrice;
        }
    }

    private void deleteFromAllModelNames(String modelName) {
        allModelNames.remove(modelName);
    }

    public Car(String brand, Model[] models) throws DuplicateModelNameException {
        this.brand = brand;
        this.models = models;

        for (Model model : models) {
            if (allModelNames.contains(model.getModelName())) {
                throw new DuplicateModelNameException(model.getModelName());
            }
            allModelNames.add(model.getModelName());
        }
    }

    public Car(String brand, int numOfModels) throws VehicleException {
        this.brand = brand;
        this.models = new Model[numOfModels];

        for (int i = 0; i < numOfModels; i++) {
            String modelName = "Модель №" + (i + 1);
            float modelPrice = (float) (150000 + Math.floor(Math.random() * 1000000));
            if (allModelNames.contains(modelName)) throw new DuplicateModelNameException();
            models[i] = new Model(modelName, modelPrice);
            allModelNames.add(modelName);
        }
    }

    @Override
    public void updateModelName(String oldName, String newName) throws VehicleException {
        if (oldName != null && !oldName.isEmpty() && newName != null && !newName.isEmpty()) {
            if (!allModelNames.contains(oldName)) throw new NoSuchModelNameException();
            if (allModelNames.contains(newName)) throw new DuplicateModelNameException();
            if (oldName.equals(newName)) return;

            Model foundModel = null;

            for (Model model : models) {
                if (model != null && oldName.equals(model.getModelName())) {
                    foundModel = model;
                    break;
                }
            }

            if (foundModel == null) {
                throw new NoSuchModelNameException();
            }

            allModelNames.remove(oldName);

            try {
                foundModel.setModelName(newName);
            }
            catch (VehicleError e) {
                allModelNames.add(oldName);
                throw e;
            }

            allModelNames.add(newName);
        }
        else {
            throw new NoSuchModelNameException();
        }
    }

    @Override
    public String[] getAllModelNames() {
        String[] modelNames = new String[models.length];

        for (int i = 0; i < models.length; i++) {
            modelNames[i] = models[i].getModelName();
        }

        return modelNames;
    }

    @Override
    public float getModelPrice(String modelName) throws NoSuchModelNameException {
        for (Model model : models) {
            if (model.getModelName().equals(modelName)) {
                return model.getModelPrice();
            }
        }
        throw new NoSuchModelNameException(modelName);
    }

    @Override
    public void updateModelPrice(String modelName, float newPrice) throws VehicleError, NoSuchModelNameException {
        boolean found = false;
        for (Model model : models) {
            if (model.getModelName().equals(modelName)) {
                model.setModelPrice(newPrice);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new NoSuchModelNameException(modelName);
        }
    }

    @Override
    public float[] getAllModelPrices() {
        float[] modelPrices = new float[models.length];

        for (int i = 0; i < models.length; i++) {
            modelPrices[i] = models[i].getModelPrice();
        }

        return modelPrices;
    }

    @Override
    public void addModel(String modelName, float modelPrice) throws VehicleException {
        if (modelPrice < 0.0 || modelPrice > Float.MAX_VALUE) {
            throw new ModelPriceOutOfBoundsException();
        }

        if (allModelNames.contains(modelName)) {
            throw new DuplicateModelNameException();
        }

//        for (Model model : models) {
//            if (model.getModelName().equals(modelName)) {
//                throw new lab1.DuplicateModelNameException();
//            }
//        }

        Model[] newModels = Arrays.copyOf(models, models.length + 1);
        newModels[models.length] = new Model(modelName, modelPrice);
        models = newModels;
        allModelNames.add(modelName);
    }

    @Override
    public void removeModel(String modelName) throws VehicleException {
        int removeIndex = -1;

        for (int i = 0; i < models.length; i++) {
            if (models[i].getModelName().equals(modelName)) {
                removeIndex = i;
                break;
            }
        }

        if (removeIndex != -1) {
            Model[] newModels = new Model[models.length - 1];
            System.arraycopy(models, 0, newModels, 0, removeIndex);
            System.arraycopy(models, removeIndex + 1, newModels, removeIndex, models.length - removeIndex - 1);
            models = newModels;
            allModelNames.remove(modelName);
        }
        else {
            throw new NoSuchModelNameException();
        }
    }

    @Override
    public int getModelCount() {
        return models.length;
    }
}