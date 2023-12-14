package com.lab.airbnb.model.dao;

import com.lab.airbnb.model.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface OrderDAO extends ListCrudRepository<Order, String>{
    List<Order> findByStatus(String status, Pageable pageable);


}
