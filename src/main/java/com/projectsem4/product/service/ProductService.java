package com.projectsem4.product.service;
import com.projectsem4.product.entity.Category;
import com.projectsem4.product.entity.Product;
import com.projectsem4.product.exception.CategoryNotFoundException;
import com.projectsem4.product.exception.ResourceNotFoundException;
import com.projectsem4.product.repository.CategoryRepository;
import com.projectsem4.product.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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

    public Product addProduct(String categoryIdentifier, Product product){

       try {
           //Exceptions: Product not found

           //Product to be added to a specific category, product != null, category exist
           Category category = categoryRepository.findByCategoryIdentifier(categoryIdentifier);

           //Set random productIdentifier
           product.setProductIdentifier(returnRandomId(product.getName()));

           //Set price and quantity
           if(product.getPrice() < 0){
               product.setPrice(0);
           }
           if(product.getCurrentQuantity() < 0){
               product.setCurrentQuantity(0);
           }

           //set the category to product
           product.setCategory(category);

           //we want our project sequence to be like this: IDCATE-1 IDCATE-2
           Integer categorySequence = category.getPTSequence();

           //Update the category sequence
           categorySequence++;

           category.setPTSequence(categorySequence);

           //Add sequence to product
           product.setProductSequence(categoryIdentifier + "-" + categorySequence);
           product.setCategoryIdentifier(categoryIdentifier);

           //Initial priority when priority null
           if(product.getPriority() == null){
               product.setPriority(3);
           }

           //Initial status when status is null
           if(product.getStatus() == "" || product.getStatus() == null || product.getCurrentQuantity() == 0){
               product.setStatus("Out of stock");
           }
           else {
               product.setStatus("Stocking");
           }

           return productRepository.save(product);
       }catch (Exception e){
            throw new CategoryNotFoundException("Category not Found");
       }
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

    public Iterable<Product> findCategoryById(String id){

        Category category = categoryRepository.findByCategoryIdentifier(id);

        if(category==null){
            throw new CategoryNotFoundException("Project with ID: '"+id+"' does not exist");
        }

        return productRepository.findByProductIdentifierOrderByPriority(id);

//        List<Product> products = getAllProduct();
//        List<Product> listProductByCategory = new ArrayList<>();
//        for(Product product: products){
//            if(product.getCategoryIdentifier().equals(id)){
//                listProductByCategory.add(product);
//            }
//        }
//        if(listProductByCategory == null){
//            throw new CategoryNotFoundException("Category with Id" + id + "does not exits");
//        }
//        return listProductByCategory;


    }

    public Product findProductByProductSequence(String categoryId, String sequence){

        //make sure to searching in the right category
        Category category = categoryRepository.findByCategoryIdentifier(categoryId);
        if(category == null){
            throw new CategoryNotFoundException("Category with Id " + categoryId + " does not exits");
        }

        //make sure that our task exists
        Product product = productRepository.findByProductSequence(sequence);
        if(product == null){
            throw new CategoryNotFoundException("Product " + sequence + " not found");
        }

        //make sure that the product is in the path corresponds to the right category
        if(!product.getCategoryIdentifier().equals(categoryId)){
            throw new CategoryNotFoundException("Product " + sequence + " does not exist in category " + categoryId);
        }

        return product;
    }

    public List<Product> getAllProduct(){
        return productRepository.findAll();
    }

    public Product findProductByIdentifier(String productId){
        Product product = productRepository.findByProductIdentifier(productId.toUpperCase());
        if(product == null){
            throw new ResourceNotFoundException("Product ID '" + product.getProductIdentifier().toUpperCase()+ "' does not exits");
        }

        return product;
    }

    public Product updateByCategorySequence(Product updateProduct, String categoryId, String productId){
        Product product = findProductByProductSequence(categoryId, productId);

        product = updateProduct;

        return productRepository.save(product);
    }

    public void delete(String id) {
        log.debug("Request to delete Song : {}", id);
        Product product = productRepository.findByProductIdentifier(id);

        if(product == null){
            throw new ResourceNotFoundException("Product ID '" + product.getProductIdentifier().toUpperCase()+ "' does not exits");
        }

        productRepository.delete(product);
    }

    public void deleteProductByProductSequence(String categoryId, String productId){
        Product product = findProductByProductSequence(categoryId, productId);

//        Category category = product.getCategory();
//        List<Product> products = (List<Product>) category.getProducts();
//        products.remove(product);
//        categoryRepository.save(category);

        productRepository.delete(product);
    }
}

  //  public Product saveOrUpDate(Product product){
//            product.setProductIdentifier(returnRandomId(product.getName()));
//            if(product.getPrice() < 0){
//                product.setPrice(0);
//            }
//            if(product.getCurrentQuantity() < 0){
//                product.setCurrentQuantity(0);
//            }
//
//            return productRepository.save(product);
////            if(product.getDateSaleStart().compareTo(product.getDateSaleEnd()) >= 0){
////                throw new ResourceNotFoundException("End date cant occurs Start date or equal");
////            }
//    }


//    public List<Product> findAllByDateBetween( String codeIDProduct,Date dateTimeStart, Date dateTimeEnd,){
//
//    }

//    public Product updateProduct(Long categoryId, Long productId, Product productRequest){
//        if(!categoryRepository.existsById(categoryId)){
//            throw new ResourceNotFoundException("CategoryId " + categoryId + " not found");
//        }
//
//        return productRepository.findById(productId).map(product -> {
//            product.setCodeId(productRequest.getCodeId());
//            product.setName(productRequest.getName());
//            product.setImage(productRequest.getImage());
//            product.setCurrentQuantity(productRequest.getCurrentQuantity());
//            return productRepository.save(product);
//        }).orElseThrow(() -> new ResourceNotFoundException("productId " + productId + "not found"));
//    }