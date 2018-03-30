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

    private BufferedReader reader =
            new BufferedReader(new InputStreamReader(System.in));

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

        String menu = " [ " + CLIENT + " ] to login as client\n" +
                " [ " + SALES + " ] to login as sales\n" +
                " [ " + MANAGER + " ] to login as manager\n" +
                " [ " + EXIT + " ] to exit\n";

        do {
            System.out.println(menu);
            command = getCommand();

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

    private String getToken(String prompt) {
        do {
            try {
                System.out.println(prompt);
                String line = reader.readLine();
                StringTokenizer tokenizer = new StringTokenizer(line,"\n\r\f");
                if (tokenizer.hasMoreTokens()) {
                    return tokenizer.nextToken();
                }
            } catch (IOException ioe) {
                System.exit(0);
            }
        } while (true);
    }

    private int getCommand() {
        do {
            try {
                int value = Integer.parseInt(getToken("Enter command:" ));
                if (value >= EXIT && value <= CLIENT) {
                    return value;
                }
            }
            catch (NumberFormatException nfe) {
                System.out.println("Enter a number");
            }
        } while (true);
    }
}
