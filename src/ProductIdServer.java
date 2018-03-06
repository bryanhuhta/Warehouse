// Author: Bryan Huhta

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class ProductIdServer implements Serializable {
    private int idCounter;
    private static ProductIdServer server;

    private ProductIdServer() {
        idCounter = 1;
    }

    public static ProductIdServer instance() {
        if (server == null) {
            return (server = new ProductIdServer());
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
            server = (ProductIdServer) inputStream.readObject();
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
                server = (ProductIdServer) inputStream.readObject();
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
