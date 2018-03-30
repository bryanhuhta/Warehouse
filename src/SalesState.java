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

        logout();
    }

    private void becomeClient() {
        System.out.println("sales: Become a client");
    }

    private void addClient() {
        System.out.println("sales: add client");
        //UserInterface.instance().addClient();
    }

    private void addProduct() {
        System.out.println("sales: add product");
        //UserInterface.instance().addProduct();
    }

    private void listProducts() {
        System.out.println("sales: list products");
        //UserInterface.instance().listProducts();
    }

    private void listManufacturers() {
        System.out.println("sales: list manufacturers");
        //UserInterface.instance().listManufacturers();
    }

    private void logout() {
        int nextState = 0;

        switch (WarehouseUiContext.instance().getCurrentUser()) {
            case 2:
                // User is sales, logout.
                nextState = 0;
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
}
