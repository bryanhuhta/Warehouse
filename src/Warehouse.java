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

    private static Warehouse warehouse;

    // Construct Warehouse.
    private Warehouse() {
        clientList = ClientList.instance();
        productList = ProductList.instance();
        manufacturerList = ManufacturerList.instance();
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
    // End add/delete methods.

    // Getters.
    public Client getClient(String cid) {
        Client client = null;
        Client temp = null;
        Iterator iterator = warehouse.getClients();

        while (iterator.hasNext()) {
            temp = (Client) iterator.next();

            if (temp.getId().equals(cid)) {
                client = temp;
                break;
            }
        }

        return client;
    }
    // End getters.

    // Iterators.
    public Iterator<Client> getClients() {
        return clientList.getClients();
    }

    public Iterator<Product> getProducts() {
        return productList.getProducts();
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
