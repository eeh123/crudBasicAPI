package com.practice.crudBasicAPI.repository;

import com.practice.crudBasicAPI.entity.Lines;
import com.practice.crudBasicAPI.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Products, Integer> {
    @Modifying
    @Query(value = "UPDATE tbl_products SET status = 0 WHERE id = ?1",
            nativeQuery = true)
    void softDeleteProduct(int id);
}
