package com.projectsem4.product.repository;

import com.projectsem4.product.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.Entity;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("select distinct category from Category category left join fetch category.products")
    List<Category> findAllWithEagerRelationships();

    @Query("select category from Category category left join fetch category.products where category.id = :id")
    Optional<Category> findOneWitEagerRelationships(@Param("id") Long id);
}
