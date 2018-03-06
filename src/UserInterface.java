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
    private static final int HELP                   = 0,
                             SAVE                   = 1,
                             ADD_CLIENT             = 2,
                             ADD_PRODUCT            = 3,
                             ADD_MANUNFACTURERS     = 4,
                             LIST_CLIENTS           = 5,
                             LIST_PRODUCTS          = 6,
                             LIST_MANUFACTURERS     = 7,
                             EXIT                   = 8;

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

                case LIST_CLIENTS:
                    listClients();
                    break;

                case LIST_PRODUCTS:
                    listProducts();
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
                "[ " + ADD_PRODUCT + " ] to add a product\n" +
                "[ " + ADD_MANUNFACTURERS + " ] to add a manufacturer\n" +
                "[ " + LIST_CLIENTS + " ] to list clients\n" +
                "[ " + LIST_PRODUCTS + " ] to list products\n" +
                "[ " + LIST_MANUFACTURERS + " ] to list manufacturers\n" +
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

        do {
            try {
                System.out.print("Enter product name: ");
                name = reader.readLine();
            }
            catch (Exception e) {
                System.out.println("Invalid name, try again.");
                e.printStackTrace();
            }
        } while (name == null);

        product = warehouse.addProduct(name);
        if (product != null) {
            System.out.println("Added: " + product);
        }
        else {
            System.out.println("Cannot add product.");
        }
    }

    // 4.
    private void addManunfacturers() {
        String name = null;
        Manufacturer manufacturer = null;

        do {
            try {
                System.out.print("Enter a manufacturer name: ");
                name = reader.readLine();
            }
            catch (Exception e) {
                System.out.println("Invalid name, try again");
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
    // 4.
    private void listClients() {
        Iterator iterator = warehouse.getClients();

        while (iterator.hasNext()) {
            Client temp = (Client) iterator.next();

            System.out.println(temp);
        }
    }

    // 5.
    private void listProducts() {
        Iterator iterator = warehouse.getProducts();

        while (iterator.hasNext()) {
            Product temp = (Product) iterator.next();

            System.out.println(temp);
        }
    }

    // 6.
    private void listManufacturers() {
        System.out.println("list manufac");
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
