package srinageswari.lEarn.orderservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import srinageswari.lEarn.orderservice.entity.Order;
import srinageswari.lEarn.orderservice.external.client.PaymentService;
import srinageswari.lEarn.orderservice.external.client.ProductService;
import srinageswari.lEarn.orderservice.external.request.PaymentRequest;
import srinageswari.lEarn.orderservice.model.OrderRequest;
import srinageswari.lEarn.orderservice.repository.OrderRepository;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Log4j2
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final PaymentService paymentService;
    /**
     * @param orderReq
     * @return
     */
    @Override
    public long placeOrder(OrderRequest orderReq) {
        log.info("Placing order request: {}",orderReq);

        productService.reduceQuantity(orderReq.getProductId(),orderReq.getQuantity());

        log.info("Creating order with status CREATED");
        Order order = Order.builder().amount(orderReq.getTotalAmount()).orderStatus("CREATED").productId(orderReq.getProductId()).orderDate(Instant.now()).quantity(orderReq.getQuantity()).build();
        order = orderRepository.save(order);

        log.info("Calling payment service to complete the payment");
        PaymentRequest paymentRequest = PaymentRequest.builder().orderId(order.getId()).paymentMode(orderReq.getPaymentMode()).amount(orderReq.getTotalAmount()).build();
        String orderStatus = null;

        try
        {
            paymentService.doPayment(paymentRequest);
            log.info("Payment done Successfully. Changing the order status to placed");
            orderStatus = "PLACED";
        }
        catch(Exception e)
        {
            log.info("Error occured in payment. Changing the order status to failed");
            orderStatus = "FAILED";
        }
        order.setOrderStatus(orderStatus);
        orderRepository.save(order);
        log.info("Order placed successfully: {}",order);
        return order.getId();
    }
}
