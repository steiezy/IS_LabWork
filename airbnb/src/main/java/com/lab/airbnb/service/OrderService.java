package com.lab.airbnb.service;

import com.lab.airbnb.domain.QueryInfo;
import com.lab.airbnb.model.Order;
import com.lab.airbnb.model.dao.OrderDAO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private OrderDAO orderDAO;

    public OrderService(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    public List<Order> findAllUnleased(QueryInfo queryInfo) {
        Pageable pageable = PageRequest.of(queryInfo.getPage(), queryInfo.getSize());
        return orderDAO.findByStatus("0", pageable);
    }
}
