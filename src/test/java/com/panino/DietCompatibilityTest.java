package com.panino;

import org.junit.jupiter.api.Test;

import static com.panino.DietCompatibility.NO_VALUE;
import static com.panino.DietCompatibility.OMNIVORE;
import static com.panino.DietCompatibility.PESCETARIAN;
import static com.panino.DietCompatibility.VEGAN;
import static com.panino.DietCompatibility.VEGETARIAN;
import static org.assertj.core.api.Assertions.assertThat;

class DietCompatibilityTest {

    @Test
    void testDietCompatibilityCombine() {
        assertThat(VEGETARIAN.combine(VEGETARIAN)).isEqualTo(VEGETARIAN);
        assertThat(VEGETARIAN.combine(NO_VALUE)).isEqualTo(VEGETARIAN);
        assertThat(NO_VALUE.combine(VEGETARIAN)).isEqualTo(VEGETARIAN);
        assertThat(OMNIVORE.combine(VEGETARIAN)).isEqualTo(OMNIVORE);
        assertThat(VEGETARIAN.combine(OMNIVORE)).isEqualTo(OMNIVORE);
        assertThat(VEGAN.combine(OMNIVORE)).isEqualTo(OMNIVORE);
        assertThat(OMNIVORE.combine(VEGAN)).isEqualTo(OMNIVORE);
        assertThat(VEGETARIAN.combine(VEGAN)).isEqualTo(VEGETARIAN);
        assertThat(VEGAN.combine(VEGETARIAN)).isEqualTo(VEGETARIAN);
        assertThat(PESCETARIAN.combine(VEGETARIAN)).isEqualTo(PESCETARIAN);
        assertThat(VEGETARIAN.combine(PESCETARIAN)).isEqualTo(PESCETARIAN);
        assertThat(VEGAN.combine(PESCETARIAN)).isEqualTo(PESCETARIAN);
        assertThat(PESCETARIAN.combine(VEGAN)).isEqualTo(PESCETARIAN);
        assertThat(PESCETARIAN.combine(OMNIVORE)).isEqualTo(OMNIVORE);
        assertThat(OMNIVORE.combine(PESCETARIAN)).isEqualTo(OMNIVORE);
    }
}