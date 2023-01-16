package com.example.apibasic.validate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter @Setter @ToString
public class Address {
    @NotBlank
    private String street;

    @NotBlank
    private String postCode;
}
