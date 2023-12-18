package com.lab.airbnb.controller.order;

import com.lab.airbnb.domain.QueryInfo;
import com.lab.airbnb.domain.vo.OrderVo;
import com.lab.airbnb.exception.HouseNotExistException;
import com.lab.airbnb.model.House;
import com.lab.airbnb.model.Order;
import com.lab.airbnb.model.User;
import com.lab.airbnb.service.OrderService;
import com.lab.airbnb.service.PermissionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/lease")
public class LeaseController {

    private OrderService orderService;

    private PermissionService permissionService;

    public LeaseController(OrderService orderService, PermissionService permissionService) {
        this.orderService = orderService;
        this.permissionService = permissionService;
    }


    @GetMapping("/unleased")
    public List<Order> getAllUnleased(@RequestParam(name = "page", required = false) Integer page,
                                      @RequestParam(name = "size", required = false) Integer size) {
        QueryInfo.QueryInfoBuilder builder = QueryInfo.builder();
        if (page != null) builder.page(page);
        if (size != null) builder.size(size);
        return orderService.findAllUnleased(builder.build());
    }


    @PutMapping("/{houseId}/order")
    public ResponseEntity newOrder(@AuthenticationPrincipal User user,
                                          @PathVariable String houseId,
                                         @Valid @RequestBody OrderVo orderVo) {
        if (!permissionService.userHasCreatOrderPermission(user)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<Order> order= null;
        try {
            order = orderService.addNewOrder(houseId,orderVo);
        } catch (HouseNotExistException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        if (order.isPresent()){
            return ResponseEntity.ok(order.get());
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PostMapping("/{orderId}/lease")
    public ResponseEntity lease(@AuthenticationPrincipal User user,
                                @PathVariable String orderId) {
        if (!permissionService.userHasLeaseOrderPermission(user)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Optional<Order> opOrder = orderService.findOrderById(orderId);
        if (opOrder.isPresent()){
            Order order = opOrder.get();
            order.setStatus("1");
            orderService.save(order);
            return ResponseEntity.ok(order);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
