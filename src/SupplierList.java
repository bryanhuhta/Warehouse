// Author: Bryan Huhta

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class SupplierList implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Supplier> suppliers = new LinkedList<Supplier>();
    private static SupplierList supplierList;

    private SupplierList() {
        // Empty constructor.
    }

    public static SupplierList instance() {
        if (supplierList == null) {
            return (supplierList = new SupplierList());
        }
        else {
            return supplierList;
        }
    }

    public boolean addSupplier(Supplier supplier) {
        boolean canAdd = true;
        Iterator iterator = suppliers.iterator();

        while (iterator.hasNext()) {
            Supplier temp = (Supplier) iterator.next();

            if (temp.equals(supplier)) {
                canAdd = false;
                break;
            }
        }

        if (canAdd) {
            canAdd = suppliers.add(supplier);
        }

        return canAdd;
    }

    public boolean deleteSupplier(Supplier supplier) {
        return suppliers.remove(supplier);
    }

    public Iterator<Supplier> getSuppliers() {
        return suppliers.iterator();
    }

    private void writeObject(java.io.ObjectOutputStream outputStream) {
        try {
            outputStream.defaultWriteObject();
            outputStream.writeObject(supplierList);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readObject(java.io.ObjectInputStream inputStream) {
        try {
            if (supplierList != null) {
                return;
            }
            else {
                inputStream.defaultReadObject();
                if (supplierList == null) {
                    supplierList = (SupplierList) inputStream.readObject();
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
        return suppliers.toString();
    }
}
