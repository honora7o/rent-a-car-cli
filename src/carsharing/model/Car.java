package carsharing.model;

public class Car {
    private int id;
    private String name;
    private int companyId;

    public Car(int id, String name, int companyId) {
        this.id = id;
        this.name = name;
        this.companyId = companyId;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    public int getCompanyId() {
        return companyId;
    }
}
