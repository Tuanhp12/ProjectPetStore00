package com.projectsem4.product.webapi;

import com.projectsem4.product.entity.Category;
import com.projectsem4.product.entity.Product;
import com.projectsem4.product.exception.ResourceNotFoundException;
import com.projectsem4.product.service.CategoryService;
import com.projectsem4.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/category/v1")
@CrossOrigin
public class CategoryResource {

    private final CategoryService categoryService;
    private final ProductService productService;

    public CategoryResource(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
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

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long categoryId){
        categoryService.delete(categoryId);
        return ResponseEntity.ok().build();
    }

//    @PutMapping("/{categoryId}")
//    public Category updateCategory(@PathVariable Long categoryId, @Valid @RequestBody Category categoryRequest){
//        return categoryService.findOne(categoryId).map(category -> {
//            category.setType(categoryRequest.getType());
//            return categoryService.save(category);
//        }).orElseThrow(() -> new ResourceNotFoundException("CategoryId " + categoryId + "not found"));
//    }
}
