package com.brandao.dscatalog.customValidations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.brandao.dscatalog.dtos.errorHandlers.FieldMessage;
import com.brandao.dscatalog.dtos.request.UserRequestDTO;
import com.brandao.dscatalog.entities.User;
import com.brandao.dscatalog.repositories.UserRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserRequestDTO> { //Bean validation customizado

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
           context.disableDefaultConstraintViolation(); //Quando você quer criar mensagens de erro personalizadas e não quer que a mensagem padrão da anotação seja incluída, usar quando você for adicionar violações personalizadas com buildConstraintViolationWithTemplate()
           context.buildConstraintViolationWithTemplate(e.getMessage()) //Para criar mensagens de erro específicas ao invés de usar as mensagens padrão
               .addPropertyNode(e.getFieldName()).addConstraintViolation();//.addPropertyNode - Para indicar qual campo está com o erro - .addConstraintViolation() É o método que efetivamente "commita" a violação criada
       }

       return list.isEmpty();
    }

}
