package com.adekunle.service.productService;

import com.adekunle.dto.ProductDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

    Flux<ProductDto> getAllProducts();
    Mono<ProductDto> getProduct(String id);
    Flux<ProductDto> findByPriceRange(double min,double max);
    Mono<ProductDto> saveProduct(Mono<ProductDto> productDtoMono);
    Mono<ProductDto> updateProduct(Mono<ProductDto> productDtoMono,String id);
    Mono<Void> deleteProduct(String id);
}
