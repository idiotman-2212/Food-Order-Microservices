package com.microservices.favouriteservice.Service.Imp;

import com.microservices.favouriteservice.Payload.FavouriteResponse;

import java.util.List;

public interface FavouriteServiceImp {
    List<FavouriteResponse> findAll();
    List<FavouriteResponse> findById( int favouriteId);
    boolean save(int idProduct, int idUser);
    boolean update(int idProduct, int idUser);
    boolean deleteById( int favouriteId);
}
