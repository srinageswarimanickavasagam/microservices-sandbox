package srinageswari.lEarn.orderservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import srinageswari.lEarn.orderservice.entity.Order;
import srinageswari.lEarn.orderservice.model.OrderRequest;
import srinageswari.lEarn.orderservice.repository.OrderRepository;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Log4j2
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    /**
     * @param orderReq
     * @return
     */
    @Override
    public long placeOrder(OrderRequest orderReq) {
        log.info("Placing order request: {}",orderReq);
        Order order = Order.builder().amount(orderReq.getTotalAmount()).orderStatus("CREATED").productId(orderReq.getProductId()).orderDate(Instant.now()).quantity(orderReq.getQuantity()).build();
        order = orderRepository.save(order);
        log.info("Order placed successfully: {}",order);
        return order.getId();
    }
}
