// Author: Bryan Huhta

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ClientList implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Client> clients = new LinkedList<Client>();
    private static ClientList clientList;

    private ClientList() {
        // Empty constructor.
    }

    public static ClientList instance() {
        if (clientList == null) {
            return (clientList = new ClientList());
        }
        else {
            return clientList;
        }
    }

    public boolean addClient(Client client) {
        return clients.add(client);
    }

    public Iterator<Client> getClients() {
        return clients.iterator();
    }

    private void writeObject(java.io.ObjectOutputStream outputStream) {
        try {
            outputStream.defaultWriteObject();
            outputStream.writeObject(clientList);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readObject(java.io.ObjectInputStream inputStream) {
        try {
            if (clientList != null) {
                return;
            }
            else {
                inputStream.defaultReadObject();
                if (clientList == null) {
                    clientList = (ClientList) inputStream.readObject();
                }
                else {
                    inputStream.readObject();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return clients.toString();
    }
}
