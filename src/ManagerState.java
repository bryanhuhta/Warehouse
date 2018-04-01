public class ManagerState extends WarehouseState {
    private static final int LOGOUT             = 0,
                             BECOME_CLIENT      = 1,
                             BECOME_SALES       = 2,
                             ADD_MANUFACTURER   = 3;

    private static ManagerState managerState;

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

        String menu = " [ " + LOGOUT + " ] to logout\n" +
                " [ " + BECOME_CLIENT + " ] to become a client\n" +
                " [ " + BECOME_SALES + " ] to become sales\n" +
                " [ " + ADD_MANUFACTURER + " ] to add a manufacturer\n";

        do {
            System.out.println(menu);
            command = getCommand(LOGOUT, ADD_MANUFACTURER);

            switch (command) {
                case BECOME_CLIENT:
                    becomeClient();
                    break;

                case BECOME_SALES:
                    becomeSales();
                    break;

                case ADD_MANUFACTURER:
                    addManufacturer();
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
        WarehouseUiContext.instance().changeState(0);
    }
}
