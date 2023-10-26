package com.practice.crudBasicAPI.service;

import com.practice.crudBasicAPI.dto.*;
import com.practice.crudBasicAPI.entity.Products;

import java.util.List;

public interface IProductService {

//------------Products------------
    List<ProductDisplayDTO> getAllProducts();
    ProductDisplayDTO getProductById(int id);
    ProductDisplayDTO addProduct(AddProductDTO product);
    ProductDisplayDTO updateProduct(int id, UpdateProductDTO udProduct);
    ProductDisplayDTO softDeleteProduct(int id);


}
