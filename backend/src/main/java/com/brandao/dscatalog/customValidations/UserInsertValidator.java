package com.brandao.dscatalog.customValidations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.brandao.dscatalog.dtos.error.FieldMessage;
import com.brandao.dscatalog.dtos.request.UserRequestDTO;
import com.brandao.dscatalog.entities.User;
import com.brandao.dscatalog.repositories.UserRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserRequestDTO> {

    @Autowired
    private UserRepository repository;

    @Override
    public void initialize(UserInsertValid userInsertValid) {
    }

    @Override
    public boolean isValid(UserRequestDTO request,
      ConstraintValidatorContext context) {
        
        List<FieldMessage> list = new ArrayList<>();

        User user = repository.findByEmail(request.getEmail());
        if(user != null) {
            list.add(new FieldMessage("email", "Email already exists"));
        }
        
       for(FieldMessage e : list) {
           context.disableDefaultConstraintViolation();
           context.buildConstraintViolationWithTemplate(e.getMessage())
               .addPropertyNode(e.getFieldName()).addConstraintViolation();
       }

       return list.isEmpty();
    }

}
