package com.panino;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.panino.DietCompatibility.NO_VALUE;
import static com.panino.DietCompatibility.OMNIVORE;
import static com.panino.DietCompatibility.PESCETARIAN;
import static com.panino.DietCompatibility.VEGAN;
import static com.panino.DietCompatibility.VEGETARIAN;
import static com.panino.Food.builder;
import static org.assertj.core.api.Assertions.assertThat;

public class FoodTest {

    private final FoodBuilderForTest mapper = new FoodBuilderForTest();

    @ParameterizedTest
    @CsvSource({
            "salad, cheese, tomato, bread, ham, fish, OMNIVORE",
            "salad, cheese, tomato, bread, ham, nothing, OMNIVORE",
            "salad, cheese, tomato, bread, nothing, nothing, VEGETARIAN",
            "salad, nothing, tomato, bread, nothing, nothing, VEGAN",
            "salad, cheese, tomato, bread, nothing, fish, PESCETARIAN",
    })
    void testPaninoWithThreeIngredientsOnItsDietCompatibility(String ingredient1,
                                                              String ingredient2,
                                                              String ingredient3,
                                                              String ingredient4,
                                                              String ingredient5,
                                                              String ingredient6,
                                                              String expectation) {
        Food panino = createPanino(ingredient1, ingredient2, ingredient3, ingredient4, ingredient5, ingredient6);
        assertThat(panino.getDietCompatibility()).isEqualTo(DietCompatibility.valueOf(expectation));
    }

    @Test
    void testNutritionalValuesAddition() {
        final Food panino = createPanino("salad", "cheese", "tomato");
        final Food salad = mapper.createFood("salad");
        final Food cheese = mapper.createFood("cheese");
        final Food tomato = mapper.createFood("tomato");
        assertThat(panino.getFat()).isEqualTo(salad.getFat() + cheese.getFat() + tomato.getFat());
        assertThat(panino.getSalt()).isEqualTo(salad.getSalt() + cheese.getSalt() + tomato.getSalt());
        assertThat(panino.getCalories()).isEqualTo(salad.getCalories() + cheese.getCalories() + tomato.getCalories());
    }

    private Food createPanino(String... ingredients) {
        Food noFood = Food.builder().dietCompatibility(NO_VALUE).build();
        return Arrays.stream(ingredients)
                .map(ingredient -> mapper.createFood(ingredient))
                .reduce(noFood, (food1, food2) -> food1.combine(food2));
    }

    private class FoodBuilderForTest {
        private Map<String, Food> internalMap = new HashMap();
        {
            internalMap.put("nothing", builder().dietCompatibility(NO_VALUE).build());
            internalMap.put("salad", builder().dietCompatibility(VEGAN).fat(0).salt(0).calories(50).build());
            internalMap.put("cheese", builder().dietCompatibility(VEGETARIAN).fat(80).salt(0).calories(20000).build());
            internalMap.put("tomato", builder().dietCompatibility(VEGAN).fat(1).salt(3).calories(100).build());
            internalMap.put("bread", builder().dietCompatibility(VEGAN).fat(2).salt(0.2).calories(100000).build());
            internalMap.put("ham", builder().dietCompatibility(OMNIVORE).fat(150).salt(0.1).calories(300000).build());
            internalMap.put("fish", builder().dietCompatibility(PESCETARIAN).fat(2).salt(0.2).calories(100000).build());
        }
        public Food createFood(String ingredient) {
            return internalMap.get(ingredient);
        }
    }
}
