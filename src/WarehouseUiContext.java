import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class WarehouseUiContext extends WarehouseContext {
    public static final int NUM_STATES = 4;
    public static final int NONE       = 0,
                            CLIENT     = 1,
                            SALES      = 2,
                            MANAGER    = 3;

    private static WarehouseUiContext context;
    private static Warehouse warehouse;

    private int currentUser;
    private String userId;

    private BufferedReader reader =
            new BufferedReader(new InputStreamReader(System.in));

    private WarehouseUiContext() {
        super(NUM_STATES);

        if (yesOrNo("Load from disk?")) {
            retrieve();
        }
        else {
            warehouse = Warehouse.instance();
        }

        currentUser = NONE;

        addState(LoginState.instance());
        addTransition(0, 0, -1);
        for (int i = 1; i < NUM_STATES; ++i) {
            addTransition(0, i, i);
        }

        addState(ClientState.instance());
        for (int i = 0; i < NUM_STATES; ++i) {
            addTransition(1, i, i);
        }

        addState(SalesState.instance());
        for (int i = 0; i < NUM_STATES; ++i) {
            addTransition(2, i, i);
        }


        addState(ManagerState.instance());
        for (int i = 0; i < NUM_STATES; ++i) {
            addTransition(3, i, i);
        }

    }

    public static WarehouseUiContext instance() {
        if (context == null) {
            context = new WarehouseUiContext();
        }

        return context;
    }

    public void setCurrentUser(int user) {
        currentUser = user;
    }

    public int getCurrentUser() {
        return currentUser;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    protected void terminate() {
        if (yesOrNo("Save to disk?")) {
            if (warehouse.save()) {
                System.out.println("Save successful.");
            }
            else {
                System.out.println("Save unsuccessful.");
            }
        }

        System.out.println("Exiting.");
        System.exit(0);
    }

    private void retrieve() {
        try {
            Warehouse tempWarehouse = Warehouse.retrieve();

            if (tempWarehouse != null) {
                System.out.println("Loaded from disk.");
                warehouse = tempWarehouse;
            }
            else {
                System.out.println("File does not exist. Creating new warehouse.");
                warehouse = Warehouse.instance();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
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

    private boolean yesOrNo(String prompt) {
        String more = getToken(prompt + " ( [y]es | [n]o )");
        return (more.charAt(0) == 'y' || more.charAt(0) == 'Y');
    }

    public static void main(String[] args) {
        WarehouseUiContext.instance().start();
    }
}
