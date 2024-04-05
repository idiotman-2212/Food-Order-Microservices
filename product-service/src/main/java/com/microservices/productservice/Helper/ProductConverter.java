package com.microservices.productservice.Helper;

import com.microservices.productservice.Entity.ProductEntity;
import com.microservices.productservice.Payload.Response.ProductResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter  {
    public static ProductResponse toProductResponse(ProductEntity productEntity) {
        if (productEntity == null) {
            return null;
        }
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(productEntity.getId());
        productResponse.setName(productEntity.getName());
        productResponse.setImage(productEntity.getImage());
        productResponse.setPrice(productEntity.getPrice());
        productResponse.setDescription(productEntity.getDescription());
        productResponse.setQuantity(productEntity.getQuantity());
        productResponse.setCreateDate(productEntity.getCreateDate());

        productResponse.setCategory(CategoryConverter.toCategoryResponse(productEntity.getCategory()));

        return productResponse;
    }
    public static ProductEntity toProductEntity(ProductResponse productResponse) {
        if (productResponse == null) {
            return null;
        }
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(productResponse.getId());
        productEntity.setName(productResponse.getName());
        productEntity.setImage(productResponse.getImage());
        productEntity.setPrice(productResponse.getPrice());
        productEntity.setDescription(productResponse.getDescription());
        productEntity.setQuantity(productResponse.getQuantity());
        productEntity.setCreateDate(productResponse.getCreateDate());

        productEntity.setCategory(CategoryConverter.toCategoryEntity(productResponse.getCategory()));

        return productEntity;
    }

}
