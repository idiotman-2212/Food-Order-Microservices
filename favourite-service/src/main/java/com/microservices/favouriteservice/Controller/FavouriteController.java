package com.microservices.favouriteservice.Controller;

import com.microservices.favouriteservice.Payload.BaseResponse;
import com.microservices.favouriteservice.Payload.FavouriteResponse;
import com.microservices.favouriteservice.Service.FavouriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favourites")
public class FavouriteController {
    @Autowired
    private FavouriteService favouriteService;

    @GetMapping("")
    public ResponseEntity<?> getAllRoles(){
        List<FavouriteResponse> list = favouriteService.findAll();
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMessage("Get all favourite");
        baseResponse.setData(list);
        baseResponse.setStatusCode(200);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<BaseResponse> addFavourite(@RequestParam int idProduct, @RequestParam int idUser) {
        boolean isAdded = favouriteService.save(idProduct, idUser);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setData(isAdded ? "Insert successfully" : "Insert failed");
        baseResponse.setMessage("Insert new favourite");

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFavouriteById(@PathVariable int id){
        BaseResponse baseResponse = new BaseResponse();
        boolean isDelete = favouriteService.deleteById(id);
        baseResponse.setMessage("Delete favourite By id");
        baseResponse.setStatusCode(200);
        baseResponse.setData(isDelete? "Delete successfully" : "Delete failed");
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);

    }
}
