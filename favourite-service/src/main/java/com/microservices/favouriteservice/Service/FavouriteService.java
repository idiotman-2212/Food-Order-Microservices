package com.microservices.favouriteservice.Service;

import com.microservices.favouriteservice.Entity.FavouriteEntity;
import com.microservices.favouriteservice.Payload.ApiResponse;
import com.microservices.favouriteservice.Payload.FavouriteResponse;
import com.microservices.favouriteservice.Payload.ProductResponse;
import com.microservices.favouriteservice.Payload.UserResponse;
import com.microservices.favouriteservice.Repository.FavouriteRepository;
import com.microservices.favouriteservice.Service.Imp.FavouriteServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
public class FavouriteService implements FavouriteServiceImp {
    @Autowired
    private FavouriteRepository favouriteRepository;

    @Autowired
    private CallAPI callAPI;

    @Override
    public List<FavouriteResponse> findAll() {
        List<FavouriteEntity> list = favouriteRepository.findAll();
        List<FavouriteResponse> listResponse = new ArrayList<>();

        for (FavouriteEntity data: list) {
            FavouriteResponse response = new FavouriteResponse();
            response.setId(data.getId());
            response.setProductId(data.getProductId());
            response.setUserId(data.getUserId());
            response.setCreateDate(data.getCreateDate());

            // Gọi API để lấy thông tin user
            Mono<ApiResponse<UserResponse>> userResponseMono = callAPI.getUserById(response.getUserId(), "token");
            UserResponse userResponse = Objects.requireNonNull(userResponseMono.block()).getData();
            if (userResponse != null) {
                response.setUserResponse(userResponse);
            }else {
                System.out.println("No user response for favourite: " + response.getId());
            }

            // Gọi API để lấy thông tin product
            Mono<ApiResponse<ProductResponse>> responseMono = callAPI.getProductById(response.getProductId());
            ProductResponse productResponse = Objects.requireNonNull(responseMono.block()).getData();
            if (productResponse != null) {
                response.setProductResponse(productResponse);
            }else {
                System.out.println("No product response for favourite: " + response.getId());
            }

            listResponse.add(response);
        }
        return listResponse;
    }

    @Override
    public  List<FavouriteResponse> findById(int favouriteId) {
        Optional<FavouriteEntity> optional = favouriteRepository.findById(favouriteId);

        if (optional.isPresent()) {
            FavouriteEntity favouriteEntity  = optional.get();
            FavouriteResponse favouriteResponse = new FavouriteResponse();
            favouriteResponse.setId(favouriteEntity.getId());
            favouriteResponse.setProductId(favouriteEntity.getProductId());
            favouriteResponse.setUserId(favouriteEntity.getUserId());
            favouriteResponse.setCreateDate(favouriteEntity.getCreateDate());

            List<FavouriteResponse> responseList = new ArrayList<>();

            // Gọi API để lấy thông tin user
            Mono<ApiResponse<UserResponse>> userResponseMono = callAPI.getUserById(favouriteResponse.getUserId(), "token");
            UserResponse userResponse = Objects.requireNonNull(userResponseMono.block()).getData();
            if (userResponse != null) {
                favouriteResponse.setUserResponse(userResponse);
            }else {
                System.out.println("No user response for favourite: " + favouriteResponse.getId());
            }

            // Gọi API để lấy thông tin product
            Mono<ApiResponse<ProductResponse>> responseMono = callAPI.getProductById(favouriteResponse.getProductId());
            ProductResponse productResponse = Objects.requireNonNull(responseMono.block()).getData();
            if (productResponse != null) {
                favouriteResponse.setProductResponse(productResponse);
            }else {
                System.out.println("No product response for favourite: " + favouriteResponse.getId());
            }

            responseList.add(favouriteResponse);
            return responseList;
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public boolean save(int idProduct, int idUser) {
        FavouriteEntity favourite = new FavouriteEntity();
        favourite.setUserId(idUser);
        favourite.setProductId(idProduct);
        favourite.setCreateDate(new Date());

        favouriteRepository.save(favourite);
        return true;
    }

    @Override
    public boolean update(int idProduct, int idUser) {
        FavouriteEntity favourite = new FavouriteEntity();
        favourite.setUserId(idUser);
        favourite.setProductId(idProduct);
        favourite.setCreateDate(new Date());

        favouriteRepository.save(favourite);
        return true;
    }

    @Override
    public boolean deleteById(int favouriteId) {
        if (favouriteRepository.existsById(favouriteId)) {

            favouriteRepository.deleteById(favouriteId);
            return true;
        } else {
            return false;
        }
    }
}
