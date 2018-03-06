// Author: Grace Hughes and Bryan Huhta

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ManufacturerList implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Manufacturer> manufacturers = new LinkedList<Manufacturer>();
    private static ManufacturerList manufacturerList;

    //constructor
    private ManufacturerList() {
        // Empty constructor.
    }

    public static ManufacturerList instance() {
        if (manufacturerList == null) {
            return(manufacturerList = new ManufacturerList());
        }
        else {
            return manufacturerList;
        }
    }

    public boolean addManufacturer(Manufacturer manufacturer) {
        return manufacturers.add(manufacturer);
    }

    public boolean deleteManufacturer(Manufacturer manufacturer) {
        return manufacturers.remove(manufacturer);
    }

    public Iterator<Manufacturer> getManufacturers() {
        return manufacturers.iterator();
    }

    private void writeObject(java.io.ObjectOutputStream outputStream) {
        try {
            outputStream.defaultWriteObject();
            outputStream.writeObject(manufacturerList);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readObject(java.io.ObjectInputStream inputStream) {
        try {
            if (manufacturerList != null) {
                return;
            }
            else {
                inputStream.defaultReadObject();
                if(manufacturerList == null) {
                    manufacturerList = (ManufacturerList) inputStream.readObject();
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
        return manufacturers.toString();
    }
}
