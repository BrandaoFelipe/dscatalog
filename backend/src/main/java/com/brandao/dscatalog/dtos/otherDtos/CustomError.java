package com.brandao.dscatalog.dtos.otherDtos;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomError {

    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

}