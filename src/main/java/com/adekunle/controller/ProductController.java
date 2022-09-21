package com.adekunle.controller;

import com.adekunle.dto.ProductDto;
import com.adekunle.service.productService.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/all-products")
    public Flux<ProductDto> getAllProducts(){
        return productService.getAllProducts();
        }

   @GetMapping("/get-product/{id}")
   public Mono<ProductDto> getProduct(@PathVariable String id){
        return productService.getProduct(id);
        }

    @GetMapping("/product-in-range")
    public Flux<ProductDto> getProductsInRange(@RequestParam("min")double min,@RequestParam("max")double max){
        return productService.findByPriceRange(min,max);
    }

    @PostMapping("/save-product")
    public Mono<ProductDto> saveProduct(@RequestBody Mono<ProductDto> productDto ){
return productService.saveProduct(productDto);
    }

    @PutMapping("/update-product/{id}")
    public Mono<ProductDto> updateProduct(@RequestBody Mono<ProductDto> productDto,@PathVariable String id ){
        return productService.updateProduct(productDto,id);
    }

    @DeleteMapping("/delete-product/{id}")
    public Mono<Void> deleteProduct(@PathVariable String id){
        return productService.deleteProduct(id);
    }

}
