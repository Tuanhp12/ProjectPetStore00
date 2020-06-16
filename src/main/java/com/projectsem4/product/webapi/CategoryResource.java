package com.projectsem4.product.webapi;

import com.projectsem4.product.entity.Category;
import com.projectsem4.product.entity.Product;
import com.projectsem4.product.exception.ResourceNotFoundException;
import com.projectsem4.product.service.CategoryService;
import com.projectsem4.product.service.MapValidationErrorService;
import com.projectsem4.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/category/v1")
@CrossOrigin
public class CategoryResource {

    private final CategoryService categoryService;
    private final ProductService productService;
    private final MapValidationErrorService mapValidationErrorService;

    public CategoryResource(CategoryService categoryService, ProductService productService, MapValidationErrorService mapValidationErrorService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.mapValidationErrorService = mapValidationErrorService;
    }

    @PostMapping("/")
    public Category createCategory(@Valid @RequestBody Category category){
        return categoryService.saveOrUpDate(category);
    }

    @PostMapping("/{categoryId}")
    public Product addProductToCategory(@Valid @RequestBody Product product, @PathVariable String categoryId){
        Product product1 = productService.addProduct(categoryId, product);
        return product1;
    }

    @GetMapping("/all")
    public List<Category> getAllCategory(){
        return categoryService.findAll();
    }

    @GetMapping("/{categoryId}")
    public Iterable<Product> getProduct(@PathVariable String categoryId){
        return productService.findCategoryById(categoryId);
    }

    @GetMapping("/{categoryId}/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable String categoryId, @PathVariable String productId){
        Product product = productService.findProductByProductSequence(categoryId,productId);

        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }

    @PutMapping("/{categoryId}/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable String categoryId, @Valid @RequestBody Product productRequest,
                                           @PathVariable String productId, BindingResult result){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null) return errorMap;

        Product updateProduct = productService.updateByCategorySequence(productRequest,categoryId,productId);
        return new ResponseEntity<Product>(updateProduct, HttpStatus.OK);

    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long categoryId){
        categoryService.delete(categoryId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{categoryId}/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable String categoryId, @PathVariable String productId){
        productService.deleteProductByProductSequence(categoryId, productId);
        return new ResponseEntity<String>("Product " + productId + " was deleted successfully", HttpStatus.OK);
    }



}
