package com.brandao.dscatalog.customValidations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = UserInsertValidator.class) //Vincula a anotação à classe que contém a lógica de validação, no caso vincula @interface UserInsertValid à UserInsertValidator.class
@Target({ ElementType.TYPE }) //Significa que só pode ser usada em classes, interfaces, enums. [Outras opções: METHOD: métodos, FIELD: campos, PARAMETER: parâmetros de métodos]
@Retention(RetentionPolicy.RUNTIME) //Define quando a anotação estará disponível RUNTIME: A anotação estará disponível via reflection durante a execução

public @interface UserInsertValid {
    String message() default "an validation error has occured!"; //Define a mensagem de erro padrão - Para que serve: Mensagem retornada quando a validação falhar

    Class<?>[] groups() default {}; //O que faz: Permite agrupar validações, Para que serve: Validar apenas em cenários específicos

    Class<? extends Payload>[] payload() default {}; //Transporta metadados extras sobre o erro, Para que serve: Para classificar a severidade ou tipo do erro
}