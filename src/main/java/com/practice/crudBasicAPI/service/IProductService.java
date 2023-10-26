package com.practice.crudBasicAPI.service;

import com.practice.crudBasicAPI.dto.*;
import com.practice.crudBasicAPI.repository.ProductRepository;
import com.practice.crudBasicAPI.service.implement.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface IProductService {

//------------Products------------
    List<ProductDisplayDTO> getAllProducts();
    ProductDisplayDTO getProductById(int id);
    ProductDisplayDTO addProduct(AddProductDTO product);
    ProductDisplayDTO updateProduct(int id, UpdateProductDTO udProduct);
    ProductDisplayDTO softDeleteProduct(int id);


}
