package com.microservice.orderservice.Payload.Response.Product;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CategoryResponse implements Serializable {
    private int id;
    private String name;
    private Date createDate;
    private List<ProductResponse> products;

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
