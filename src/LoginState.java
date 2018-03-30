import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class LoginState extends WarehouseState {
    private static final int EXIT       = 0,
                             CLIENT     = 1,
                             SALES      = 2,
                             MANAGER    = 3;

    private WarehouseUiContext context;
    private static LoginState loginState;

    private LoginState() {
        super();
        context.instance().addState(loginState);

        // Build transition matrix.
        for (int i = 0; i < context.NUM_STATES; ++i) {
            context.instance().addTransition(0, i, 1);
        }
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

        String menu = " [ " + EXIT + " ] to exit\n" +
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

                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        } while (command != EXIT);
    }

    private void login(int code) {
        context.setCurrentUser(code);
        context.changeState(code);
    }
}
