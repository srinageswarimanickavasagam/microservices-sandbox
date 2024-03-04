package srinageswari.lEarn.orderservice.service;

import srinageswari.lEarn.orderservice.model.OrderRequest;

public interface OrderService {
    long placeOrder(OrderRequest orderReq);
}
