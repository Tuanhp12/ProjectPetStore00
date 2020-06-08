package com.projectsem4.product.repository;

import com.projectsem4.product.entity.Product;
import com.projectsem4.product.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    Optional<Sale> findByIdAndProductId(Long id, Long productId);

//    @Query("select p.codeId,p.name,s.dateSaleStart,s.dateSaleEnd,s.salePercentage,s.status " +
//            "From Product p inner join p.sales s " +
//            "where :dateTimeStart >= s.dateSaleStart and  :dateTimeEnd <= s.dateSaleEnd and s.product.codeId = :codeIDProduct")
//    List<Sale> findAllByDateBetween1(Date dateTimeStart, Date dateTimeEnd, String codeIDProduct);

}
