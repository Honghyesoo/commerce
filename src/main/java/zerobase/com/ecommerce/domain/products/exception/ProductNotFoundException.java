package zerobase.com.ecommerce.domain.products.exception;

import zerobase.com.ecommerce.domain.exception.CommerceException;

public class ProductNotFoundException extends CommerceException {
    public ProductNotFoundException(String message){
        super(message);
    }
}
