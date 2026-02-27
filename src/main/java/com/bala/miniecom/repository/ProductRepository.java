package com.bala.miniecom.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.bala.miniecom.model.ProductDetails;

public interface ProductRepository extends JpaRepository<ProductDetails, String> {

    Optional<ProductDetails> findByItemId(String itemId);

    List<ProductDetails> findByCategory(String category);

    @Query("""
    		SELECT p FROM ProductDetails p
    		WHERE
    		  (p.name IS NOT NULL AND LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')))
    		  OR (p.description IS NOT NULL AND LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%')))
    		  OR (p.details IS NOT NULL AND LOWER(p.details) LIKE LOWER(CONCAT('%', :keyword, '%')))
    		""")
    		List<ProductDetails> searchByKeyword(@Param("keyword") String keyword);

}
