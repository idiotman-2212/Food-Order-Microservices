package com.microservices.favouriteservice.Repository;

import com.microservices.favouriteservice.Entity.FavouriteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavouriteRepository extends JpaRepository<FavouriteEntity,Integer> {
}
