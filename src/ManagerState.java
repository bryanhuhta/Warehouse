import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ManagerState extends WarehouseState {
    private static final int LOGOUT             = 0,
                             BECOME_CLIENT      = 1,
                             BECOME_SALES       = 2,
                             MODIFY_SALE_PRICE  = 3,
                             ADD_MANUFACTURER   = 4,
                             ADD_SUPPLIER       = 5;

    private static ManagerState managerState;
    private BufferedReader reader =
            new BufferedReader(new InputStreamReader(System.in));

    private ManagerState() {
        super();
    }

    public static ManagerState instance() {
        if (managerState == null) {
            managerState = new ManagerState();
        }

        return  managerState;
    }

    @Override
    public void run() {
        int command;

        String menu = buildMenu();

        do {
            System.out.println(menu);
            command = getCommand(LOGOUT, ADD_SUPPLIER);

            switch (command) {
                case BECOME_CLIENT:
                    if (validate()) {
                        WarehouseUiContext.instance().changeState(1);
                    }

                    break;

                case BECOME_SALES:
                    if (validate()) {
                        WarehouseUiContext.instance().changeState(2);
                    }
                    break;

                case MODIFY_SALE_PRICE:
                    if (validate()) {
                        UserInterface.instance().modifySalePrice();
                    }
                    break;

                case ADD_MANUFACTURER:
                    if (validate()) {
                        UserInterface.instance().addManufacturers();
                    }
                    break;

                case ADD_SUPPLIER:
                    if (validate()) {
                        UserInterface.instance().addSupplier();
                    }

                case LOGOUT:
                    break;

                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        } while (command != LOGOUT);

        logout();
    }

    private void becomeClient() {
        WarehouseUiContext.instance().changeState(1);
    }

    private void becomeSales() {
        WarehouseUiContext.instance().changeState(2);
    }

    private void addManufacturer() {
        System.out.println("manager: add manufacturer");
        //UserInterface.instance().addManufacturers();
    }

    private void logout() {
        WarehouseUiContext.instance().setUserId(null);
        WarehouseUiContext.instance().changeState(0);
    }

    private boolean validate() {
        Security security = new Security();
        String password = null;
        System.out.print("Enter password: ");

        try {
            password = reader.readLine();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return security.verify(WarehouseUiContext.instance().getUserId(), password);
    }

    private String buildMenu() {
        return "User: " + WarehouseUiContext.instance().getUserId() + "\n" +
                formatMenuItem(LOGOUT,
                        "to logout") +
                formatMenuItem(BECOME_CLIENT,
                        "to become a client") +
                formatMenuItem(BECOME_SALES,
                        "to become sales") +
                formatMenuItem(MODIFY_SALE_PRICE,
                        "to modify a sale price") +
                formatMenuItem(ADD_MANUFACTURER,
                        "to add a manufacturer") +
                formatMenuItem(ADD_SUPPLIER,
                        "to add a supplier");
    }

    private String formatMenuItem(int optionId, String description) {
        return " [ " + optionId + " ]\t" + description + "\n";
    }
}
