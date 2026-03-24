package main.java;

public class Motorcycle implements VehicleInterface, Cloneable {
    private String brand;
    private Model head = new Model();
    {
        head.prev = head;
        head.next = head;
    }
    private int size = 0;

    private class Model {
        private String modelName = null;
        private float modelPrice = Float.NaN;
        private Model prev = null;
        private Model next = null;

        Model() {
        }

        Model(String modelName, float modelPrice) throws VehicleException {
            if (modelName == null || modelName.isEmpty()) throw new NoSuchModelNameException();
            if (modelPrice < 0.0 || modelPrice > Float.MAX_VALUE) throw new ModelPriceOutOfBoundsException(modelPrice);
            this.modelName = modelName;
            this.modelPrice = modelPrice;
        }
    }

    public Motorcycle(String brand, int numOfModels) throws VehicleException {
        this.brand = brand;
        for (int i = 0; i < numOfModels; i++) {
            addModel("Модель №" + (i + 1), (float) (150000 + Math.floor(Math.random() * 1000000)));
        }
    }

    @Override
    public String getBrand() {
        return brand;
    }

    @Override
    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public String[] getAllModelNames() {
        String[] names = new String[size];
        Model current = head.next;
        for (int i = 0; i < size; i++) {
            names[i] = current.modelName;
            current = current.next;
        }
        return names;
    }

    @Override
    public float[] getAllModelPrices() {
        float[] prices = new float[size];
        Model current = head.next;
        for (int i = 0; i < size; i++) {
            prices[i] = current.modelPrice;
            current = current.next;
        }
        return prices;
    }

    @Override
    public float getModelPrice(String modelName) throws VehicleException {
        return findModelByName(modelName).modelPrice;
    }

    @Override
    public void updateModelPrice(String modelName, float newPrice) throws VehicleException {
        if (newPrice < 0.0 || newPrice > Float.MAX_VALUE) {
            throw new ModelPriceOutOfBoundsException(newPrice);
        }
        findModelByName(modelName).modelPrice = newPrice;
    }

    @Override
    public void addModel(String modelName, float modelPrice) throws VehicleException {
        if (containsModelName(modelName)) {
            throw new DuplicateModelNameException(modelName);
        }

        Model newModel = new Model(modelName, modelPrice);
        Model last = head.prev;
        newModel.next = head;
        newModel.prev = last;
        last.next = newModel;
        head.prev = newModel;
        size++;
    }

    @Override
    public void updateModelName(String oldName, String newName) throws VehicleException {
        if (newName == null || newName.isEmpty()) throw new NoSuchModelNameException();
        if (oldName.equals(newName)) return;
        if (containsModelName(newName)) throw new DuplicateModelNameException(newName);
        findModelByName(oldName).modelName = newName;
    }

    @Override
    public void removeModel(String modelName) throws VehicleException {
        Model toRemove = findModelByName(modelName);
        toRemove.prev.next = toRemove.next;
        toRemove.next.prev = toRemove.prev;
        size--;
    }

    @Override
    public int getModelCount() {
        return size;
    }

    private boolean containsModelName(String modelName) {
        if (modelName == null || modelName.isEmpty()) return false;
        Model current = head.next;
        while (current != head) {
            if (current.modelName.equals(modelName)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    private Model findModelByName(String modelName) throws NoSuchModelNameException {
        if (modelName == null || modelName.isEmpty()) throw new NoSuchModelNameException();

        Model current = head.next;
        while (current != head) {
            if (current.modelName.equals(modelName)) {
                return current;
            }
            current = current.next;
        }
        throw new NoSuchModelNameException(modelName);
    }

    @Override
    public Motorcycle clone() throws CloneNotSupportedException {
        Motorcycle clonedMotorcycle = (Motorcycle) super.clone();
        clonedMotorcycle.head = clonedMotorcycle.new Model();
        clonedMotorcycle.head.prev = clonedMotorcycle.head;
        clonedMotorcycle.head.next = clonedMotorcycle.head;
        clonedMotorcycle.size = 0;

        Model current = this.head.next;
        while (current != this.head) {
            try {
                clonedMotorcycle.addModel(current.modelName, current.modelPrice);
            } catch (VehicleException e) {
                throw new CloneNotSupportedException("Не удалось клонировать модель: " + e.getMessage());
            }
            current = current.next;
        }

        return clonedMotorcycle;
    }
}
