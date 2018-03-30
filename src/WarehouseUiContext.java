import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class WarehouseUiContext extends WarehouseContext {
    private static final int NUM_STATES = 4;
    private static WarehouseUiContext context;
    private static Warehouse warehouse;

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
    }

    public WarehouseUiContext instance() {
        if (context == null) {
            context = new WarehouseUiContext();
        }

        return context;
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
        String more = getToken(prompt + " (Y|y)[es] or anything else for no");
        if (more.charAt(0) != 'y' && more.charAt(0) != 'Y') {
            return false;
        }
        return true;
    }
}