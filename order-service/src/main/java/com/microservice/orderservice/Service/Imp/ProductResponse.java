package com.microservice.orderservice.Service.Imp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse {
    private int id;
    private String name;
    private String image;
    private double price;
    private String description;
    private int quantity;
    private String createDate;
//    private Category category;
}
