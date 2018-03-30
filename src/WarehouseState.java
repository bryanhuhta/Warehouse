import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public abstract class WarehouseState {
    private BufferedReader reader =
            new BufferedReader(new InputStreamReader(System.in));

    protected WarehouseState() {

    }

    public abstract void run();

    protected String getToken(String prompt) {
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

    protected int getCommand(int first, int last) {
        do {
            try {
                int value = Integer.parseInt(getToken("Enter command:" ));
                if (value >= first && value <= last) {
                    return value;
                }
            }
            catch (NumberFormatException nfe) {
                System.out.println("Enter a number");
            }
        } while (true);
    }
}
