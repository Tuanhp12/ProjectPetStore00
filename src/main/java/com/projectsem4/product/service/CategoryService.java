package com.projectsem4.product.service;

import com.projectsem4.product.entity.Category;
import com.projectsem4.product.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryService {
    private final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Save a band
     *
     * @param
     * @return the persisted entity
     */
    public Category save(Category category){
        logger.debug("Request to save category : {}", category);
        return categoryRepository.save(category);
    }

    /**
     * Get all the Category
     *
     * @return the list of entities
     */
    public List<Category> findAll(){
        logger.debug("Request to get all category");
        return categoryRepository.findAllWithEagerRelationships();
    }

    /**
     * Get one band by id
     * @param id the id of the entity
     * @return the entity
     */
    public Optional<Category> findOne(Long id){
        logger.debug("Request to get category : {}", id);
        return categoryRepository.findOneWitEagerRelationships(id);
    }

    /**
     * Delete one Category
     *
     * @param id
     */
    public void delete(Long id){
        logger.debug("Request to delete Category : {}", id);
        categoryRepository.deleteById(id);
    }
}
