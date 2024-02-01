package com.sb.order.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.sb.order.entity.Order;

@Repository
public interface OrderRepo extends MongoRepository<Order, Integer>{
 }