package com.panino;

import lombok.Builder;

@Builder
public class Food {

    private DietCompatibility dietCompatibility;

    public DietCompatibility getDietCompatibility() {
        return dietCompatibility;
    }

    public Food combine(Food food) {
        return new Food(food.dietCompatibility.combine(this.dietCompatibility));
    }
}
