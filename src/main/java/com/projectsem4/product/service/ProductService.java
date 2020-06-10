package com.projectsem4.product.service;
import com.projectsem4.product.entity.Product;
import com.projectsem4.product.exception.ResourceNotFoundException;
import com.projectsem4.product.repository.CategoryRepository;
import com.projectsem4.product.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final Logger log = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Product> getAllProduct(Long categoryId){
        return productRepository.findByCategoryId(categoryId);
    }

    public Optional<Product> save(Long categoryId, Product product){

        return categoryRepository.findById(categoryId).map(category -> {
            product.setCategory(category);
            product.setCodeId(returnRandomId(product.getName()));
            if(product.getPrice() < 0){
                product.setPrice(0);
            }
            if(product.getCurrentQuantity() < 0){
                product.setCurrentQuantity(0);
            }
//            if(product.getDateSaleStart().compareTo(product.getDateSaleEnd()) >= 0){
//                throw new ResourceNotFoundException("End date cant occurs Start date or equal");
//            }
            return productRepository.save(product);
        });
    }

//    public List<Product> findAllByDateBetween( String codeIDProduct,Date dateTimeStart, Date dateTimeEnd,){
//
//    }

    public Product updateProduct(Long categoryId, Long productId, Product productRequest){
        if(!categoryRepository.existsById(categoryId)){
            throw new ResourceNotFoundException("CategoryId " + categoryId + " not found");
        }

        return productRepository.findById(productId).map(product -> {
            product.setCodeId(productRequest.getCodeId());
            product.setName(productRequest.getName());
            product.setImage(productRequest.getImage());
            product.setCurrentQuantity(productRequest.getCurrentQuantity());
            return productRepository.save(product);
        }).orElseThrow(() -> new ResourceNotFoundException("productId " + productId + "not found"));
    }

    public void delete(Long categoryId, Long productId){
        productRepository.findByIdAndCategoryId(categoryId, productId).map(product -> {
            productRepository.delete(product);
            return true;
        });
    }

    public String returnRandomId(String nameProduct){
        String defaultFirstTwoCharacter = nameProduct.substring(0,2);
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                     + "0123456789"
                                     + "abcdefghijklmnopqrstuvxyz";

        StringBuilder s = new StringBuilder();
        int x;
        for( x = 0; x < 5; x++) {
            int index = (int) (AlphaNumericString.length() * Math.random());
            s.append(AlphaNumericString.charAt(index));
        }
        return defaultFirstTwoCharacter + s.toString();
    }
}
