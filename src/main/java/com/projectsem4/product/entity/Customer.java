package com.projectsem4.product.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Email is required")
    @Email
    @Size(max = 100)
    @Column(unique = true)
    private String email;

    @Column(updatable = false, unique = true)
    private String customerIdentifier;

    @NotNull
    @Size(max = 20)
    @NotBlank(message = "Customer name is required")
    private String nameCustomer;

    @Size(max = 20)
    @Column(unique = true)
    @NotBlank(message = "Customer phone number is required")
    private String phone;

    @Size(max = 100)
    @NotBlank(message = "Customer address is required")
    private String address;

    @Size(max = 30)
    @NotBlank(message = "Customer city is required")
    private String city;

    @JsonFormat(pattern = "yyyy-mm-dd")
    @Column(unique = true, updatable = false)
    private Date create_At;

    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date update_At;

    @OneToMany(cascade = CascadeType.REFRESH,
            fetch = FetchType.EAGER,
            mappedBy = "customer",
            orphanRemoval = true)
    private List<ListOrder> orders = new ArrayList<>();

    @PrePersist
    protected void onCreate(){
        this.create_At = new Date();
    }

    @PreUpdate
    protected void opUpdate(){
        this.update_At = new Date();
    }

    public Customer() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getCreate_At() {
        return create_At;
    }

    public void setCreate_At(Date create_At) {
        this.create_At = create_At;
    }

    public Date getUpdate_At() {
        return update_At;
    }

    public void setUpdate_At(Date update_At) {
        this.update_At = update_At;
    }

    public List<ListOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<ListOrder> orders) {
        this.orders = orders;
    }


    public String getCustomerIdentifier() {
        return customerIdentifier;
    }

    public void setCustomerIdentifier(String customerIdentifier) {
        this.customerIdentifier = customerIdentifier;
    }
}
