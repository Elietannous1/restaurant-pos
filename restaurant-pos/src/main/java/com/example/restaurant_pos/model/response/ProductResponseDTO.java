package com.example.restaurant_pos.model.response;

public class ProductResponseDTO {
    private Long id;
    private String productName;
    private Double price;
    private String description;
    private Boolean available;
    private String categoryName;

    public ProductResponseDTO() {}

    public ProductResponseDTO(Long id, String productName, Double price, String description, Boolean available, String categoryName) {
        setAvailable(available);
        setCategoryName(categoryName);
        setDescription(description);
        setPrice(price);
        setProductName(productName);
        setId(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
