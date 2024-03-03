package srinageswari.lEarn.productservice.service;

import srinageswari.lEarn.productservice.model.ProductRequest;
import srinageswari.lEarn.productservice.model.ProductResponse;

public interface ProductService {
    long addProduct(ProductRequest productRequest);

    ProductResponse getProductById(long productId);
}
