package com.practice.crudBasicAPI.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDisplayDTO {

    private int id;
    private String name;
    private String description;
    private double price;
    private int stockQty;
    private int status;

}
