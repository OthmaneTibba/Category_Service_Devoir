package com.example.category.exception;

import lombok.Data;


@Data
public class CategoryExist extends  RuntimeException{
    private  String errorMessage;

    public  CategoryExist(String message , String errorMessage){
        super(message);
        this.errorMessage= errorMessage;
    }
}
