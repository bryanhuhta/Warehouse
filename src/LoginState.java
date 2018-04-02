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

        String menu = buildMenu();

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
            WarehouseUiContext.instance().setUserId(username);
            WarehouseUiContext.instance().changeState(code);
        }
        else {
            System.out.println("Invalid username or password.");
        }
    }

    private String buildMenu() {
        return "\n" +
                formatMenuItem(EXIT,
                        "to exit") +
                formatMenuItem(CLIENT,
                        "to login as client") +
                formatMenuItem(SALES,
                        "to login as sales") +
                formatMenuItem(MANAGER,
                        "to login as manager");
    }

    private String formatMenuItem(int optionId, String description) {
        return " [ " + optionId + " ]\t" + description + "\n";
    }
}
