package com.projectsem4.product.webapi;

import com.projectsem4.product.entity.Customer;
import com.projectsem4.product.service.CustomerService;
import com.projectsem4.product.service.MapValidationErrorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customer/v1")
@CrossOrigin
public class CustomerResource {

    private final CustomerService customerService;
    private final MapValidationErrorService mapValidationErrorService;

    public CustomerResource(CustomerService customerService, MapValidationErrorService mapValidationErrorService) {
        this.customerService = customerService;
        this.mapValidationErrorService = mapValidationErrorService;
    }

    @PostMapping("")
    public ResponseEntity<?> createNewCustomer(@Valid @RequestBody Customer customer, BindingResult result){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if (errorMap != null) return errorMap;

        Customer customer1 = customerService.saveOrUpdateCustomer(customer);
        return new ResponseEntity<Customer>(customer1, HttpStatus.CREATED);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<?> getCustomerById(@PathVariable String customerId) {

        Customer customer = customerService.findCustomerByIdentifier(customerId);

        return new ResponseEntity<Customer>(customer, HttpStatus.OK);
    }

    @GetMapping("/all")
    public Iterable<Customer> getAllCustomer(){
        return customerService.findAllCustomer();
    }

    @DeleteMapping("/{customerId}")
    private ResponseEntity<?> deleteCustomer(@PathVariable String customerID){
        customerService.deleteProjectByIdentifier(customerID);

        return new ResponseEntity<String>("Customer with ID: '"+customerID+"' was deleted", HttpStatus.OK);
    }

}
