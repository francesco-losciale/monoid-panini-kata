package com.panino;

import static java.lang.Integer.max;

public enum DietCompatibility {
    NO_VALUE,
    VEGAN,
    VEGETARIAN,
    PESCETARIAN,
    OMNIVORE
    ;

    public DietCompatibility combine(DietCompatibility dietCompatibility) {
        return values()[
                    max(this.ordinal(), dietCompatibility.ordinal())
                ];
    }

}
