package com.microservice.orderservice.Payload.Response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class CategoryResponse implements Serializable {
    private int id;
    private String name;
    private Date createDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<CategoryResponse> subCategoryResponses;

    @JsonProperty("parentCategory")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CategoryResponse parentCategoryResponse;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<ProductResponse> productResponses;

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

    public Set<CategoryResponse> getSubCategoryResponses() {
        return subCategoryResponses;
    }

    public void setSubCategoryResponses(Set<CategoryResponse> subCategoryResponses) {
        this.subCategoryResponses = subCategoryResponses;
    }

    public CategoryResponse getParentCategoryResponse() {
        return parentCategoryResponse;
    }

    public void setParentCategoryResponse(CategoryResponse parentCategoryResponse) {
        this.parentCategoryResponse = parentCategoryResponse;
    }

    public Set<ProductResponse> getProductResponses() {
        return productResponses;
    }

    public void setProductResponses(Set<ProductResponse> productResponses) {
        this.productResponses = productResponses;
    }
}
