// Author: Wencel Kust and Bryan Huhta

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class OrderList implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Order> orders = new LinkedList<Order>();
    private static OrderList orderList;

    private OrderList() {
        // Empty constructor.
    }

    public static OrderList instance() {
        if (orderList == null) {
            return (orderList = new OrderList());
        }
        else {
            return orderList;
        }
    }

    public boolean addOrder(Order order) {
        return orders.add(order);
    }

    public Iterator<Order> getOrders() {
        return orders.iterator();
    }

    private void writeObject(java.io.ObjectOutputStream outputStream) {
        try {
            outputStream.defaultWriteObject();
            outputStream.writeObject(orderList);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readObject(java.io.ObjectInputStream inputStream) {
        try {
            if (orderList != null) {
                return;
            }
            else {
                inputStream.defaultReadObject();
                if (orderList == null) {
                    orderList = (OrderList) inputStream.readObject();
                }
                else {
                    inputStream.readObject();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
