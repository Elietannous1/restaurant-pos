package com.example.restaurant_pos.model;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class ProductSales {


    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @JsonIdentityReference(alwaysAsId = true)
    private Product product;

    private LocalDate saleDate;

    private int quantitySold;


    public ProductSales(Product product, LocalDate saleDate, int quantitySold) {
        setProduct(product);
        setSaleDate(saleDate);
        setQuantitySold(quantitySold);
    }

    public ProductSales() {

    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
    }

    public int getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(int quantitySold) {
        this.quantitySold = quantitySold;
    }
}


