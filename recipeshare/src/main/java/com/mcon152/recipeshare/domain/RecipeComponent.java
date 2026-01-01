package com.mcon152.recipeshare.domain;

import java.util.List;

public interface RecipeComponent {

    // This will return the name of the recipe or the meal plan
    String getName();

    // This will return the final list of recipes (flattened)
    List<Recipe> getRecipes();
}