package com.microservices.productservice.Service;

import com.microservices.productservice.Entity.CategoryEntity;
import com.microservices.productservice.Helper.CategoryConverter;
import com.microservices.productservice.Payload.Response.CategoryResponse;
import com.microservices.productservice.Repository.CategoryRepository;
import com.microservices.productservice.Service.Imp.CategoryServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService implements CategoryServiceImp {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryConverter categoryConverter;
    @Override
    public boolean insertCategory(String name) {
        if (categoryRepository.existsByName(name)) {
            throw new IllegalStateException("Category with this name already exists");
        }
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(name);
        categoryEntity.setCreateDate(new Date());
        categoryRepository.save(categoryEntity);
        return true;
    }

    @Override
    public List<CategoryResponse> getAllCategory() {
        List<CategoryEntity> categories = categoryRepository.findAll();
        return categories.stream()
                .map(CategoryConverter::toCategoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse getCategoryById(int id) {
        Optional<CategoryEntity> categoryEntityOptional = categoryRepository.findById(id);
        if(categoryEntityOptional.isPresent()){
            return CategoryConverter.toCategoryResponse(categoryEntityOptional.get());
        }else {
            return null;
        }
    }

    @Override
    public boolean deleteCategoryById(int id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateCategoryById(int id, String name) {
        Optional<CategoryEntity> optionalCategory = categoryRepository.findById(id);

        if (optionalCategory.isPresent()) {
            CategoryEntity categoryEntity = optionalCategory.get();
            categoryEntity.setName(name);
            categoryEntity.setCreateDate(new Date());
            categoryRepository.save(categoryEntity);
            return true;
        }
        return false;
    }

}
