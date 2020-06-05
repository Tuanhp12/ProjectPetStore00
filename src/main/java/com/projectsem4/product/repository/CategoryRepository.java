package com.projectsem4.product.repository;

import com.projectsem4.product.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.Entity;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
