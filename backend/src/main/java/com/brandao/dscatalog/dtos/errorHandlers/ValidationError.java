package com.brandao.dscatalog.dtos.errorHandlers;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class ValidationError extends CustomError{

    private List<FieldMessage> errors = new ArrayList<>(); 


    public void addError(String fieldName, String message){

        errors.add(new FieldMessage(fieldName, message));
    }
}
