// Author: Bryan Huhta

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class OrderIdServer implements Serializable {
    private int idCounter;
    private static OrderIdServer server;

    private OrderIdServer() {
        idCounter = 1;
    }

    public static OrderIdServer instance() {
        if (server == null) {
            return (server = new OrderIdServer());
        }
        else {
            return server;
        }
    }

    public int getId() {
        return idCounter++;
    }

    public static void retrieve(ObjectInputStream inputStream) {
        try {
            server = (OrderIdServer) inputStream.readObject();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeObject(java.io.ObjectOutputStream outputStream)
            throws IOException {
        try {
            outputStream.defaultWriteObject();
            outputStream.writeObject(server);
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void readObject(java.io.ObjectInputStream inputStream)
            throws IOException, ClassNotFoundException {
        try {
            inputStream.defaultReadObject();
            if (server == null) {
                server = (OrderIdServer) inputStream.readObject();
            }
            else {
                inputStream.readObject();
            }
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
