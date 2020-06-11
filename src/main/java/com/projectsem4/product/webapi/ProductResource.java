package com.projectsem4.product.webapi;

import com.projectsem4.product.entity.Product;
import com.projectsem4.product.service.CategoryService;
import com.projectsem4.product.service.ProductService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/product/v1")
public class ProductResource {
    private final ProductService productService;

    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public List<Product> getAllProducts(){
        return productService.getAllProduct();
    }

    @PostMapping("/")
    public Product product(@Valid @RequestBody Product product){
        return productService.save(product);
    }

    @PutMapping("/category/{categoryId}/products/{productId}")
    public Product updateProduct(@PathVariable (value = "categoryId") Long categoryId,
                                 @PathVariable (value = "productId") Long productId,
                                 @Valid @RequestBody Product productRequest){
        return productService.updateProduct(categoryId, productId, productRequest);
    }

    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable (value = "productId") Long productId){
        productService.delete(productId);
    }
}
