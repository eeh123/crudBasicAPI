package com.practice.crudBasicAPI.controller;

import com.practice.crudBasicAPI.dto.*;
import com.practice.crudBasicAPI.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private IProductService iProductService;

    public ProductController(IProductService iProductService) {
        super();
        this.iProductService = iProductService;
    }

    //---------------------------------Products---------------------------------
    @GetMapping("/getProducts") //[WORKING]
    public ResponseEntity<List<ProductDisplayDTO>> getAllProducts(){
        List<ProductDisplayDTO> products = iProductService.getAllProducts();
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // GET ALL PRODUCTS THAT ARE ACTIVE
    @GetMapping("/getProduct/{id}") //[WORKING]
    public ResponseEntity<ProductDisplayDTO> getProductById(@PathVariable Integer id){
        ProductDisplayDTO product = iProductService.getProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
    @PostMapping("/addProduct") //[WORKING]
    public ResponseEntity<ProductDisplayDTO> addNewProduct(@RequestBody AddProductDTO addProductDTO){
        return new ResponseEntity<ProductDisplayDTO>(iProductService.addProduct(addProductDTO), HttpStatus.CREATED);
    }
    @PutMapping("/updateProduct/{id}") //[WORKING]
    public ResponseEntity<ProductDisplayDTO> updateProduct(@PathVariable Integer id, @RequestBody UpdateProductDTO updateProductDTO){
        ProductDisplayDTO update_product = iProductService.updateProduct(id, updateProductDTO);
        return new ResponseEntity<>(update_product, HttpStatus.OK);
    }
    @PutMapping("/softDeleteProduct/{id}") //[WORKING]
    public ResponseEntity<ProductDisplayDTO> softDeleteProduct(@PathVariable Integer id){
        return new ResponseEntity<>(iProductService.softDeleteProduct(id), HttpStatus.OK);
    }
}
