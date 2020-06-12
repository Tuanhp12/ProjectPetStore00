package com.projectsem4.product.repository;

import com.projectsem4.product.entity.Product;
import com.projectsem4.product.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByProductIdentifier(String productId);

    List<Product> findByProductIdentifierOrderByPriority(String id);
//    List<Product> findByCategoryId(Long categoryId);
//    Optional<Product> findByIdAndCategoryId(Long id, Long categoryId);
//
//    @Query("select p from Product p inner join Sale s where :datetime >= s.dateSaleStart and :datetime <= s.dateSaleEnd")
//    List<Sale> findAllWithDatetimeAfter(@Param("datetime") Date datetime);

//    @Query("SELECT new com.projectsem4.product.entity.SearchProductByAdmin(p.codeId,p.name,s.dateSaleStart,s.dateSaleEnd,s.salePercentage,s.status)" +
//            "FROM Product p inner join p.sales s " +
//            "where :dateTimeStart >= s.dateSaleStart and  :dateTimeEnd <= s.dateSaleEnd and s.product.codeId = :codeIDProduct " +
//            "group by p.name")
//    List<SearchProductByAdmin> findAllByDateBetween(Date dateTimeStart, Date dateTimeEnd, String codeIDProduct);
}
