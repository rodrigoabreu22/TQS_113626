package org.tqs.deti.ua;

public class Product {
    private Integer id;
    private String image;
    private String description;
    private Double price;
    private String title;
    private String category;

    public Product(Integer id, String image, Double price, String title, String description, String category) {
        this.id = id;
        this.image = image;
        this.description = description;
        this.price = price;
        this.title = title;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    
}
