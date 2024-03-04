package srinageswari.lEarn.orderservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import srinageswari.lEarn.orderservice.model.OrderRequest;
import srinageswari.lEarn.orderservice.service.OrderService;

@RestController
@RequestMapping("/order")
@Log4j2
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/placeOrder")
    public ResponseEntity<Long> placeOrder(@RequestBody OrderRequest orderReq)
    {
        long orderId = orderService.placeOrder(orderReq);
        log.info("Order Id: {}",orderId);
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }
}
