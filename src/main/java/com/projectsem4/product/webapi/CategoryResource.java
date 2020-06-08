package com.projectsem4.product.webapi;

import com.projectsem4.product.entity.Category;
import com.projectsem4.product.exception.ResourceNotFoundException;
import com.projectsem4.product.service.CategoryService;
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

    public CategoryResource(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public List<Category> getAllCategory(){
        return categoryService.findAll();
    }

    @PostMapping("/categories")
    public Category createCategory(@Valid @RequestBody Category category){
        return categoryService.save(category);
    }

    @PutMapping("/categories/{categoryId}")
    public Category updateCategory(@PathVariable Long categoryId, @Valid @RequestBody Category categoryRequest){
        return categoryService.findOne(categoryId).map(category -> {
            category.setType(categoryRequest.getType());
            return categoryService.save(category);
        }).orElseThrow(() -> new ResourceNotFoundException("CategoryId " + categoryId + "not found"));
    }

    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long categoryId){
        categoryService.delete(categoryId);
        return ResponseEntity.ok().build();
    }
}
