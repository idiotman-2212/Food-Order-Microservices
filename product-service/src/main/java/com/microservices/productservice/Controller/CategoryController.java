package com.microservices.productservice.Controller;

import com.microservices.productservice.Payload.Response.BaseResponse;
import com.microservices.productservice.Payload.Response.CategoryResponse;
import com.microservices.productservice.Helper.CategoryConverter;
import com.microservices.productservice.Repository.CategoryRepository;
import com.microservices.productservice.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryConverter categoryConverter;
    @GetMapping("")
    public ResponseEntity<?> getAllCategory(){
        List<CategoryResponse> responseList = categoryService.getAllCategory();
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMessage("Get all category");
        baseResponse.setData(responseList);
        baseResponse.setStatusCode(200);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("/{idCategory}")
    public ResponseEntity<?> getCategoryById(@PathVariable int idCategory){
        CategoryResponse responseList = categoryService.getCategoryById(idCategory);
        if (categoryRepository.existsById(idCategory)) {
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setMessage("Get category by id: " + idCategory);
            baseResponse.setData(responseList);
            baseResponse.setStatusCode(200);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }else{
            BaseResponse errorResponse = new BaseResponse();
            errorResponse.setStatusCode(404);
            errorResponse.setMessage("Category with id '" + idCategory + "' not found.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("")
    public ResponseEntity<?> addCategory(@RequestParam String name) throws IOException {
        if (categoryRepository.existsByName(name)) {
            BaseResponse errorResponse = new BaseResponse();
            errorResponse.setStatusCode(400);
            errorResponse.setMessage("Category with name '" + name + "' already exists.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        boolean isSuccess = categoryService.insertCategory(name);
        BaseResponse successResponse = new BaseResponse();
        successResponse.setStatusCode(200);
        successResponse.setData(isSuccess);
        successResponse.setMessage("Insert Category Successfully");
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> updateCategory(@PathVariable int id, @RequestParam String name) {
        boolean isUpdated = categoryService.updateCategoryById(id, name);

        BaseResponse baseResponse = new BaseResponse();
        if (isUpdated) {
            baseResponse.setMessage("Update Category Successfully");
            baseResponse.setStatusCode(200);
            baseResponse.setData(categoryService.getCategoryById(id));
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        } else {
            baseResponse.setStatusCode(404);
            baseResponse.setMessage("Category with id " + id + " not found");
            return new ResponseEntity<>(baseResponse, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable int id){
        boolean isDelete = categoryService.deleteCategoryById(id);
        if(isDelete){
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setStatusCode(200);
            baseResponse.setMessage("Delete category successfully.");
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Category not found or unalbe to delete.", HttpStatus.OK);
        }
    }
}
