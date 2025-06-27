package com.brandao.dscatalog.services.exceptions;

public class EmptyRequestException extends RuntimeException{
    
    public EmptyRequestException(String e){
        super(e);
    }

}
