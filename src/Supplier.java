// Author: Bryan Huhta

import java.io.Serializable;
import java.math.RoundingMode;
import java.text.DecimalFormat;

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

    public void updateQuantity(int quantity, boolean add) {
        if (add) {
            this.quantity += quantity;
        }
        else {
            this.quantity -= quantity;
        }

    }

    @Override
    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat("##.##");
        decimalFormat.setRoundingMode(RoundingMode.DOWN);

        return " [ manufacturer: " + manufacturer +
                ", product: " + product +
                ",  quantity: " + quantity +
                ",  cost: " + decimalFormat.format(cost) + " ]";
    }

    public boolean equals(Supplier supplier) {
        return this.manufacturer == supplier.getManufacturer() &&
                this.product == supplier.getProduct();
    }
}
