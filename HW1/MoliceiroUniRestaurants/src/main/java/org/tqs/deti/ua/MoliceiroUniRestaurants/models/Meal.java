package org.tqs.deti.ua.MoliceiroUniRestaurants.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name="meal")
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Name is mandatory")
    @Column(name="name", nullable=false)
    private String name;

    @NotBlank(message = "´Type is mandatory (lunch/dinner)")
    @Column(name="type", nullable=false)
    private String type;

    @NotNull(message = "´Reservation limit is mandatory.")
    @Column(name="reservation_limit", nullable=false)
    private int reservationLimit;

    @NotNull(message = "Date is mandatory")
    @Column(name = "date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @ManyToOne
    @NotNull(message = "Restaurant is mandatory")
    @JoinColumn(name = "restaurant", referencedColumnName = "id")
    private Restaurant restaurant;

    @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL, orphanRemoval = true)  // Fix: "meal" instead of "reservation"
    private List<Reservation> reservations = new ArrayList<>();

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getReservationLimit() {
        return reservationLimit;
    }

    public void setReservationLimit(int reservationLimit) {
        this.reservationLimit = reservationLimit;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meal meal = (Meal) o;
        return id == meal.id &&
                reservationLimit == meal.reservationLimit &&
                Objects.equals(name, meal.name) &&
                Objects.equals(type, meal.type) &&
                Objects.equals(date, meal.date) &&
                Objects.equals(restaurant, meal.restaurant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, reservationLimit, date, restaurant);
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", reservationLimit=" + reservationLimit +
                ", date=" + date +
                ", restaurant=" + restaurant +
                '}';
    }
}
