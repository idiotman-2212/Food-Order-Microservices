package com.microservices.productservice.Payload.Request;


import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class CategoryRequest {
    @NotNull(message = "Tên không được phép rỗng")
    private String name;
    private Date createDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
