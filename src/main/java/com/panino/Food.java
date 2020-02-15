package com.panino;

import lombok.Builder;

@Builder
public class Food {

    private DietCompatibility dietCompatibility;
    private double fat;
    private double salt;
    private double calories;

    public double getFat() {
        return fat;
    }

    public double getSalt() {
        return salt;
    }

    public double getCalories() {
        return calories;
    }

    public DietCompatibility getDietCompatibility() {
        return dietCompatibility;
    }

    public Food combine(Food food) {
        return new Food(food.dietCompatibility.combine(this.dietCompatibility),
                food.getFat() + this.getFat(),
                food.getSalt() + this.getSalt(),
                food.getCalories() + this.getCalories());
    }
}
