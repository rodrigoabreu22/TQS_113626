package org.tqs.deti.ua.MoliceiroUniRestaurants.dto;

public class ReservationDTO {

    private String code;
    private String status;
    private Long mealId;

    public ReservationDTO(String code, String status, long mealId) {
        this.code = code;
        this.status = status;
        this.mealId = mealId;
    }

    // Getters and Setters
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

    public Long getMealId() {
        return mealId;
    }

    public void setMealId(Long mealId) {
        this.mealId = mealId;
    }
}
