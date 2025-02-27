package org.tqs.deti.ua;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ProductFinderServiceIT {
    TqsBasicHttpClient httpClient;
    ProductFinderService product_finder;

    @BeforeEach
    public void setup() throws IOException {
        httpClient = new TqsBasicHttpClient();
        product_finder = new ProductFinderService(httpClient);
    }

    @Test
    public void getProductTest() throws IOException {
        Product product = product_finder.findProductDetails(3).get();
        assertEquals(3, product.getId());
        assertEquals("Mens Cotton Jacket", product.getTitle());
    }

    @Test
    public void getNonExistingProductTest() throws IOException {
        Optional<Product> product = product_finder.findProductDetails(300);
        assertTrue(product.isEmpty());
    }
}
