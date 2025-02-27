package org.tqs.deti.ua;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;
import org.json.JSONObject;
import org.json.JSONException;

public class ProductFinderService {
    private static final String API_PRODUCTS = "https://fakestoreapi.com/products";
    private ISimpleHttpClient httpClient;
    private static final Logger LOGGER = Logger.getLogger(ProductFinderService.class.getName());

    // Constructor
    public ProductFinderService(ISimpleHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    // Method to find product details
    public Optional<Product> findProductDetails(Integer id) {
        
        String prod_url = String.format("%s/%d",API_PRODUCTS,id);
        String response = null;

        try {
            response = httpClient.doHttpGet(prod_url);
            if (response == null || response.isEmpty()) {
                LOGGER.warning("Empty or null response received for product ID: " + id);
                return Optional.empty();
            }
        } catch (IOException e) {
            LOGGER.severe("Failed to parse JSON response: " + e.getMessage());
            return Optional.empty();
        }

        try {
            JSONObject json = new JSONObject(response);
            Product product = new Product(
                json.getInt("id"), 
                json.getString("image"), 
                json.getDouble("price"), 
                json.getString("title"), 
                json.getString("description"), 
                json.getString("category")
            );
            return Optional.of(product);
        } catch (JSONException e) {
            LOGGER.severe("Failed to parse JSON response: " + e.getMessage());
            return Optional.empty();
        }
    }
}