// Author: Bryan Huhta
// Author: Wencel Kust

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.ref.Cleaner;
import java.util.Iterator;
import java.util.StringTokenizer;

import static java.lang.System.exit;
import static java.lang.System.out;

public class UserInterface {
    private static final int HELP                               = 0,
                             SAVE                               = 1,
                             ADD_CLIENT                         = 2,
                             ADD_PRODUCT                        = 3,
                             ADD_MANUFACTURERS                  = 4,
                             ADD_SUPPLIER                       = 5,
                             DELETE_SUPPLIER                    = 6,
                             LIST_CLIENTS                       = 7,
                             LIST_PRODUCTS                      = 8,
                             LIST_MANUFACTURERS                 = 9,
                             LIST_SUPPLIERS                     = 10,
                             LIST_CLIENTS_OUTSTANDING_BALANCE   = 11,
                             GET_WAITLISTED_ORDERS_PRODUCT      = 12,
                             GET_WAITLISTED_ORDERS_CLIENT       = 13,
                             ACCEPT_CLIENT_PAYMENT              = 14,
                             PLACE_CLIENT_ORDER                 = 15,
                             EXIT                               = 16;

    private static  UserInterface ui;
    private BufferedReader reader = new BufferedReader(
            new InputStreamReader(System.in));
    private static Warehouse warehouse;

    // Constructors.
    private UserInterface() {
        if (response("Load saved session?")) {
            retrieve();
        }
        else {
            warehouse = Warehouse.instance();
        }
    }

    public static UserInterface instance() {
        if (ui == null) {
            return ui = new UserInterface();
        }
        else {
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
            }
            catch (Exception e) {
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
            }
            catch (Exception e) {
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

                case  LIST_SUPPLIERS:
                    listSuppliers();
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
                    placeOrder();
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
                "[ " + LIST_CLIENTS_OUTSTANDING_BALANCE + " ] " +
                    "to list clients with an outstanding balance\n" +
                "[ " + GET_WAITLISTED_ORDERS_PRODUCT + " ] " +
                    "to list waitlisted orders for a product\n" +
                "[ " + GET_WAITLISTED_ORDERS_CLIENT + " ] " +
                    "to list waitlisted orders for a client\n" +
                "[ " + ACCEPT_CLIENT_PAYMENT + " ] " +
                    "to accept a client payment\n" +
                "[ " + PLACE_CLIENT_ORDER + " ] to place a client order\n" +
                "[ " + EXIT + " ] to exit";

        System.out.println(message);
    }

    // 1.
    private void save() {
        if (warehouse.save()) {
            System.out.println("Warehouse saved to disk");
        }
        else {
            System.out.println("Could not save to disk");
        }
    }

    // 2.
    private void addClient() {
        Client client = warehouse.addClient();

        if (client != null) {
            System.out.println("Added: " + client);
        }
        else {
            System.out.println("Cannot add a client.");
        }
    }

    // 3.
    private void addProduct() {
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
            }
            catch (Exception e) {
                System.out.println("Invalid name, try again.");
                e.printStackTrace();
            }
        } while (name == null);

        manufacturer = warehouse.addManufacturer(name);
        if (manufacturer != null) {
            System.out.println("Added: " + manufacturer);
        }
        else {
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
            }
            catch (Exception e) {
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
            }
            catch (Exception e) {
                System.out.println("Invalid input, try again.");
                e.printStackTrace();
            }
        } while (quantity < 0 && cost < 0);

        // Create the supplier.
        supplier = warehouse.addSupplier(mid, pid, quantity, cost);
        if (supplier != null) {
            System.out.println("Added:\n" + supplier);
        }
        else {
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
            }
            catch (Exception e) {
                System.out.println("Invalid id(s), try again.");
                e.printStackTrace();
            }
        } while (mid == null && pid == null);

        if (warehouse.deleteSupplier(mid, pid)) {
            System.out.println("Deleted supplier.");
        }
        else {
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
    private void listProducts() {
        Iterator iterator = warehouse.getProducts();

        while (iterator.hasNext()) {
            Product temp = (Product) iterator.next();

            System.out.println(temp);
        }
    }

    // 9.
    private void listManufacturers() {
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
    private void listOutstandingBalances() {
        Iterator iterator = warehouse.getClients();

        while (iterator.hasNext()) {
            Client temp = (Client) iterator.next();

            if (temp.getBalance() < 0) {
                System.out.println(temp);
            }
        }
    }

    // 12.
    private void getProductWaitlist() {

    }

    // 13.
    private void getClientWaitlist() {

    }

    // 14.
    private void acceptClientPayment() {

    }

    // 15.
    private void placeOrder() {

    }
    // End commands.

    private void retrieve() {
        try {
            Warehouse tempWarehouse = Warehouse.retrieve();

            if (tempWarehouse != null) {
                System.out.println("Warehouse loaded from  disk");
                warehouse = tempWarehouse;
            }
            else {
                System.out.println("File not on disk; creating new file");
                warehouse = Warehouse.instance();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        UserInterface.instance().process();
    }
}
