package com.ordersystem.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ordersystem.entity.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order,Long> {

	
}
