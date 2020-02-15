import com.panino.DietCompatibility;
import com.panino.Food;
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

public class PaninoTest {

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

    private Food createPanino(String... ingredients) {
        Food noFood = Food.builder().dietCompatibility(NO_VALUE).build();
        return Arrays.stream(ingredients)
                .map(ingredient -> mapper.convert(ingredient))
                .reduce(noFood, (food1, food2) -> food1.combine(food2));
    }

    private class FoodBuilderForTest {
        private Map<String, Food> internalMap = new HashMap();
        {
            internalMap.put("nothing", builder().dietCompatibility(NO_VALUE).build());
            internalMap.put("salad", builder().dietCompatibility(VEGAN).build());
            internalMap.put("cheese", builder().dietCompatibility(VEGETARIAN).build());
            internalMap.put("tomato", builder().dietCompatibility(VEGAN).build());
            internalMap.put("bread", builder().dietCompatibility(VEGAN).build());
            internalMap.put("ham", builder().dietCompatibility(OMNIVORE).build());
            internalMap.put("fish", builder().dietCompatibility(PESCETARIAN).build());
        }
        public Food convert(String ingredient) {
            return internalMap.get(ingredient);
        }
    }
}
