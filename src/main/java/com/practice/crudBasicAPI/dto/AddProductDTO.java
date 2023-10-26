package com.practice.crudBasicAPI.dto;

import com.practice.crudBasicAPI.entity.ProductImages;
import com.practice.crudBasicAPI.entity.Sizes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddProductDTO {

    //Product required fields
    private String name;
    private String description;
    private double price;
    private int stockQty;
    private int status;
    private Set<Sizes> sizes;
//    private Set<ProductImages> productImages;



//    //Image required fields
//    private String fileName;
//    private String downloadUri;
//    private long size;
//    //??????

}
