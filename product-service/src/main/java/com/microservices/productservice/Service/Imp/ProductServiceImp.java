package com.microservices.productservice.Service.Imp;

import com.microservices.productservice.Payload.Response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductServiceImp {
    boolean insertProduct(String name, MultipartFile file, double price, int quantity,int idCategory, String description) throws IOException;
    List<ProductResponse> getAllProduct();

    Page<ProductResponse> getAllProductsPage(Integer pageNo);

    ProductResponse getProductById(int id);

    boolean deleteProductById(int idProduct);

    boolean updateProductById(int idProduct, String name,
                              MultipartFile file, String description, double price,
                              int quantity,int idCategory) throws IOException;

    List<ProductResponse> getProductByName(String productName);

    List<ProductResponse> searchProducts(String keyword);
}
