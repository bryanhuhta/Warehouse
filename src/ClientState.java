public class ClientState extends WarehouseState {
    private static final int LOGOUT         = 0,
                             LIST_PRODUCTS  = 1,
                             PLACE_ORDER    = 2,
                             LIST_ORDERS    = 3,
                             GET_BALANCE    = 4,
                             MAKE_PAYMENT   = 5;

    private static ClientState clientState;

    private ClientState() {
        super();
    }

    public static ClientState instance() {
        if (clientState == null) {
            clientState = new ClientState();
        }

        return clientState;
    }

    @Override
    public void run() {
        int command;

        String menu = buildMenu();
        String uid = WarehouseUiContext.instance().getUserId();

        do {
            System.out.println(menu);
            command = getCommand(LOGOUT, MAKE_PAYMENT);

            switch (command) {
                case LIST_PRODUCTS:
                    UserInterface.instance().listStockedProducts();
                    break;

                case PLACE_ORDER:
                    UserInterface.instance().placeClientOrder();
                    break;

                case LIST_ORDERS:
                    UserInterface.instance().listOrdersByClient(uid);
                    break;

                case GET_BALANCE:
                    UserInterface.instance().getClientBalance(uid);
                    break;

                case MAKE_PAYMENT:
                    UserInterface.instance().makeClientPayment(uid);
                    break;

                case LOGOUT:
                    break;

                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        } while (command != LOGOUT);

        logout();
    }

    private void listProducts() {
        System.out.println("List products");
    }

    private void placeOrder() {
        System.out.println("client: Place Order");
        //UserInterface.instance().placeClientOrder();
    }

    private void listOrders() {
        System.out.println("client: List orders");
        //UserInterface.instance().listOrdersByClient();
    }

    private void getBalance() {
        System.out.println("client: get balance");
        //UserInterface.instance().getClientBalance();
    }

    private void makePayment() {
        System.out.println("client: make payment");
        //UserInterface.instance().acceptClientPayment();
    }

    private void logout() {
        int nextState = 0;

        switch (WarehouseUiContext.instance().getCurrentUser()) {
            case 1:
                // User is client, go back to login.
                nextState = 0;
                WarehouseUiContext.instance().setUserId(null);
                WarehouseUiContext.instance()
                        .setCurrentUser(WarehouseUiContext.instance().NONE);
                break;

            case 2:
                // User is sales, go back to sales.
                nextState = 2;
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
                formatMenuItem(LIST_PRODUCTS,
                        "to list products") +
                formatMenuItem(PLACE_ORDER,
                        "to place an order") +
                formatMenuItem(LIST_ORDERS,
                        "to list orders") +
                formatMenuItem(GET_BALANCE,
                        "to get balance") +
                formatMenuItem(MAKE_PAYMENT,
                        "to make a payment");
    }

    private String formatMenuItem(int optionId, String description) {
        return " [ " + optionId + " ]\t" + description + "\n";
    }
}
