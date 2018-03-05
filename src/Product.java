// Author: Ben Jeannot and Bryan Huhta

import java.io.Serializable;

public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String PRODUCT_STRING = "P";

    private String productId;

    Product(String productId){
        this.productId = PRODUCT_STRING + (ProductIdServer.instance()).getId();
    }

    public String getProductId(){
        return productId;
    }

    @Override
    public String toString(){
        return ("[ id: " + productId + " ]");
    }
}
