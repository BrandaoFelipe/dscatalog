package com.brandao.dscatalog.dtos;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CustomError {

    private Instant timestamp;
    private Integer status;
    private String error;
    private String path;

}