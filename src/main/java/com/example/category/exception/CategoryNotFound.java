package com.example.category.exception;

import lombok.Data;

@Data
public class CategoryNotFound extends  RuntimeException{
    private  String errorMessage;

    public  CategoryNotFound(String message , String errorMessage){
        super(message);
        this.errorMessage= errorMessage;
    }
}
