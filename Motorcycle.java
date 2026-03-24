package main.java;

import java.util.Date;

public class Motorcycle implements VehicleInterface{
    private String brand;
    private Model head = null;
    private Date lastModified;
    {
        lastModified = new Date();
    }
    private int size = 0;

    @Override
    public String getBrand() {
        return brand;
    }

    @Override
    public void setBrand(String brand) {
        this.brand = brand;
    }

    private class Model {
        private String modelName;
        private float modelPrice;
        private Model prev = null;
        private Model next = null;

        Model(String modelName, float modelPrice) throws VehicleException {
            if (modelName == null || modelName.isEmpty()) throw new NoSuchModelNameException();
            if (modelPrice < 0.0 || modelPrice > Float.MAX_VALUE) throw new ModelPriceOutOfBoundsException();

            if (head != null) {
                Model m = head;
                do {
                    if (m.getModelName().equals(modelName)) throw new DuplicateModelNameException();
                    m = m.next;
                } while(m != head);
            }

            this.modelName = modelName;
            this.modelPrice = modelPrice;
            updateLastModified();
        }

        String getModelName() {
            return this.modelName;
        }

        void setModelName(String modelName) throws VehicleException {
            if (modelName == null || modelName.isEmpty()) throw new NoSuchModelNameException();

            if (head != null) {
                Model cur = head;
                do {
                    if (cur != this && cur.getModelName().equals(modelName)) {
                        throw new DuplicateModelNameException();
                    }
                    cur = cur.next;
                } while(cur != head);
            }

            this.modelName = modelName;
            updateLastModified();
        }

        float getModelPrice() {
            return this.modelPrice;
        }

        void setModelPrice(float modelPrice) throws VehicleError {
            if (modelPrice < 0.0 || modelPrice > Float.MAX_VALUE) throw new ModelPriceOutOfBoundsException();
            this.modelPrice = modelPrice;
            updateLastModified();
        }
    }

    public Motorcycle(String brand, int numOfModels) throws VehicleError, VehicleException {
        this.brand = brand;

        for (int i = 0; i < numOfModels; i++) {
            String modelName = "Модель №" + (i + 1);
            float modelPrice = (float) (150000 + Math.floor(Math.random() * 1000000));
            this.addModel(modelName, modelPrice);
        }
    }
    private void updateLastModified() {
        this.lastModified = new Date();
    }

    public Date getLastModified() {
        return lastModified;
    }

    @Override
    public void addModel(String modelName, float modelPrice) throws VehicleError, VehicleException {
        Model newModel = new Model(modelName, modelPrice);
        if (head == null) {
            head = newModel;
            head.next = head;
            head.prev = head;
        } else {
            Model last = head.prev;
            newModel.next = head;
            newModel.prev = last;
            head.prev = newModel;
            last.next = newModel;
        }

        updateLastModified();
        size++;
    }

    private Model findModelByName(String modelName) throws VehicleException {
        if (head == null) throw new NoSuchModelNameException(modelName);

        Model current = head;
        do {
            if (current.getModelName().equals(modelName)) {
                return current;
            }
            current = current.next;
        } while (current != head);

        throw new NoSuchModelNameException(modelName);
    }

    @Override
    public void removeModel(String modelName) throws VehicleException {
        if (head == null) throw new NoSuchModelNameException(modelName);

        Model toRemove = findModelByName(modelName);

        if (size == 1) {
            head = null;
        } else {
            toRemove.prev.next = toRemove.next;
            toRemove.next.prev = toRemove.prev;
            if (toRemove == head) {
                head = head.next;
            }
        }

        size--;
        updateLastModified();
    }

    @Override
    public String[] getAllModelNames() {
        if (head == null) return new String[0];

        String[] names = new String[size];
        Model current = head;
        for (int i = 0; i < size; i++) {
            names[i] = current.getModelName();
            current = current.next;
        }
        return names;
    }

    @Override
    public float getModelPrice(String modelName) throws VehicleException {
        Model model = findModelByName(modelName);
        return model.getModelPrice();
    }

    @Override
    public void updateModelPrice(String modelName, float newPrice) throws VehicleException {
        Model model = findModelByName(modelName);
        model.setModelPrice(newPrice);
    }

    @Override
    public float[] getAllModelPrices() {
        if (head == null) return new float[0];

        float[] prices = new float[size];
        Model current = head;
        for (int i = 0; i < size; i++) {
            prices[i] = current.getModelPrice();
            current = current.next;
        }
        return prices;
    }

    @Override
    public void updateModelName(String oldName, String newName) throws VehicleException {
        if (oldName == null || newName == null || oldName.isEmpty() || newName.isEmpty()) {
            throw new NoSuchModelNameException();
        }

        if (oldName.equals(newName)) {
            return;
        }

        Model model = findModelByName(oldName);
        model.setModelName(newName);
    }

    @Override
    public int getModelCount() {
        return size;
    }
}