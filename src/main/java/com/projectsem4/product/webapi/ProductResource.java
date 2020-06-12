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
@CrossOrigin
public class ProductResource {
    private final ProductService productService;

    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public List<Product> getAllProducts(){
        return productService.getAllProduct();
    }

    @GetMapping("/{productId}")
    public Product getProductById(@PathVariable String productId){
        return productService.findProductByIdentifier(productId);
    }

    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable (value = "productId") String productId){
       productService.delete(productId);
    }
}


//@PostMapping("/")
//    public Product product(@Valid @RequestBody Product product){
//        return productService.saveOrUpDate(product);
//    }

//    @PutMapping("/category/{categoryId}/products/{productId}")
//    public Product updateProduct(@PathVariable (value = "categoryId") Long categoryId,
//                                 @PathVariable (value = "productId") Long productId,
//                                 @Valid @RequestBody Product productRequest){
//        return productService.updateProduct(categoryId, productId, productRequest);
//    }