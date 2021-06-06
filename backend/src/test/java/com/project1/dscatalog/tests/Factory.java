package com.project1.dscatalog.tests;

import com.project1.dscatalog.dto.ProductDTO;
import com.project1.dscatalog.entities.Category;
import com.project1.dscatalog.entities.Product;

import java.time.Instant;

public class Factory {
    public static Product createProduct() {
        Product product = new Product(1L, "Phone", "Good Phone", 800.0, "https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/20-big.jpg", Instant.parse("2020-10-20T03:00:00Z"));
        product.getCategories().add(createCategory());
        return product;
    }

    public static ProductDTO createProductDTO() {
        Product product = createProduct();
        return new ProductDTO(product, product.getCategories());
    }

    public static Category createCategory() {
        return new Category(1L, "Electronics");
    }
}
