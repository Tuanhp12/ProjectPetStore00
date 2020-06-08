package com.projectsem4.product.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dateSaleStart;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dateSaleEnd;

    @NotNull
    private double salePercentage;

    @Size(max = 100)
    private String description;

    @NotNull
    private String status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    @JsonIgnore
    private Product product;

    public Sale() {
    }

    public Sale(Date dateSaleStart, Date dateSaleEnd){
        this.dateSaleStart = dateSaleStart;
        this.dateSaleEnd = dateSaleEnd;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDateSaleStart() {
        return dateSaleStart;
    }

    public void setDateSaleStart(Date dateSaleStart) {
        this.dateSaleStart = dateSaleStart;
    }

    public Date getDateSaleEnd() {
        return dateSaleEnd;
    }

    public void setDateSaleEnd(Date dateSaleEnd) {
        this.dateSaleEnd = dateSaleEnd;
    }

    public double getSalePercentage() {
        return salePercentage;
    }

    public void setSalePercentage(double salePercentage) {
        this.salePercentage = salePercentage;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
