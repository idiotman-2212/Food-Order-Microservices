package com.microservices.productservice.Service.Imp;

import com.microservices.productservice.Payload.Response.CategoryResponse;

import java.util.List;

public interface CategoryServiceImp {
    boolean insertCategory(String name);
    List<CategoryResponse> getAllCategory();
    CategoryResponse getCategoryById(int id);
    boolean deleteCategoryById(int id);
    boolean updateCategoryById(int id, String name);

}
