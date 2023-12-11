package com.codemobiles.stock_backend.controller.api;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.codemobiles.stock_backend.controller.request.ProductRequest;
import com.codemobiles.stock_backend.exception.ProductNotFoundException;
import com.codemobiles.stock_backend.exception.ValidationException;
import com.codemobiles.stock_backend.model.Product;
import com.codemobiles.stock_backend.service.StorageService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/product")
//@CrossOrigin
@Slf4j
public class ProductController {

	private final AtomicLong counter = new AtomicLong();
	private List<Product> products = new ArrayList<>();

	private StorageService storageService;

	ProductController(StorageService storageService) {
		this.storageService = storageService;
	}

	@GetMapping()
	public List<Product> getProducts() {
		log.error("iBlurBlur error");
		log.warn("iBlurBlur warn");
		return products;
	}

	@GetMapping("/{id}")
	public Product getProduct(@PathVariable long id) {
		return products.stream().filter(result -> result.getId() == id).findFirst()
				.orElseThrow(() -> new ProductNotFoundException(id));
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping()
	public Product addProduct(@RequestBody ProductRequest productRequest, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			bindingResult.getFieldErrors().stream().forEach(fieldError -> {
				throw new ValidationException(fieldError.getField() + ": " + fieldError.getDefaultMessage());
			});
		}
		// String fileName = storageService.store(productRequest.getImage());
		Product data = new Product(counter.incrementAndGet(), productRequest.getName(), productRequest.getImage(),
				productRequest.getPrice(), productRequest.getStock());
		products.add(data);
		return data;
	}

	@PutMapping("/{id}")
	public void editProduct(@RequestBody Product product, @PathVariable long id) {
		products.stream().filter(result -> result.getId() == id).findFirst().ifPresentOrElse(result -> {
			result.setName(product.getName());
			result.setImage(product.getImage());
			result.setPrice(product.getPrice());
			result.setStock(product.getStock());
		}, () -> {
			throw new ProductNotFoundException(id);
		});
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void deleteProduct(@PathVariable long id) {
		products.stream().filter(result -> result.getId() == id).findFirst()
				.ifPresentOrElse(result -> products.remove(result), () -> {
					throw new ProductNotFoundException(id);
				});
	}
}
