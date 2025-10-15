package com.brandao.dscatalog.dtos.errorHandlers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FieldMessage {

    private String fieldName;
    private String message;
}
