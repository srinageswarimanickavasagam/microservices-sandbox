package srinageswari.lEarn.orderservice.service;

import srinageswari.lEarn.orderservice.model.OrderRequest;
import srinageswari.lEarn.orderservice.model.OrderResponse;

public interface OrderService {
    long placeOrder(OrderRequest orderReq);
    OrderResponse getOrderDetails(long orderId);
}
