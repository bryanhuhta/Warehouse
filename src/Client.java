// Author: Bryan Huhta

import java.io.Serializable;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Client implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String CLIENT_STRING = "C";

    private String id;
    private double balance;

    public Client() {
        this.id = CLIENT_STRING + (ClientIdServer.instance()).getId();
        this.balance = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void chargeAccount(double balance) {
        this.balance += balance;
    }

    @Override
    public String toString() {
        DecimalFormat decimalFormat = new DecimalFormat("##.##");
        decimalFormat.setRoundingMode(RoundingMode.DOWN);

        return "[ id: " + id + ", balance: " +
                decimalFormat.format(balance) + " ]";
    }
}
