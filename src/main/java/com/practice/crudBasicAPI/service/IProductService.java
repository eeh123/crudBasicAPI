package com.practice.crudBasicAPI.service;

import com.practice.crudBasicAPI.dto.AddProductDTO;
import com.practice.crudBasicAPI.dto.ProductDisplayDTO;
import com.practice.crudBasicAPI.dto.UpdateProductDTO;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface IProductService {

//------------Products------------
    List<ProductDisplayDTO> getAllProducts();
    List<ProductDisplayDTO> getActiveProducts();
    ProductDisplayDTO getProductById(int id);
    ProductDisplayDTO addProduct(AddProductDTO product);
    ProductDisplayDTO updateProduct(int id, UpdateProductDTO udProduct);
    ProductDisplayDTO softDeleteProduct(int id);


}
