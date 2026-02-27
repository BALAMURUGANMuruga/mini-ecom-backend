package com.bala.miniecom.service;

import java.util.List;
import java.util.Map;

import com.bala.miniecom.model.ProductDetails;
import com.bala.miniecom.model.StockUpdateRequest;

public interface ProductService {
	
	ProductDetails saveProduct(ProductDetails product);
	ProductDetails updateStock( String itemId,int stockQty);
	List<ProductDetails> updateStockBulk( List<StockUpdateRequest> requests);

    List<ProductDetails> getAllProducts();

    ProductDetails getProductByItemId(String itemId);
    
    List<ProductDetails> saveAll(List<ProductDetails> products);

	List<ProductDetails> searchByKeyword(String q);
    

}
