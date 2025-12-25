package com.mcon152.recipeshare.domain;

import java.util.Collections;
import java.util.List;

public class SingleRecipeComponent implements RecipeComponent {
    private Recipe recipe;

    public SingleRecipeComponent(Recipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public String getName() {
        return recipe.getName(); // This now works because of the getName() alias in Recipe
    }

    @Override
    public List<Recipe> getRecipes() {
        return Collections.singletonList(this.recipe);
    }
}