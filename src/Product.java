// Author: Ben Jeannot and Bryan Huhta

import java.io.Serializable;

public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String PRODUCT_STRING = "P";

    private String productId;
    private String name;

    Product(String name){
        this.productId = PRODUCT_STRING + (ProductIdServer.instance()).getId();
        this.name = name;
    }

    public String getProductId(){
        return productId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString(){
        return ("[ id: " + productId + ", name: " + name + " ]");
    }
}
