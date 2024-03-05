package srinageswari.lEarn.orderservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import srinageswari.lEarn.orderservice.entity.Order;
import srinageswari.lEarn.orderservice.exception.OrderServiceCustomException;
import srinageswari.lEarn.orderservice.external.PaymentResponse;
import srinageswari.lEarn.orderservice.external.client.PaymentService;
import srinageswari.lEarn.orderservice.external.client.ProductService;
import srinageswari.lEarn.orderservice.external.request.PaymentRequest;
import srinageswari.lEarn.orderservice.model.OrderRequest;
import srinageswari.lEarn.orderservice.model.OrderResponse;
import srinageswari.lEarn.orderservice.model.ProductResponse;
import srinageswari.lEarn.orderservice.repository.OrderRepository;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Log4j2
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final PaymentService paymentService;
    private final RestTemplate restTemplate;

    /**
     * @param orderReq
     * @return
     */
    @Override
    public long placeOrder(OrderRequest orderReq) {
        log.info("Placing order request: {}", orderReq);

        productService.reduceQuantity(orderReq.getProductId(), orderReq.getQuantity());

        log.info("Creating order with status CREATED");
        Order order = Order.builder().amount(orderReq.getTotalAmount()).orderStatus("CREATED").productId(orderReq.getProductId()).orderDate(Instant.now()).quantity(orderReq.getQuantity()).build();
        order = orderRepository.save(order);

        log.info("Calling payment service to complete the payment");
        PaymentRequest paymentRequest = PaymentRequest.builder().orderId(order.getId()).paymentMode(orderReq.getPaymentMode()).amount(orderReq.getTotalAmount()).build();
        String orderStatus = null;

        paymentService.doPayment(paymentRequest);
        log.info("Payment done Successfully. Changing the order status to placed");
        orderStatus = "PLACED";

        order.setOrderStatus(orderStatus);
        orderRepository.save(order);
        log.info("Order placed successfully: {}", order);
        return order.getId();
    }

    /**
     * @param orderId
     * @return
     */
    @Override
    public OrderResponse getOrderDetails(long orderId) {
        log.info("Get Order details for order Id: {}", orderId);
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderServiceCustomException("Order not found for the orderId", "NOT FOUND", 404));
        ProductResponse productResponse = restTemplate.getForObject("http://PRODUCT-SERVICE/product/" + order.getProductId(), ProductResponse.class);
        assert productResponse != null;
        log.info("Getting payment info from payment service");
        PaymentResponse paymentResponse = restTemplate.getForObject("http://PAYMENT-SERVICE/payment/" + orderId, PaymentResponse.class);
        OrderResponse.ProductDetails productDetails = OrderResponse.ProductDetails.builder().productId(productResponse.getProductId()).productName(productResponse.getProductName()).price(productResponse.getPrice()).build();
        assert paymentResponse != null;
        OrderResponse.PaymentDetails paymentDetails = OrderResponse.PaymentDetails.builder().paymentId(paymentResponse.getPaymentId()).paymentDate(paymentResponse.getPaymentDate()).paymentMode(paymentResponse.getPaymentMode()).status(paymentResponse.getStatus()).build();
        return OrderResponse.builder().orderId(order.getId()).orderStatus(order.getOrderStatus()).amount(order.getAmount()).orderDate(order.getOrderDate()).productDetails(productDetails).paymentDetails(paymentDetails).build();
    }
}
