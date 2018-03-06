// Author: Bryan Huhta

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class ManufacturerIdServer implements Serializable {
    private int idCounter;
    private static ManufacturerIdServer server;

    private ManufacturerIdServer() {
        idCounter = 1;
    }

    public static ManufacturerIdServer instance() {
        if (server == null) {
            return (server = new ManufacturerIdServer());
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
            server = (ManufacturerIdServer) inputStream.readObject();
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
                server = (ManufacturerIdServer) inputStream.readObject();
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
