package com.microservices.productservice.Controller;

import com.microservices.productservice.Payload.Response.BaseResponse;
import com.microservices.productservice.Payload.Response.ProductResponse;
import com.microservices.productservice.Repository.ProductRepository;
import com.microservices.productservice.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("")
    public ResponseEntity<BaseResponse<List<ProductResponse>>> getAllProducts() {
        List<ProductResponse> productResponseList = productService.getAllProduct();
        BaseResponse<List<ProductResponse>> baseResponse = new BaseResponse<>();
        baseResponse.setMessage("Get all product");
        baseResponse.setData(productResponseList);
        baseResponse.setStatusCode(200);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<ProductResponse>> getProductById(@PathVariable Integer id) {
        ProductResponse product = productService.getProductById(id);
        BaseResponse<ProductResponse> baseResponse = new BaseResponse<>();

        if (product != null) {
            baseResponse.setMessage("Get product by id");
            baseResponse.setData(product);
            baseResponse.setStatusCode(200);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        } else {
            baseResponse.setMessage("Product not found");
            baseResponse.setData(null);
            baseResponse.setStatusCode(404);
            return new ResponseEntity<>(baseResponse, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> insertProduct(@RequestParam String name,
                                           @RequestParam MultipartFile file, @RequestParam double price,
                                           @RequestParam int quantity, @RequestParam int idCategory, @RequestParam String description) throws IOException, IOException {

        if(productRepository.existsByName(name)){
            BaseResponse errorResponse = new BaseResponse();
            errorResponse.setStatusCode(400);
            errorResponse.setMessage("Product with name '" + name+ " ' already exist");

            return new ResponseEntity<>( errorResponse, HttpStatus.BAD_REQUEST);
        }

        try {
            boolean isSuccess = productService.insertProduct(name, file, price, quantity, idCategory, description);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMessage("Insert Product Successfully");
        baseResponse.setStatusCode(200);
        baseResponse.setData(isSuccess);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            BaseResponse errorResponse = new BaseResponse();
            errorResponse.setStatusCode(500);
            errorResponse.setMessage("Failed to process the file.");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{idProduct}")
    public ResponseEntity<?> updateProductById(@PathVariable int idProduct, @RequestParam String name,
                                               @RequestParam MultipartFile file, @RequestParam String description, @RequestParam double price,
                                               @RequestParam int quantity, @RequestParam int idCategory
    ) throws IOException {
        boolean isUpdated = productService.updateProductById(idProduct, name, file, description, price, quantity, idCategory);
        if (isUpdated) {
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setMessage("Product updated successfully");
            baseResponse.setStatusCode(200);
            baseResponse.setData(isUpdated);

            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        } else {
            BaseResponse errorResponse = new BaseResponse();
            errorResponse.setMessage("Product with id '" + idProduct + " ' not found");
            errorResponse.setStatusCode(404);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{idProduct}")
    public ResponseEntity<?> deleteProductById(@PathVariable int idProduct){

        boolean isDeleted = productService.deleteProductById(idProduct);
        if (isDeleted){
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setMessage("Product deleted successfully");
            baseResponse.setStatusCode(200);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Product not found or unable to delete", HttpStatus.OK);
        }
    }
    @GetMapping("/search")
    public BaseResponse searchProduct(@RequestParam String query) {
        BaseResponse response = new BaseResponse();

        try {
            List<ProductResponse> productList = productService.searchProducts(query);

            if (productList.isEmpty()) {
                response.setMessage("No products found for the given query.");
            } else {
                response.setData(productList);
                response.setMessage("Products found successfully.");
            }

        } catch (Exception e) {
            response.setMessage("An error occurred while searching for products.");
        }

        return response;
    }
}
