package com.naveend3v.readerhub.exceptions;

public class ProductNotExistException extends IllegalArgumentException{
    public ProductNotExistException(String msg){
        super(msg);
    }
}
