package com.projectsem4.product.service;

import com.projectsem4.product.entity.Category;
import com.projectsem4.product.entity.Product;
import com.projectsem4.product.entity.Sale;
import com.projectsem4.product.exception.ResourceNotFoundException;
import com.projectsem4.product.repository.ProductRepository;
import com.projectsem4.product.repository.SaleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SaleService {
    private final Logger logger = LoggerFactory.getLogger(SaleService.class);

    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;

    public SaleService(SaleRepository saleRepository, ProductRepository productRepository) {
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
    }

    /**
     * Save a sale
     *
     * @param
     * @return the persisted entity
     */

    public Optional<Sale> save(Long productId, Sale sale){
        return productRepository.findById(productId).map(product -> {
            sale.setProduct(product);
            if(sale.getSalePercentage() < 0){
                throw new ResourceNotFoundException("percentage cant bellow 0");
            }
            if(sale.getDescription() == null){
                sale.setDescription("");
            }
            if(sale.getStatus() == null || sale.getStatus() == ""){
                sale.setStatus("Not set this sale time yet!");
            }
            return saleRepository.save(sale);
        });
    }

    /**
     * Get all the bands.
     *
     * @return the list of entities.
     */
    public List<Sale> findAll() {
        logger.debug("Request to get all Bands");
        return saleRepository.findAll();
    }

    public Sale updateSale(Long productId, Long saleId, Sale saleRequest){
        if(!productRepository.existsById(productId)){
            throw new ResourceNotFoundException("ProductId " + productId + " not found");
        }

        return saleRepository.findById(saleId).map(sale -> {
            sale.setDateSaleStart(saleRequest.getDateSaleStart());
            sale.setDateSaleEnd(saleRequest.getDateSaleEnd());
            sale.setSalePercentage(saleRequest.getSalePercentage());
            sale.setDescription(saleRequest.getDescription());
            sale.setStatus(saleRequest.getStatus());
            return saleRepository.save(sale);
        }).orElseThrow(() ->new ResourceNotFoundException("ProductId " + productId + " not found"));
    }

    /**
     * Get all the sale.
     *
     * @return the list of entities.
     * @param id
     */
    public Optional<Sale> findOne(Long id) {
        logger.debug("Request to get Band : {}", id);
        return saleRepository.findById(id);
    }

    /**
     * Delete the band by id.
     *
     * @param saleId the id of the entity.
     */
    public void delete(Long productId, Long saleId){
        saleRepository.findByIdAndProductId(productId, saleId).map(sale -> {
            saleRepository.delete(sale);
            return true;
        });
    }
}
