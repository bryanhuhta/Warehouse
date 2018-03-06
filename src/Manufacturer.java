// Author: Bryan Huhta

import java.io.Serializable;

public class Manufacturer implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String MAN_STRING = "M";

    private String id;
    private String name;

    public Manufacturer(String name) {
        this.id = MAN_STRING + (ManufacturerIdServer.instance()).getId();
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "[ id: " + id + ", name: " + name + " ]";
    }
}
