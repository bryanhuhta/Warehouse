// Author: Bryan Huhta
// Author: Wencel Kust

import java.io.*;
import java.util.Iterator;

public class Warehouse implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String DATA_FILE = "WarehouseData";

    private ClientList clientList;
    private ProductList productList;
    private ManufacturerList manufacturerList;
    private SupplierList supplierList;
    private OrderList orderList;

    private static Warehouse warehouse;

    // Construct Warehouse.
    private Warehouse() {
        clientList = ClientList.instance();
        productList = ProductList.instance();
        manufacturerList = ManufacturerList.instance();
        supplierList = SupplierList.instance();
        orderList = OrderList.instance();
    }

    public static Warehouse instance() {
        if (warehouse == null) {
            ClientIdServer.instance();
            ProductIdServer.instance();
            ManufacturerIdServer.instance();
            return warehouse = new Warehouse();
        }
        else {
            return warehouse;
        }
    }
    // End construct.

    // Add/delete methods.
    public Client addClient() {
        Client client = new Client();

        if (clientList.addClient(client)) {
            return client;
        }

        return null;
    }

    public Product addProduct(String name) {
        Product product = new Product(name);

        if (productList.addProduct(product)) {
            return product;
        }

        return null;
    }

    public Manufacturer addManufacturer(String name) {
        Manufacturer manufacturer = new Manufacturer(name);

        if (manufacturerList.addManufacturer(manufacturer)) {
            return manufacturer;
        }

        return null;
    }

    public Supplier addSupplier(String mid, String pid, int quantity,
                                double cost) {
        Supplier supplier = null;
        Manufacturer manufacturer = getManufacturer(mid);
        Product product = getProduct(pid);

        if (manufacturer != null && product != null) {
            supplier = new Supplier(manufacturer, product, quantity, cost);

            // Null supplier if it cannot be added.
            if (!supplierList.addSupplier(supplier)) {
                supplier = null;
            }
        }

        return supplier;
    }

    public boolean deleteSupplier(String mid, String pid) {
        boolean deleted = false;
        Iterator iterator = supplierList.getSuppliers();

        while (iterator.hasNext()) {
            Supplier temp = (Supplier) iterator.next();

            if (temp.getManufacturer().getId().equals(mid) &&
                    temp.getProduct().getProductId().equals(pid)) {
                deleted = supplierList.deleteSupplier(temp);
                break;
            }
        }

        return deleted;
    }

    public Order addOrder(String mid, String pid, String cid, int quantity) {
        Order order = null;
        Supplier supplier = getSupplier(mid, pid);
        Client client = getClient(cid);

        if (supplier != null && client != null) {
            order = new Order(supplier, client, quantity);

            // Null order if it is not added to list.
            if (!orderList.addOrder(order)) {
                order = null;
            }
        }

        return order;
    }
    // End add/delete methods.

    // Getters.
    public Client getClient(String cid) {
        Client client = null;
        Iterator iterator = getClients();

        while (iterator.hasNext()) {
            Client temp = (Client) iterator.next();

            if (cid.equals(temp.getId())) {
                client = temp;
                break;
            }
        }

        return client;
    }

    public Product getProduct(String pid) {
        Product product = null;
        Iterator iterator = getProducts();

        while (iterator.hasNext()) {
            Product temp = (Product) iterator.next();

            if (pid.equals(temp.getProductId())) {
                product = temp;
                break;
            }
        }

        return product;
    }

    public Manufacturer getManufacturer(String mid) {
        Manufacturer manufacturer = null;
        Iterator iterator = getManufacturers();

        while (iterator.hasNext()) {
            Manufacturer temp = (Manufacturer) iterator.next();

            if (mid.equals(temp.getId())) {
                manufacturer = temp;
                break;
            }
        }

        return manufacturer;
    }

    public Supplier getSupplier(String mid, String pid) {
        Supplier supplier = null;
        Iterator iterator = getSuppliers();

        while (iterator.hasNext()) {
            Supplier temp = (Supplier) iterator.next();

            if (mid.equals(temp.getManufacturer().getId()) &&
            pid.equals(temp.getProduct().getProductId())) {
                supplier = temp;
                break;
            }
        }

        return supplier;
    }
    // End getters.

    // Iterators.
    public Iterator<Client> getClients() {
        return clientList.getClients();
    }

    public Iterator<Product> getProducts() {
        return productList.getProducts();
    }

    public Iterator<Manufacturer> getManufacturers() {
        return manufacturerList.getManufacturers();
    }

    public Iterator<Supplier> getSuppliers() {
        return supplierList.getSuppliers();
    }

    public Iterator<Order> getOrders() {
        return orderList.getOrders();
    }
    // End iterators.

    // Serialize/deserialize.
    public static Warehouse retrieve() {
        try {
            FileInputStream file = new FileInputStream(DATA_FILE);
            ObjectInputStream inputStream = new ObjectInputStream(file);

            inputStream.readObject();
            ClientIdServer.retrieve(inputStream);
            ProductIdServer.retrieve(inputStream);
            ManufacturerIdServer.retrieve(inputStream);

            return warehouse;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean save() {
        try {
            FileOutputStream file = new FileOutputStream(DATA_FILE);
            ObjectOutputStream outputStream = new ObjectOutputStream(file);

            outputStream.writeObject(warehouse);
            outputStream.writeObject(ClientIdServer.instance());
            outputStream.writeObject(ProductIdServer.instance());
            outputStream.writeObject(ManufacturerIdServer.instance());

            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void writeObject(java.io.ObjectOutputStream outputStream) {
        try {
            outputStream.defaultWriteObject();
            outputStream.writeObject(warehouse);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readObject(java.io.ObjectInputStream inputStream) {
        try {
            inputStream.defaultReadObject();
            if (warehouse == null) {
                warehouse = (Warehouse) inputStream.readObject();
            }
            else {
                inputStream.readObject();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    // End serialize/deserialize.

    @Override
    public String toString() {
        return clientList + "\n";
    }
}
