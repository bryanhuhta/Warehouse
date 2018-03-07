// Author: Wencel Kust and Bryan Huhta

import java.io.Serializable;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String ORDER_STRING = "O";

    private String id;
    private Supplier supplier;
    private Client client;
    private int quantity;
    private double cost;
    private boolean isWaitlisted;

    public Order(Supplier supplier, Client client, int quantity) {
        this.id = ORDER_STRING + (OrderIdServer.instance()).getId();
        this.supplier = supplier;
        this.client = client;
        this.quantity = quantity;
        this.cost = supplier.getCost() * this.quantity;
        this.isWaitlisted = false;
    }

    public String getId() {
        return id;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public Client getClient() {
        return client;
    }

    public int getQuantity() {
        return quantity;
    }

    public void updateQuantity(int quantity) {
        this.quantity = quantity;
        this.cost = supplier.getCost() * this.quantity;
    }

    public double getCost() {
        return cost;
    }

    public boolean isWaitlisted() {
        return isWaitlisted;
    }

    public void setWaitlisted(boolean waitlisted) {
        isWaitlisted = waitlisted;
    }

    @Override
    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat("##.##");
        decimalFormat.setRoundingMode(RoundingMode.DOWN);

        return "[ id: " + id +
                ", supplier: " + supplier +
                ", client: " + client +
                ", quantity: " + quantity +
                ", cost: " + decimalFormat.format(cost) +
                ", filled: " + !isWaitlisted + " ]";
    }

    public boolean equals(Order order) {
        return this.id.equals(order.getId());
    }
}
