package com.practice.crudBasicAPI.service.implement;

import com.practice.crudBasicAPI.dto.*;
import com.practice.crudBasicAPI.entity.*;
import com.practice.crudBasicAPI.exceptionhandler.ResourceNotFoundException;
import com.practice.crudBasicAPI.repository.*;
import com.practice.crudBasicAPI.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j //used for logging
@Service
public class ProductService implements IProductService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private IProductService iProductService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SizeRepository sizeRepository;
    @Autowired
    private ProductImagesRepository productImagesRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private ModelMapper modelMapper;


//---------------------------------Products---------------------------------
    @Override //[WORKING]
    public List<ProductDisplayDTO> getAllProducts() {
        ProductDisplayDTO productDisplayDTO = new ProductDisplayDTO();
        List<ProductDisplayDTO> allProducts = new ArrayList<>();
        List<Products> products = new ArrayList<>(productRepository.findAll());
        for (Products l : products) {
            productDisplayDTO = modelMapper.map(l, ProductDisplayDTO.class);
            allProducts.add(productDisplayDTO);
        }
        return allProducts;
    }
    @Override //[WORKING]
    public ProductDisplayDTO getProductById(int id) {
        ProductDisplayDTO productDisplayDTO = new ProductDisplayDTO();
        Optional<Products> existingProduct = productRepository.findById(id);
        if(existingProduct.isPresent()) {
            productDisplayDTO = modelMapper.map(existingProduct.get(), ProductDisplayDTO.class);
            return productDisplayDTO;
        }
        throw new ResourceNotFoundException("No existing product with id: " + id + " found");
    }
    @Transactional
    @Override //[WORKING]
    public ProductDisplayDTO addProduct(AddProductDTO product) {
        Products p = new Products();
        p.setName(product.getName());
        p.setDescription(product.getDescription());
        p.setPrice(product.getPrice());
        p.setStockQty(product.getStockQty());
        p.setStatus(product.getStatus());
        p.setSizes(product.getSizes());
//        p.setProductImages(products.getProductImages());
        var productDetails = productRepository.save(p);
        ProductDisplayDTO productDisplayDTO = modelMapper.map(productDetails, ProductDisplayDTO.class);

        List<Sizes> s = new ArrayList<>(product.getSizes());
        for (Sizes sizes : s) {
            sizes.setName(getSizeName(product.getSizes()));
            sizes.setProduct(productDetails);
            sizeRepository.save(sizes);
        }

//        List<ProductImages> pi = new ArrayList<>(products.getProductImages());
//        for (ProductImages productImages : pi) {
//            productImages.setImgType(getImgType(products.getProductImages()));
//            productImages.setProduct(productDetails);
//            productImagesRepository.save(sizes);
//        }


        return productDisplayDTO;
    }
    @Override //[WORKING]
    public ProductDisplayDTO updateProduct(int id, UpdateProductDTO udProduct) {
        ProductDisplayDTO productDisplayDTO = new ProductDisplayDTO();
        Optional<Products> existingProduct = productRepository.findById(id);
        if (existingProduct.isPresent()){
            Products productDetails = existingProduct.get();
            productDetails.setName(udProduct.getName());
            productDetails.setDescription(udProduct.getDescription());
            productDetails.setPrice(udProduct.getPrice());
            productDetails.setStockQty(udProduct.getStockQty());
            productDetails.setStatus(udProduct.getStatus());
            productRepository.save(productDetails);
            productDisplayDTO = modelMapper.map(productDetails, ProductDisplayDTO.class);
            return productDisplayDTO;
        }
        throw new ResourceNotFoundException("No existing line with id: " + id + " found");
    }
    @Override //[WORKING]
    public ProductDisplayDTO softDeleteProduct(int id){
        //cascades soft delete till ngrecords due to parent-child relationship
        productRepository.softDeleteProduct(id);
        //works only if every parent has a child record, otherwise wont cascade properly
        //every station must have at least one checklist and every checklist of that station
        //must have at least one ngrecord for the query to work as intended
        return getProductById(id);
    }




    protected String getSizeName(Set<Sizes> set){
        String sName = "";
        if (set == null){
            return null;
        }

        for (Sizes size : set) {
            sName = size.getName();
        }
        return sName;
    }
    protected String getImgType(Set<ProductImages> set){
        String imgType = "";
        if (set == null){
            return null;
        }

        for (ProductImages pi : set) {
            imgType = pi.getImgType();
        }
        return imgType;
    }

}
