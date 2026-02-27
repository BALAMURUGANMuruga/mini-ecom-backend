package com.bala.miniecom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bala.miniecom.model.ProductDetails;
import com.bala.miniecom.model.StockUpdateRequest;
import com.bala.miniecom.service.ProductService;

@RestController
@RequestMapping("/api/products")
@CrossOrigin
public class Productontroller {
	
	 @Autowired
	    private ProductService productService;

	    @PostMapping
	    public ProductDetails createProduct(@RequestBody ProductDetails product) {
	        return productService.saveProduct(product);
	    }
	    
	    @PostMapping("/bulk")
	    public List<ProductDetails> saveAll(@RequestBody List<ProductDetails> products) {
	        return productService.saveAll(products);
	    }
	    
	   

	    @GetMapping
	    public List<ProductDetails> getAllProducts() {
	        return productService.getAllProducts();
	    }

	    @GetMapping("/{itemId}")
	    public ProductDetails getProduct(@PathVariable String itemId) {
	        return productService.getProductByItemId(itemId);
	    }
	  
	    @GetMapping("/search")
	    public List<ProductDetails> search(@RequestParam String q) {
	        return productService.searchByKeyword(q);
	    }
	    
	    @PatchMapping("/stock/bulk")
	    public List<ProductDetails> updateBulkStock(
	            @RequestBody List<StockUpdateRequest> requests) {

	        return productService.updateStockBulk(requests);
	    }

}
