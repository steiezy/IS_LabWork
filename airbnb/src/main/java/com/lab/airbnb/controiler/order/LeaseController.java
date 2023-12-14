package com.lab.airbnb.controiler.order;

import com.lab.airbnb.domain.QueryInfo;
import com.lab.airbnb.model.Order;
import com.lab.airbnb.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/lease")
public class LeaseController {

    private OrderService orderService;

    public LeaseController(OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping("/unleased")
    public List<Order> getAllUnleased(@RequestParam(name = "page", required = false) Integer page,
                                      @RequestParam(name = "size", required = false) Integer size) {
        QueryInfo.QueryInfoBuilder builder = QueryInfo.builder();
        if (page != null) builder.page(page);
        if (size != null) builder.size(size);
        return orderService.findAllUnleased(builder.build());
    }
}
