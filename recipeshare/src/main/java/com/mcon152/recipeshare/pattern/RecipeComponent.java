package com.mcon152.recipeshare.pattern;

import com.mcon152.recipeshare.domain.Recipe;
import java.util.List;

/**
 * The Component interface for the Composite Design Pattern.
 * It defines the shared operations for both leaf nodes (Single Recipes)
 * and composites (Meal Plans).
 */
public interface RecipeComponent {

    /**
     * Retrieves the name of the component.
     * @return String representing the title or name.
     */
    String getName();

    /**
     * Retrieves a flattened list of recipes.
     * For a single recipe, this returns a list containing itself.
     * For a meal plan, this returns all recipes contained within the plan.
     * @return List of Recipe entities.
     */
    List<Recipe> getRecipes();
}