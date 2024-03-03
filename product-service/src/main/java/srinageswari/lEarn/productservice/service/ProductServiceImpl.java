package srinageswari.lEarn.productservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import srinageswari.lEarn.productservice.entity.Product;
import srinageswari.lEarn.productservice.exception.ProductServiceCustomException;
import srinageswari.lEarn.productservice.model.ProductRequest;
import srinageswari.lEarn.productservice.model.ProductResponse;
import srinageswari.lEarn.productservice.repository.ProductRepository;

import static org.springframework.beans.BeanUtils.*;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    /**
     * @param productRequest
     * @return
     */
    @Override
    public long addProduct(ProductRequest productRequest) {
        log.info("Adding product...");
        Product product = Product.builder().productName(productRequest.getName()).quantity(productRequest.getQuantity()).price(productRequest.getPrice()).build();
        productRepository.save(product);
        log.info("Product created...");
        return product.getProductId();
    }

    /**
     * @param productId
     * @return
     */
    @Override
    public ProductResponse getProductById(long productId) {
        log.info("Get the product for productId: {}",productId);
        Product product = productRepository.findById(productId).orElseThrow(()->new ProductServiceCustomException("Product with given id not found","Product not found"));
        ProductResponse productResponse = new ProductResponse();
        copyProperties(product,productResponse);
        return productResponse;
    }
}
