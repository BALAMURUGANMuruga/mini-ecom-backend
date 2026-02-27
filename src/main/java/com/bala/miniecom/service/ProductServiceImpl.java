package com.bala.miniecom.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bala.miniecom.model.ProductDetails;
import com.bala.miniecom.model.StockUpdateRequest;
import com.bala.miniecom.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductDetails saveProduct(ProductDetails product) {
        return productRepository.save(product);
    }

    @Override
    public List<ProductDetails> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public ProductDetails getProductByItemId(String itemId) {
        return productRepository.findById(itemId)
            .orElseThrow(() -> new RuntimeException("Product not found"));
    }

	@Override
	public List<ProductDetails> saveAll(List<ProductDetails> products) {
		return productRepository.saveAll(products);
	}

	@Override
	public List<ProductDetails> searchByKeyword(String q) {
		 return productRepository.searchByKeyword(q);
	}

	@Override
	public ProductDetails updateStock(String itemId, int stockQty) {

	    if (stockQty < 0) {
	        throw new IllegalArgumentException("Stock cannot be negative");
	    }

	    ProductDetails product = productRepository.findByItemId(itemId)
	        .orElseThrow(() ->
	            new RuntimeException("Product not found: " + itemId));

	    product.setStock(stockQty);
	    return productRepository.save(product);
	}

	@Transactional
	@Override
	public List<ProductDetails> updateStockBulk(List<StockUpdateRequest> requests) {

	    List<ProductDetails> updated = new ArrayList<>();

	    for (StockUpdateRequest req : requests) {

	        ProductDetails product = productRepository.findByItemId(req.getItemId())
	            .orElseThrow(() ->
	                new RuntimeException("Product not found: " + req.getItemId()));

	        if (req.getStockQty() < 0) {
	            throw new IllegalArgumentException("Stock cannot be negative");
	        }

	        product.setStock(req.getStockQty());
	        updated.add(product);
	    }

	    return productRepository.saveAll(updated);
	}


}
