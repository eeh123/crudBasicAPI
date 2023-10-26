package com.practice.crudBasicAPI.repository;

import com.practice.crudBasicAPI.entity.Ngrecords;
import com.practice.crudBasicAPI.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface OrderItemRepository extends JpaRepository<OrderItems,Integer> {
}
