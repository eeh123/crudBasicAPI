package com.practice.crudBasicAPI.repository;

import com.practice.crudBasicAPI.entity.ProductImages;
import com.practice.crudBasicAPI.entity.Sizes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ProductImagesRepository extends JpaRepository<ProductImages, Integer> {
}
