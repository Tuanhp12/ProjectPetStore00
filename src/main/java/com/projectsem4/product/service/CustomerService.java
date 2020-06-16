package com.projectsem4.product.service;

import com.projectsem4.product.entity.Customer;
import com.projectsem4.product.exception.CustomerIdException;
import com.projectsem4.product.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final ProductService productService;

    public CustomerService(CustomerRepository customerRepository, ProductService productService) {
        this.customerRepository = customerRepository;
        this.productService = productService;
    }

    public Customer saveOrUpdateCustomer(Customer customer){

        customer.setCustomerIdentifier(productService.returnRandomId(customer.getNameCustomer()));
        return customerRepository.save(customer);
    }

    public Customer findCustomerByIdentifier(String customerId){
        Customer customer = customerRepository.findByCustomerIdentifier(customerId.toUpperCase());

        if(customer == null){
            throw new CustomerIdException("Customer ID '"+customerId+"' does not exist");
        }
        return customer;
    }

    public Iterable<Customer> findAllCustomer(){
        return customerRepository.findAll();
    }

    public void deleteProjectByIdentifier(String customerId){
        Customer customer = customerRepository.findByCustomerIdentifier(customerId.toUpperCase());
        if(customer == null){
            throw new CustomerIdException("Customer ID '"+customerId+"' does not exist");
        }

        customerRepository.delete(customer);
    }
}
