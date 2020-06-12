package com.projectsem4.product.exception;

public class CategoriesNotFoundExceptionResponse {
    private String productNotFound;

    public CategoriesNotFoundExceptionResponse(String productNotFound) {
        this.productNotFound = productNotFound;
    }

    public String getProductNotFound() {
        return productNotFound;
    }

    public void setProductNotFound(String productNotFound) {
        this.productNotFound = productNotFound;
    }
}
