package zerobase.com.ecommerce.exception.product;

import zerobase.com.ecommerce.exception.global.CommerceException;

public class ProductNotFoundException extends CommerceException {
    public ProductNotFoundException(String message){
        super(message);
    }
}
