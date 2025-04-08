package org.tqs.deti.ua.MoliceiroUniRestaurants.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Entity(name="reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Status is mandatory")
    @Column(name="status", nullable=false)
    private String status;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @JsonIgnore
    @ManyToOne
    @NotNull(message = "Meal is mandatory")
    @JoinColumn(name = "meal", referencedColumnName = "id")
    private Meal meal;

    public Reservation() {}

    // Constructor to initialize Reservation with Meal and generate code
    public Reservation(Meal meal) {
        this.status = "VALIDA";
        this.meal = meal;
        this.code = generateRandomCode();  // Set code when creating reservation
    }

    // Method to generate a random 6-character alphanumeric code
    private String generateRandomCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int randomIndex = (int) (Math.random() * characters.length());
            code.append(characters.charAt(randomIndex));
        }
        return code.toString();
    }

    // Getter and Setter methods for fields

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    // Overrides for equals and hashCode based on 'code' for uniqueness
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(code, that.code);  // Compare based on 'code'
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);  // Hash based on 'code'
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "code='" + code + '\'' +
                ", status='" + status + '\'' +
                ", meal=" + meal +
                '}';
    }
}
