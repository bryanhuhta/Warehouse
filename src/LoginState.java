import java.io.BufferedReader;
import java.io.InputStreamReader;

public class LoginState extends WarehouseState {
    private static final int EXIT       = 0,
                             CLIENT     = 1,
                             SALES      = 2,
                             MANAGER    = 3;

    private static LoginState loginState;
    private BufferedReader reader =
            new BufferedReader(new InputStreamReader(System.in));

    private LoginState() {
        super();
    }

    public static LoginState instance() {
        if (loginState == null) {
            loginState = new LoginState();
        }

        return loginState;
    }

    @Override
    public void run() {
        int command;

        String menu =  "\n" +
                " [ " + EXIT + " ] to exit\n" +
                " [ " + CLIENT + " ] to login as client\n" +
                " [ " + SALES + " ] to login as sales\n" +
                " [ " + MANAGER + " ] to login as manager\n";

        do {
            System.out.println(menu);
            command = getCommand(EXIT, MANAGER);

            switch (command) {
                case CLIENT:
                case SALES:
                case MANAGER:
                    login(command);
                    break;

                case EXIT:
                    break;

                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        } while (command != EXIT);

        // Exit application.
        WarehouseUiContext.instance().changeState(0);
    }

    private void login(int code) {
        Security security = new Security();
        String username = null;
        String password = null;

        // Get username.
        try {
            System.out.print("Enter username: ");
            username = reader.readLine();
        }
        catch (Exception e) {
            System.out.println("Could not read username.");
        }

        // Get password.
        try {
            System.out.print("Enter password: ");
            password = reader.readLine();
        }
        catch (Exception e) {
            System.out.println("Could not read password.");
        }

        if (security.verify(username, password)) {
            WarehouseUiContext.instance().setCurrentUser(code);
            WarehouseUiContext.instance().changeState(code);
        }
        else {
            System.out.println("Invalid username or password.");
        }
    }
}
