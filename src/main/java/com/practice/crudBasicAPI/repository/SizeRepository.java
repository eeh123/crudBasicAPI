package com.practice.crudBasicAPI.repository;

import com.practice.crudBasicAPI.entity.Products;
import com.practice.crudBasicAPI.entity.Sizes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface SizeRepository extends JpaRepository<Sizes, Integer> {
}
