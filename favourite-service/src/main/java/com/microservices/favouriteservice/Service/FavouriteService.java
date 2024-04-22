package com.microservices.favouriteservice.Service;

import com.microservices.favouriteservice.Entity.FavouriteEntity;
import com.microservices.favouriteservice.Payload.FavouriteResponse;
import com.microservices.favouriteservice.Repository.FavouriteRepository;
import com.microservices.favouriteservice.Service.Imp.FavouriteServiceImp;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class FavouriteService implements FavouriteServiceImp {
    @Autowired
    private FavouriteRepository favouriteRepository;

    @Override
    public List<FavouriteResponse> findAll() {
        List<FavouriteEntity> list = favouriteRepository.findAll();
        List<FavouriteResponse> listResponse = new ArrayList<>();

        for (FavouriteEntity data: list) {
            FavouriteResponse response = new FavouriteResponse();
            response.setProductId(data.getProductId());
            response.setUserId(data.getUserId());
            response.setCreateDate(data.getCreateDate());
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
            favouriteResponse.setProductId(favouriteEntity.getProductId());
            favouriteResponse.setUserId(favouriteEntity.getUserId());
            favouriteResponse.setCreateDate(favouriteEntity.getCreateDate());

            List<FavouriteResponse> responseList = new ArrayList<>();
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
