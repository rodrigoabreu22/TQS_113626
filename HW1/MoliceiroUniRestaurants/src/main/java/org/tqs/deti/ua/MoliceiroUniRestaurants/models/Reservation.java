package org.tqs.deti.ua.MoliceiroUniRestaurants.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

@Entity(name="reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long code;

    @NotBlank(message = "Status is mandatory")
    @Column(name="status", nullable=false)
    private String status;

    @ManyToOne
    @NotNull(message = "Meal is mandatory")
    @JoinColumn(name = "meal", referencedColumnName = "id")
    private Meal meal;

    public Reservation() {
        this.status = "VALIDA";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return code == that.code;
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "code=" + code +
                ", status='" + status + '\'' +
                ", meal=" + meal +
                '}';
    }
}
