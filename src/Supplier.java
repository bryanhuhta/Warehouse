// Author: Bryan Huhta

import java.io.Serializable;

public class Supplier implements Serializable {
    private static final long serialVersionUID = 1L;

    private Manufacturer manufacturer;
    private Product product;
    private int quantity;
    private double cost;

    Supplier(Manufacturer manufacturer, Product product,
             int quantity, double cost) {
        this.manufacturer = manufacturer;
        this.product = product;
        this.quantity = quantity;
        this.cost = cost;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getCost() {
        return cost;
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }

    @Override
    public String toString() {
        return " [ manufacturer: " + manufacturer +
                "\n  product: " + product +
                "\n  quantity: " + quantity +
                "\n  cost: " + cost + " ]";
    }

    public boolean equals(Supplier supplier) {
        return this.manufacturer == supplier.getManufacturer() &&
                this.product == supplier.getProduct();
    }
}
