package com.microservices.productservice.Service;

import com.microservices.productservice.Entity.CategoryEntity;
import com.microservices.productservice.Entity.ProductEntity;
import com.microservices.productservice.Payload.Response.CategoryResponse;
import com.microservices.productservice.Payload.Response.ProductResponse;
import com.microservices.productservice.Repository.ProductRepository;
import com.microservices.productservice.Service.Imp.ProductServiceImp;
import com.microservices.productservice.Helper.ProductConverter;
import com.microservices.productservice.Repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.CacheResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService implements ProductServiceImp {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductConverter productConverter;

    @Value("${root.folder}")
    private String rootFolder;

    @Override
    public boolean insertProduct(String name, MultipartFile file, double price, int quantity, int idCategory, String description) throws IOException {
        String pathImage= rootFolder + "/" + file.getOriginalFilename();

        Path path = Paths.get(rootFolder);
        Path pathImageCopy = Paths.get(pathImage);
        if(!Files.exists(path)){
            Files.createDirectory(path);
        }

        Files.copy(file.getInputStream(), pathImageCopy, StandardCopyOption.REPLACE_EXISTING);

        ProductEntity productEntity =  new ProductEntity();
        productEntity.setName(name);
        productEntity.setImage(file.getOriginalFilename());
        productEntity.setPrice(price);
        productEntity.setQuantity(quantity);
        productEntity.setDescription(description);

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(idCategory);
        productEntity.setCategory(categoryEntity);

        productEntity.setCreateDate(new Date());

        productRepository.save(productEntity);
        return true;
    }

    @Override
    public List<ProductResponse> getAllProduct() {
        List<ProductEntity> list = productRepository.findAll();
        List<ProductResponse> responseList = new ArrayList<>();

        for (ProductEntity item : list) {
            ProductResponse productResponse = new ProductResponse();
            productResponse.setId(item.getId());
            productResponse.setName(item.getName());
            productResponse.setImage(item.getImage());
            productResponse.setQuantity(item.getQuantity());
            productResponse.setDescription(item.getDescription());

            if (item.getPrice() != null) {
                productResponse.setPrice(item.getPrice().doubleValue());
            } else {
                productResponse.setPrice(0.0);
            }

            CategoryEntity categoryEntity = item.getCategory();
            if (categoryEntity != null) {
                CategoryResponse categoryResponse = new CategoryResponse();
                categoryResponse.setName(categoryEntity.getName());
                categoryResponse.setId(categoryEntity.getId());
                categoryResponse.setCreateDate(categoryEntity.getCreateDate());
                productResponse.setCategory(categoryResponse);
            }
            productResponse.setCreateDate(item.getCreateDate());

            responseList.add(productResponse);
        }

        return responseList;
    }

    @Override
    public Page<ProductResponse> getAllProductsPage(Integer pageNo) {
        PageRequest pageable = PageRequest.of(pageNo, 10); // Page size of 10
        Page<ProductEntity> productPage = productRepository.findAll(pageable);

        return productPage.map(ProductConverter::toProductResponse);
    }

    @Override
    public ProductResponse getProductById(int id) {
        Optional<ProductEntity> productOptional = productRepository.findById(id);

        if (productOptional.isPresent()) {
            ProductEntity productEntity = productOptional.get();
            ProductResponse productResponse = new ProductResponse();
            productResponse.setId(productEntity.getId());
            productResponse.setName(productEntity.getName());
            productResponse.setImage(productEntity.getImage());
            productResponse.setDescription(productEntity.getDescription());
            productResponse.setPrice(productEntity.getPrice());
            productResponse.setCreateDate(productEntity.getCreateDate());

            CategoryEntity categoryEntity = productEntity.getCategory();
            if (categoryEntity != null) {
                CategoryResponse categoryResponse = new CategoryResponse();
                categoryResponse.setName(categoryEntity.getName());
                categoryResponse.setId(categoryEntity.getId());
                categoryResponse.setCreateDate(categoryEntity.getCreateDate());
                productResponse.setCategory(categoryResponse);
            }
            return productResponse;
        }else {
            return null;
        }
    }

    @Override
    public boolean deleteProductById(int idProduct) {
        if(productRepository.existsById(idProduct)){
            productRepository.deleteById(idProduct);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateProductById(int idProduct, String name, MultipartFile file, String description, double price, int quanity, int idCategory) throws IOException {
        Optional<ProductEntity> productOptional = productRepository.findById(idProduct);
        List<ProductResponse> responseList = new ArrayList<>();

        if (productOptional.isPresent()) {
            ProductEntity productEntity = productOptional.get();

            String oldImage = productEntity.getImage();
            if (oldImage != null) {
                Files.deleteIfExists(Paths.get(rootFolder, oldImage));
            }

            String newImage = file.getOriginalFilename();
            Path newPathImageCopy = Paths.get(rootFolder, newImage);
            Files.copy(file.getInputStream(), newPathImageCopy, StandardCopyOption.REPLACE_EXISTING);

            productEntity.setName(name);
            productEntity.setImage(file.getOriginalFilename());
            productEntity.setPrice(price);
            productEntity.setQuantity(quanity);
            productEntity.setDescription(description);

            CategoryEntity categoryEntity = new CategoryEntity();
            categoryEntity.setId(idCategory);
            productEntity.setCategory(categoryEntity);

            productEntity.setCreateDate(new Date());

            productRepository.save(productEntity);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<ProductResponse> getProductByName(String productName) {
        List<ProductEntity> products = productRepository.findByNameContainingIgnoreCase(productName);
        return products.stream()
                .map(ProductConverter::toProductResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> searchProducts(String keyword) {
        List<ProductEntity> products = productRepository.searchProducts(keyword);
        return products.stream()
                .map(ProductConverter::toProductResponse)
                .collect(Collectors.toList());
    }
}
