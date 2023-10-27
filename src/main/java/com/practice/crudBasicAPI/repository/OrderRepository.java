package com.practice.crudBasicAPI.repository;

import com.practice.crudBasicAPI.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface OrderRepository extends JpaRepository<Orders,Integer> {
}
