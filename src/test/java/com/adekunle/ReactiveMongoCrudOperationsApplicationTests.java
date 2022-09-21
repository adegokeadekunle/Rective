package com.adekunle;

import com.adekunle.controller.ProductController;
import com.adekunle.dto.ProductDto;
import com.adekunle.service.productService.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebFluxTest(ProductController.class)
class ReactiveMongoCrudOperationsApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private ProductService productService;

		@Test
		public void addProduct(){
			Mono<ProductDto> productDtoMono = Mono.just(new ProductDto("102","Shoes",1,25000.00));
			when(productService.saveProduct(productDtoMono)).thenReturn(productDtoMono);
			webTestClient.post().uri("/products/save-product")
					.body(Mono.just(productDtoMono),ProductDto.class)
					.exchange()
					.expectStatus()
					.isOk();
		}

		@Test
		public void getAllProduct(){
			Flux<ProductDto> fluxProductDto = Flux.just(new ProductDto("100","Shoes",1,25000.00),
														new ProductDto("101","TV",1,70000.00),
														new ProductDto("102","Phone",1,125000.00),
														new ProductDto("103","Furniture",1,45000.00),
														new ProductDto("104","Stationary",1,5000.00),
														new ProductDto("105","Props",1,8000.00));
			when(productService.getAllProducts()).thenReturn(fluxProductDto);

			Flux<ProductDto> fluxResponseBody = webTestClient.get()
					.uri("/products/all-products")
					.exchange()
					.expectStatus()
					.isOk()
					.returnResult(ProductDto.class)
					.getResponseBody();

			StepVerifier.create(fluxResponseBody)
					.expectSubscription()
					.expectNext(new ProductDto("100","Shoes",1,25000.00))
					.expectNext(new ProductDto("101","TV",1,70000.00))
					.expectNext(new ProductDto("102","Phone",1,125000.00))
					.expectNext(new ProductDto("103","Furniture",1,45000.00))
					.expectNext(new ProductDto("104","Stationary",1,5000.00))
					.expectNext(new ProductDto("105","Props",1,8000.00))
					.verifyComplete();
		}
		@Test
		public void getProduct(){
			Mono<ProductDto> getProductMono = Mono.just(new ProductDto("103","Furniture",1,45000.00));
			when(productService.getProduct(any())).thenReturn(getProductMono);

			Flux<ProductDto> monoResponseBody = webTestClient.get()
					.uri("/products/get-product/103")
					.exchange()
					.expectStatus()
					.isOk()
					.returnResult(ProductDto.class)
					.getResponseBody();

			StepVerifier.create(monoResponseBody)
					.expectSubscription()
					.expectNextMatches(p -> p.getName().equals("Furniture"))
					.verifyComplete();

		}

		@Test
		public void updateProduct(){
			Mono<ProductDto> productMono = Mono.just(new ProductDto("103","Mac Book Pro",1,550_000.00));
			when(productService.updateProduct(productMono,"103")).thenReturn(productMono);

			webTestClient.put()
					.uri("/products/update-product/103")
					.body(Mono.just(productMono),ProductDto.class)
					.exchange()
					.expectStatus()
					.isOk();
			}

		@Test
		public void getProductInRange(){

		}

		@Test
		public void deleteProduct(){
			given(productService.deleteProduct(any())).willReturn(Mono.empty());

			webTestClient.delete()
					.uri("/products/delete-product/101")
					.exchange()
					.expectStatus()
					.isOk();

		}

}
