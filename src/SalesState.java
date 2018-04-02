public class SalesState extends WarehouseState {
    private static final int LOGOUT                             = 0,
                             BECOME_CLIENT                      = 1,
                             ADD_CLIENT                         = 2,
                             LIST_CLIENTS                       = 3,
                             ADD_PRODUCT                        = 4,
                             LIST_PRODUCTS                      = 5,
                             LIST_STOCKED_PRODUCTS              = 6,
                             LIST_MANUFACTURERS                 = 7,
                             LIST_MANUFACTURERS_FOR_PRODUCT     = 8,
                             PROCESS_MANUFACTURER_ORDER         = 9,
                             LIST_SUPPLIERS                     = 10,
                             LIST_ORDERS                        = 11,
                             LIST_CLIENTS_OUTSTANDING_BALANCE   = 12,
                             GET_WAITLISTED_ORDERS_PRODUCT      = 13,
                             GET_WAITLISTED_ORDERS_CLIENT       = 14,
                             ACCEPT_CLIENT_PAYMENT              = 15;

    private static SalesState salesState;

    private SalesState() {
        super();
    }

    public static SalesState instance() {
        if (salesState == null) {
            salesState = new SalesState();
        }

        return salesState;
    }

    @Override
    public void run() {
        int command;

        String menu = buildMenu();

        do {
            System.out.println(menu);
            command = getCommand(LOGOUT, LIST_MANUFACTURERS);

            switch (command) {
                case BECOME_CLIENT:
                    WarehouseUiContext.instance().changeState(1);
                    break;

                case ADD_CLIENT:
                    UserInterface.instance().addClient();
                    break;

                case LIST_CLIENTS:
                    UserInterface.instance().listClients();
                    break;

                case ADD_PRODUCT:
                    UserInterface.instance().addProduct();
                    break;

                case LIST_PRODUCTS:
                    UserInterface.instance().listProducts();
                    break;

                case LIST_STOCKED_PRODUCTS:
                    UserInterface.instance().listStockedProducts();
                    break;

                case LIST_MANUFACTURERS:
                    UserInterface.instance().listManufacturers();
                    break;

                case LIST_MANUFACTURERS_FOR_PRODUCT:
                    UserInterface.instance().setListManufacturerForProduct();
                    break;

                case PROCESS_MANUFACTURER_ORDER:
                    UserInterface.instance().processManufacturerOrder();
                    break;

                case LIST_SUPPLIERS:
                    UserInterface.instance().listSuppliers();
                    break;

                case LIST_ORDERS:
                    UserInterface.instance().listOrders();
                    break;

                case LIST_CLIENTS_OUTSTANDING_BALANCE:
                    UserInterface.instance().listOutstandingBalances();
                    break;

                case GET_WAITLISTED_ORDERS_PRODUCT:
                    UserInterface.instance().getProductWaitlist();
                    break;

                case GET_WAITLISTED_ORDERS_CLIENT:
                    UserInterface.instance().getClientWaitlist();
                    break;

                case ACCEPT_CLIENT_PAYMENT:
                    UserInterface.instance().acceptClientPayment();
                    break;

                case LOGOUT:
                    // Empty case to avoid printing an error message.
                    break;

                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        } while (command != LOGOUT);

        logout();
    }

    private void logout() {
        int nextState = 0;

        switch (WarehouseUiContext.instance().getCurrentUser()) {
            case 2:
                // User is sales, logout.
                nextState = 0;
                WarehouseUiContext.instance().setUserId(null);
                WarehouseUiContext.instance()
                        .setCurrentUser(WarehouseUiContext.instance().NONE);
                break;

            case 3:
                // User is manager, go back to manager.
                nextState = 3;
                break;
        }

        WarehouseUiContext.instance().changeState(nextState);
    }

    private String buildMenu() {
        return "User: " + WarehouseUiContext.instance().getUserId() + "\n" +
            formatMenuItem(LOGOUT,
                    "to logout") +
            formatMenuItem(BECOME_CLIENT,
                    "to become a client") +
            formatMenuItem(ADD_CLIENT,
                    "to add a client") +
            formatMenuItem(LIST_CLIENTS,
                    "to list clients") +
            formatMenuItem(ADD_PRODUCT,
                    "to add a product") +
            formatMenuItem(LIST_PRODUCTS,
                    "to list products") +
            formatMenuItem(LIST_STOCKED_PRODUCTS,
                    "to list stocked products") +
            formatMenuItem(LIST_MANUFACTURERS,
                    "to list manufacturers") +
            formatMenuItem(LIST_MANUFACTURERS_FOR_PRODUCT,
                    "to list manufacturers for a product") +
            formatMenuItem(PROCESS_MANUFACTURER_ORDER,
                    "to process manufacturer order") +
            formatMenuItem(LIST_SUPPLIERS,
                    "to list suppliers") +
            formatMenuItem(LIST_ORDERS,
                    "to list orders") +
            formatMenuItem(LIST_CLIENTS_OUTSTANDING_BALANCE,
                    "to list clients with an outstanding balance") +
            formatMenuItem(GET_WAITLISTED_ORDERS_PRODUCT,
                    "to get waitlisted orders for a product") +
            formatMenuItem(GET_WAITLISTED_ORDERS_CLIENT,
                    "to get waitlisted orders for a client") +
            formatMenuItem(ACCEPT_CLIENT_PAYMENT,
                    "to process a client payment");
    }

    private String formatMenuItem(int optionId, String description) {
        return " [ " + optionId + " ]\t" + description + "\n";
    }
}
