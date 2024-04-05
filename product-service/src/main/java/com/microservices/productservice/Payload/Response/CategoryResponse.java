package com.microservices.productservice.Payload.Response;

import java.util.Date;
import java.util.List;

public class CategoryResponse {
    private int id;
    private String name;
    private Date createDate;
    private List<ProductResponse> products;

    public CategoryResponse() {
    }
    public CategoryResponse(int id, String name, Date createDate, List<ProductResponse> products) {
        this.id = id;
        this.name = name;
        this.createDate = createDate;
        this.products = products;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public List<ProductResponse> getProducts() {
        return products;
    }

    public void setProducts(List<ProductResponse> products) {
        this.products = products;
    }
}
