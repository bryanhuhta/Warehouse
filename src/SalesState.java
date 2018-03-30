public class SalesState extends WarehouseState {
    private static final int LOGOUT             = 0,
                             BECOME_CLIENT      = 1,
                             ADD_CLIENT         = 2,
                             ADD_PRODUCT        = 3,
                             LIST_PRODUCTS      = 4,
                             LIST_MANUFACTURERS = 5;

    private WarehouseUiContext context;
    private static SalesState salesState;

    private SalesState() {
        super();
        context.instance().addState(salesState);

        // Build transition matrix.
        for (int i = 0; i < context.NUM_STATES; ++i) {
            context.instance().addTransition(0, i, 1);
        }
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

        String menu = " [ " + LOGOUT + " ] to logout\n" +
                " [ " + BECOME_CLIENT + " ] to become a client\n" +
                " [ " + ADD_CLIENT + " ] to add a client\n" +
                " [ " + ADD_PRODUCT + " ] to add a product\n" +
                " [ " + LIST_PRODUCTS + " ] to list products\n" +
                " [ " + LIST_MANUFACTURERS + " ] to list manufacturers\n";

        do {
            System.out.println(menu);
            command = getCommand(LOGOUT, LIST_MANUFACTURERS);

            switch (command) {
                case BECOME_CLIENT:
                    becomeClient();
                    break;

                case ADD_CLIENT:
                    addClient();
                    break;

                case ADD_PRODUCT:
                    addProduct();
                    break;

                case LIST_PRODUCTS:
                    listProducts();
                    break;

                case LIST_MANUFACTURERS:
                    listManufacturers();
                    break;

                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        } while (command != LOGOUT);
    }

    private void becomeClient() {
        System.out.println("Become a client");
    }

    private void addClient() {
        UserInterface.instance().addClient();
    }


    private void addProduct() {
        UserInterface.instance().addProduct();
    }

    private void listProducts() {
        UserInterface.instance().listProducts();
    }

    private void listManufacturers() {
        UserInterface.instance().listManufacturers();
    }
}
