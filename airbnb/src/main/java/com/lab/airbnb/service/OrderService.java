package com.lab.airbnb.service;

import com.lab.airbnb.domain.QueryInfo;
import com.lab.airbnb.domain.vo.OrderVo;
import com.lab.airbnb.exception.HouseNotExistException;
import com.lab.airbnb.model.House;
import com.lab.airbnb.model.Order;
import com.lab.airbnb.model.dao.OrderDAO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    private OrderDAO orderDAO;

    private HouseService houseService;

    public OrderService(OrderDAO orderDAO, HouseService houseService) {
        this.orderDAO = orderDAO;
        this.houseService = houseService;
    }

    public List<Order> findAllUnleased(QueryInfo queryInfo) {
        Pageable pageable = PageRequest.of(queryInfo.getPage(), queryInfo.getSize());
        return orderDAO.findByStatus("0", pageable);
    }

    public Optional<Order> addNewOrder(String houseId, OrderVo orderVo) throws HouseNotExistException {
        Optional<House> opHouse = houseService.findByHouseId(houseId);
        if (opHouse.isPresent()) {
            Order order = new Order();
            String uuid = UUID.randomUUID().toString();
            uuid = uuid.replace("-", "");
            order.setOrderId(uuid);
            order.setHouse(opHouse.get());
            order.setPrice(orderVo.getPrice());
            order.setLeastTime(orderVo.getLeastTime());
            order.setStatus("0");
            order.setReleaseTime(LocalDateTime.now());
            return Optional.of(orderDAO.save(order));
        } else {
            throw new HouseNotExistException();
        }
    }

    public Optional<Order> findOrderById(String orderId) {
        return orderDAO.findById(orderId);
    }

    public Order save(Object order) {
        return orderDAO.save((Order) order);
    }
}
