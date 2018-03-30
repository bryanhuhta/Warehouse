// Author: Bryan Huhta
// Author: Wencel Kust

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.StringTokenizer;

import static java.lang.System.exit;

public class UserInterface {
    private static final int HELP = 0,
            SAVE = 1,
            ADD_CLIENT = 2,
            ADD_PRODUCT = 3,
            ADD_MANUFACTURERS = 4,
            ADD_SUPPLIER = 5,
            DELETE_SUPPLIER = 6,
            LIST_CLIENTS = 7,
            LIST_PRODUCTS = 8,
            LIST_MANUFACTURERS = 9,
            LIST_SUPPLIERS = 10,
            LIST_ORDERS = 11,
            LIST_CLIENTS_OUTSTANDING_BALANCE = 12,
            GET_WAITLISTED_ORDERS_PRODUCT = 13,
            GET_WAITLISTED_ORDERS_CLIENT = 14,
            ACCEPT_CLIENT_PAYMENT = 15,
            PLACE_CLIENT_ORDER = 16,
            PLACE_MANUFACTURER_ORDER = 17,
            PROCESS_MANUFACTURER_ORDER = 18,
            EXIT = 19;

    private static UserInterface ui;
    private BufferedReader reader = new BufferedReader(
            new InputStreamReader(System.in));
    private static Warehouse warehouse;

    // Constructors.
    private UserInterface() {
        if (response("Load saved session?")) {
            retrieve();
        } else {
            warehouse = Warehouse.instance();
        }
    }

    public static UserInterface instance() {
        if (ui == null) {
            return ui = new UserInterface();
        } else {
            return ui;
        }
    }
    // End Constructors.

    // Process commands.
    private boolean response(String prompt) {
        String next = getToken(prompt + " (y/n)");
        return (next.charAt(0) == 'y' || next.charAt(0) == 'Y');
    }

    private String getToken(String prompt) {
        do {
            try {
                System.out.println(prompt);

                String line = reader.readLine();
                StringTokenizer tokenizer =
                        new StringTokenizer(line, "\n\r\f");

                if (tokenizer.hasMoreTokens()) {
                    return tokenizer.nextToken();
                }
            } catch (Exception e) {
                exit(2);
            }
        } while (true);
    }

    private int getCommand() {
        do {
            try {
                int value = Integer.parseInt((getToken("Type " + HELP
                        + " for help. Enter command: ")));
                if (value <= EXIT && value >= HELP) {
                    return value;
                }
            } catch (Exception e) {
                System.out.println("Command must be a number.");
            }
        } while (true);
    }

    private void process() {
        int command;
        help();

        while ((command = getCommand()) != EXIT) {
            switch (command) {
                case HELP:
                    help();
                    break;

                case SAVE:
                    save();
                    break;

                case ADD_CLIENT:
                    addClient();
                    break;

                case ADD_PRODUCT:
                    addProduct();
                    break;

                case ADD_MANUFACTURERS:
                    addManufacturers();
                    break;

                case ADD_SUPPLIER:
                    addSupplier();
                    break;

                case DELETE_SUPPLIER:
                    deleteSupplier();
                    break;

                case LIST_CLIENTS:
                    listClients();
                    break;

                case LIST_PRODUCTS:
                    listProducts();
                    break;

                case LIST_MANUFACTURERS:
                    listManufacturers();
                    break;

                case LIST_SUPPLIERS:
                    listSuppliers();
                    break;

                case LIST_ORDERS:
                    listOrders();
                    break;

                case LIST_CLIENTS_OUTSTANDING_BALANCE:
                    listOutstandingBalances();
                    break;

                case GET_WAITLISTED_ORDERS_PRODUCT:
                    getProductWaitlist();
                    break;

                case GET_WAITLISTED_ORDERS_CLIENT:
                    getClientWaitlist();
                    break;

                case ACCEPT_CLIENT_PAYMENT:
                    acceptClientPayment();
                    break;

                case PLACE_CLIENT_ORDER:
                    placeClientOrder();
                    break;

                case PLACE_MANUFACTURER_ORDER:
                    placeManufacturerOrder();
                    break;

                case PROCESS_MANUFACTURER_ORDER:
                    processManufacturerOrder();
                    break;
            }
        }

        System.out.println("Exiting...");
    }
    // End process commands.

    // Commands:
    // 0.
    private void help() {
        String message = "Commands are:\n" +
                "[ " + HELP + " ] for help\n" +
                "[ " + SAVE + " ] to save data\n" +
                "[ " + ADD_CLIENT + " ] to add a client\n" +
                "[ " + ADD_PRODUCT + " ] to add products\n" +
                "[ " + ADD_MANUFACTURERS + " ] to add a manufacturer\n" +
                "[ " + ADD_SUPPLIER + " ] to add a supplier\n" +
                "[ " + DELETE_SUPPLIER + " ] to delete a supplier\n" +
                "[ " + LIST_CLIENTS + " ] to list clients\n" +
                "[ " + LIST_PRODUCTS + " ] to list products\n" +
                "[ " + LIST_MANUFACTURERS + " ] to list manufacturers\n" +
                "[ " + LIST_SUPPLIERS + " ] to list suppliers\n" +
                "[ " + LIST_ORDERS + " ] to list orders\n" +
                "[ " + LIST_CLIENTS_OUTSTANDING_BALANCE + " ] " +
                "to list clients with an outstanding balance\n" +
                "[ " + GET_WAITLISTED_ORDERS_PRODUCT + " ] " +
                "to list waitlisted orders for a product\n" +
                "[ " + GET_WAITLISTED_ORDERS_CLIENT + " ] " +
                "to list waitlisted orders for a client\n" +
                "[ " + ACCEPT_CLIENT_PAYMENT + " ] " +
                "to accept a client payment\n" +
                "[ " + PLACE_CLIENT_ORDER + " ] to place a client order\n" +
                "[ " + PLACE_MANUFACTURER_ORDER + " ] " +
                "to place a manufacturer order\n" +
                "[ " + PROCESS_MANUFACTURER_ORDER + " ] " +
                "to process a manufacturer order\n" +
                "[ " + EXIT + " ] to exit";

        System.out.println(message);
    }

    // 1.
    private void save() {
        if (warehouse.save()) {
            System.out.println("Warehouse saved to disk");
        } else {
            System.out.println("Could not save to disk");
        }
    }

    // 2.
    public void addClient() {
        Client client = warehouse.addClient();

        if (client != null) {
            System.out.println("Added: " + client);
        } else {
            System.out.println("Cannot add a client.");
        }
    }

    // 3.
    public void addProduct() {
        String name = null;
        Product product = null;
        int count = 0;

        System.out.println("Enter product names one at a time. " +
                "Press return twice to finish list.");

        while (true) {
            do {
                try {
                    System.out.print("Enter product name: ");
                    name = reader.readLine();
                } catch (Exception e) {
                    System.out.println("Invalid name, try again.");
                    e.printStackTrace();
                }
            } while (name == null);

            if (name.equals("")) {
                break;
            }

            product = warehouse.addProduct(name);
            if (product != null) {
                System.out.println("Added: " + product);
                ++count;
            } else {
                System.out.println("Cannot add product.");
            }
        }

        System.out.println("Added [ " + count + " ] items.");
    }

    // 4.
    private void addManufacturers() {
        String name = null;
        Manufacturer manufacturer = null;

        do {
            try {
                System.out.print("Enter a manufacturer name: ");
                name = reader.readLine();
            } catch (Exception e) {
                System.out.println("Invalid name, try again.");
                e.printStackTrace();
            }
        } while (name == null);

        manufacturer = warehouse.addManufacturer(name);
        if (manufacturer != null) {
            System.out.println("Added: " + manufacturer);
        } else {
            System.out.println("Cannot add manufacturer.");
        }
    }

    // 5.
    private void addSupplier() {
        String mid = null;
        String pid = null;
        int quantity = -1;
        double cost = -1;
        Supplier supplier = null;

        // Collect the mid and pid.
        do {
            try {
                System.out.print("Enter a manufacturer id: ");
                mid = reader.readLine();

                System.out.print("Enter a product id: ");
                pid = reader.readLine();
            } catch (Exception e) {
                System.out.println("Invalid id(s), try again.");
                e.printStackTrace();
            }
        } while (mid == null && pid == null);

        // Collect the quantity and cost.
        do {
            try {
                System.out.print("Enter quantity: ");
                quantity = Integer.parseInt(reader.readLine());

                System.out.print("Enter cost: ");
                cost = Double.parseDouble(reader.readLine());
            } catch (Exception e) {
                System.out.println("Invalid input, try again.");
                e.printStackTrace();
            }
        } while (quantity < 0 && cost < 0);

        // Create the supplier.
        supplier = warehouse.addSupplier(mid, pid, quantity, cost);
        if (supplier != null) {
            System.out.println("Added:\n" + supplier);
        } else {
            System.out.println("Cannot add supplier.");
        }
    }

    // 6.
    private void deleteSupplier() {
        String mid = null;
        String pid = null;

        // Collect the mid and pid.
        do {
            try {
                System.out.print("Enter a manufacturer id: ");
                mid = reader.readLine();

                System.out.print("Enter a product id: ");
                pid = reader.readLine();
            } catch (Exception e) {
                System.out.println("Invalid id(s), try again.");
                e.printStackTrace();
            }
        } while (mid == null && pid == null);

        if (warehouse.deleteSupplier(mid, pid)) {
            System.out.println("Deleted supplier.");
        } else {
            System.out.println("Cannot delete supplier.");
        }
    }

    // 7.
    private void listClients() {
        Iterator iterator = warehouse.getClients();

        while (iterator.hasNext()) {
            Client temp = (Client) iterator.next();

            System.out.println(temp);
        }
    }

    // 8.
    public void listProducts() {
        Iterator iterator = warehouse.getProducts();

        while (iterator.hasNext()) {
            Product temp = (Product) iterator.next();

            System.out.println(temp);
        }
    }

    // 9.
    public void listManufacturers() {
        Iterator iterator = warehouse.getManufacturers();

        while (iterator.hasNext()) {
            Manufacturer temp = (Manufacturer) iterator.next();

            System.out.println(temp);
        }
    }

    // 10.
    private void listSuppliers() {
        Iterator iterator = warehouse.getSuppliers();

        while (iterator.hasNext()) {
            Supplier temp = (Supplier) iterator.next();

            System.out.println(temp);
        }
    }

    // 11.
    private void listOrders() {
        Iterator iterator = warehouse.getOrders();

        while (iterator.hasNext()) {
            Order temp = (Order) iterator.next();

            System.out.println(temp);
        }
    }

    // 12.
    private void listOutstandingBalances() {
        Iterator iterator = warehouse.getClients();

        while (iterator.hasNext()) {
            Client temp = (Client) iterator.next();

            if (temp.getBalance() < 0) {
                System.out.println(temp);
            }
        }
    }

    // 13.
    private void getProductWaitlist() {
        String pid = null;
        Product product = null;

        do {
            try {
                System.out.print("Enter product id: ");
                pid = reader.readLine();
            } catch (Exception e) {
                System.out.println("Invalid product id, try again.");
                e.printStackTrace();
            }
        } while (pid == null);

        product = warehouse.getProduct(pid);
        if (product == null) {
            System.out.println("Product does not exist.");
            return;
        }

        Iterator iterator = warehouse.getOrders();

        while (iterator.hasNext()) {
            Order temp = (Order) iterator.next();

            if (temp.isWaitlisted() &&
                    temp.getSupplier().getProduct() == product &&
                    temp.isClientOrder()) {
                System.out.println(temp);
            }
        }
    }

    // 14.
    private void getClientWaitlist() {
        String cid = null;
        Client client = null;

        do {
            try {
                System.out.print("Enter client id: ");
                cid = reader.readLine();
            } catch (Exception e) {
                System.out.println("Invalid client id, try again.");
                e.printStackTrace();
            }
        } while (cid == null);

        client = warehouse.getClient(cid);
        if (client == null) {
            System.out.println("Client does not exist.");
            return;
        }

        Iterator iterator = warehouse.getOrders();

        while (iterator.hasNext()) {
            Order temp = (Order) iterator.next();

            if (temp.isWaitlisted() && temp.getClient() == client &&
                    temp.isClientOrder()) {
                System.out.println(temp);
            }
        }
    }

    // 15.
    public void acceptClientPayment() {
        String cid = null;
        double payment = -1;
        Client client = null;

        // Collect cid.
        do {
            try {
                System.out.print("Enter client id: ");
                cid = reader.readLine();
            } catch (Exception e) {
                System.out.println("Invalid client id, try again.");
                e.printStackTrace();
            }
        } while (cid == null);

        // Collect payment amount.
        do {
            try {
                System.out.print("Enter payment: ");
                payment = Double.parseDouble(reader.readLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid, enter a number.");
                payment = -1;
            } catch (Exception e) {
                System.out.println("Invalid, enter a number.");
                payment = -1;
                e.printStackTrace();
            }
        } while (payment < 0);

        // Find client.
        client = warehouse.getClient(cid);
        if (client == null) {
            System.out.println("Client does not exist.");
            return;
        }

        client.chargeAccount(payment);

        System.out.println("Payment processed: " + client);
    }

    // 16.
    public void placeClientOrder() {
        String mid = null;
        String pid = null;
        String cid = null;
        int quantity = 0;

        Order order = null;
        Supplier supplier = null;

        // Collect cid.
        do {
            try {
                System.out.print("Enter client id: ");
                cid = reader.readLine();
            } catch (Exception e) {
                System.out.println("Invalid input, try again.");
                e.printStackTrace();
            }
        } while (cid == null);

        // Collect mid and pid.
        do {
            try {
                System.out.print("Enter manufacturer id: ");
                mid = reader.readLine();

                System.out.print("Enter product id: ");
                pid = reader.readLine();
            } catch (Exception e) {
                System.out.println("Invalid id(s), try again.");
                e.printStackTrace();
            }
        } while (mid == null && pid == null);

        // Collect the quantity.
        do {
            try {
                System.out.print("Enter quantity: ");
                quantity = Integer.parseInt(reader.readLine());

                if (quantity < 1) {
                    System.out.println("Invalid input, try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, try again.");
                quantity = 0;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (quantity < 1);

        // Create order.
        order = warehouse.addOrder(mid, pid, cid, quantity, true);
        if (order == null) {
            System.out.println("Cannot create order.");
            return;
        }

        // Process order.
        supplier = warehouse.getSupplier(mid, pid);
        if (supplier == null) {
            System.out.println("Cannot find supplier.");
            return;
        }

        // Check if order should be waitlisted.
        if (supplier.getQuantity() < quantity) {
            // Check if some amount of the order can be filled.
            if (supplier.getQuantity() > 0) {
                // Fill a partial order.
                order.updateQuantity(supplier.getQuantity());
                supplier.updateQuantity(supplier.getQuantity(), false);

                // Charge client.
                order.getClient().chargeAccount(-order.getCost());

                // Create new (waitlisted) order with remaining quantity.
                int remainingOrderQuantity = quantity - order.getQuantity();
                Order newOrder = warehouse.addOrder(mid, pid, cid,
                        remainingOrderQuantity, true);

                if (newOrder == null) {
                    System.out.println("Could not create a waitlisted order.");
                    return;
                }

                newOrder.setWaitlisted(true);

                System.out.println("Filled client order:\n" + order +
                        "\nWaitlisted client order:\n" + newOrder);
            } else {
                // Waitlist the order outright. The supplier has no product in
                // stock.
                order.setWaitlisted(true);
                System.out.println("Waitlisted client order:\n" + order);
            }
        } else {
            // Fill an entire order.
            supplier.updateQuantity(quantity, false);

            // Charge the client.
            order.getClient().chargeAccount(-order.getCost());

            // Flag order as filled.
            order.setWaitlisted(false);
            System.out.println("Filled client order:\n" + order);
        }
    }

    // 17.
    private void placeManufacturerOrder() {
        String mid = null;
        String pid = null;
        int quantity = -1;

        Supplier supplier = null;
        Order order = null;

        // Collect mid and pid.
        do {
            try {
                System.out.print("Enter manufacturer id: ");
                mid = reader.readLine();

                System.out.print("Enter product id: ");
                pid = reader.readLine();
            } catch (Exception e) {
                System.out.println("Invalid id(s), try again.");
                e.printStackTrace();
            }
        } while (mid == null && pid == null);

        // Collect quantity.
        do {
            try {
                System.out.print("Enter quantity: ");
                quantity = Integer.parseInt(reader.readLine());
            } catch (NumberFormatException e) {
                System.out.println("Must be a number.");
                quantity = -1;
            } catch (Exception e) {
                System.out.println("Invalid quantity, try again.");
                e.printStackTrace();
            }
        } while (quantity < 0);

        order = warehouse.addOrder(mid, pid, null, quantity, false);
        if (order == null) {
            System.out.println("Cannot create order.");
            return;
        }
        order.setWaitlisted(true);

        System.out.println("Placed manufacturer order:\n" + order);
    }

    // 18.
    private void processManufacturerOrder() {
        String oid = null;
        String response = null;
        int quantity = 0;

        Order manufacturerOrder = null;
        Iterator orderIterator = warehouse.getOrders();

        // Collect the oid.
        do {
            try {
                System.out.print("Enter order id: ");
                oid = reader.readLine();
            } catch (Exception e) {
                System.out.println("Invalid id, try again.");
                e.printStackTrace();
            }
        } while (oid == null);

        // Find the order.
        manufacturerOrder = warehouse.getOrder(oid);
        if (manufacturerOrder == null) {
            System.out.println("Order id does not exist.");
            return;
        } else if (manufacturerOrder.isClientOrder()) {
            System.out.println("Order is not a manufacturer order.");
            return;
        }
        quantity = manufacturerOrder.getQuantity();

        // Display order details.
        System.out.println("Manufacturer order:\n" + manufacturerOrder);

        // Get confirmation.
        do {
            try {
                System.out.print("Confirm? (y/n): ");
                response = reader.readLine();
            } catch (Exception e) {
                System.out.println("Invalid response, try again.");
                e.printStackTrace();
            }
        } while (response == null || !(response.equalsIgnoreCase("y")
                || response.equalsIgnoreCase("n")));

        // Fill waitlisted orders.
        while (orderIterator.hasNext() && quantity > 0) {
            Order waitlist = (Order) orderIterator.next();

            // waitlist has the same supplier as the incoming order AND
            // waitlist is waitlisted AND
            // waitlist is a clientOrder.
            if (waitlist.getSupplier() == manufacturerOrder.getSupplier() &&
                    waitlist.isWaitlisted() && waitlist.isClientOrder()) {
                // Check if order can only partially filled.
                if (waitlist.getQuantity() > quantity) {
                    // Fill a portion of the order.
                    int remainingClientQuantity = waitlist.getQuantity() - quantity;
                    waitlist.updateQuantity(quantity);
                    waitlist.setWaitlisted(false);

                    // Charge the client.
                    waitlist.getClient().chargeAccount(
                            -manufacturerOrder.getSupplier().getCost() * quantity);

                    // Create a new order for the remaining quantity.
                    Order newOrder = warehouse.addOrder(
                            manufacturerOrder.getSupplier().getManufacturer().getId(),
                            manufacturerOrder.getSupplier().getProduct().getProductId(),
                            manufacturerOrder.getClient().getId(),
                            remainingClientQuantity,
                            true);
                    newOrder.setWaitlisted(true);

                    quantity = 0;

                    System.out.println("Filled order:\n" + waitlist +
                            "Waitlisted order:\n" + newOrder);
                } else {
                    // Order can be filled completely.
                    waitlist.setWaitlisted(false);

                    // Charge client.
                    waitlist.getClient().chargeAccount(-manufacturerOrder.getCost());

                    // Update remaining quantity.
                    quantity -= waitlist.getQuantity();

                    System.out.println("Filled order:\n" + waitlist);
                }
            }
        }

        manufacturerOrder.setWaitlisted(false);

        // Add remaining to stock (if any).
        if (quantity > 0) {
            manufacturerOrder.getSupplier().updateQuantity(quantity, true);

            System.out.println("Stocked supplier: " + manufacturerOrder.getSupplier());
        }
    }

    public void listOrdersByClient() {
        String cid = null;

        // Collect cid.
        do {
            try {
                System.out.print("Enter client id: ");
                cid = reader.readLine();
            } catch (Exception e) {
                System.out.println("Invalid input, try again.");
                e.printStackTrace();
            }
        } while (cid == null);

        Iterator iterator = warehouse.getOrders();

        while (iterator.hasNext()) {
            Order temp = (Order) iterator.next();

            if (temp.getClient().getId().equals(cid)) {
                System.out.println(temp);
            }
        }
    }

    public void getClientBalance() {
        String cid = null;

        // Collect cid.
        do {
            try {
                System.out.print("Enter client id: ");
                cid = reader.readLine();
            } catch (Exception e) {
                System.out.println("Invalid input, try again.");
                e.printStackTrace();
            }
        } while (cid == null);

        Client client = warehouse.getClient(cid);

        System.out.println("Balance: " + client.getBalance());
    }
    // End commands.

    // Helper methods.
    private void retrieve() {
        try {
            Warehouse tempWarehouse = Warehouse.retrieve();

            if (tempWarehouse != null) {
                System.out.println("Warehouse loaded from  disk");
                warehouse = tempWarehouse;
            } else {
                System.out.println("File not on disk; creating new file");
                warehouse = Warehouse.instance();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // End helper methods.
}
