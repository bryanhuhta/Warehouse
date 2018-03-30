import java.util.Iterator;

public class ClientState extends WarehouseState {
    private static final int LOGOUT         = 0,
                             LIST_PRODUCTS  = 1,
                             PLACE_ORDER    = 2,
                             LIST_ORDERS     = 3,
                             GET_BALANCE    = 4,
                             MAKE_PAYMENT   = 5;

    private WarehouseUiContext context;
    private static ClientState clientState;

    private ClientState() {
        super();
        context.instance().addState(clientState);

        // Build transition matrix.
        for (int i = 0; i < context.NUM_STATES; ++i) {
            context.instance().addTransition(0, i, 1);
        }
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

        String menu = " [ " + LOGOUT + " ] to logout\n" +
                " [ " + LIST_PRODUCTS + " ] to list all products\n" +
                " [ " + PLACE_ORDER + " ] to place an order\n" +
                " [ " + LIST_ORDERS + " ] to get client orders\n" +
                " [ " + GET_BALANCE + " ] to get balance\n" +
                " [ " + MAKE_PAYMENT + " ] to make a payment\n";

        do {
            System.out.println(menu);
            command = getCommand(LOGOUT, MAKE_PAYMENT);

            switch (command) {
                case LIST_PRODUCTS:
                    listProducts();
                    break;

                case PLACE_ORDER:
                    placeOrder();
                    break;

                case LIST_ORDERS:
                    listOrders();
                    break;

                case GET_BALANCE:
                    getBalance();
                    break;

                case MAKE_PAYMENT:
                    makePayment();
                    break;

                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        } while (command != LOGOUT);
    }

    private void listProducts() {
        UserInterface.instance().listProducts();
    }

    private void placeOrder() {
        UserInterface.instance().placeClientOrder();
    }

    private void listOrders() {
        UserInterface.instance().listOrdersByClient();
    }

    private void getBalance() {
        UserInterface.instance().getClientBalance();
    }

    private void makePayment() {
        UserInterface.instance().acceptClientPayment();
    }

}
