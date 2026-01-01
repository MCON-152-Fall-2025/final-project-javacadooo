package com.mcon152.recipeshare.domain;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class MealPlanCompositeTest {

    @Test
    void testFlatteningNestedMealPlans() {
        // 1.   Setup concrete recipes using the fix constructors
        BasicRecipe eggs = new BasicRecipe();
        eggs.setTitle("Scrambled Eggs");

        DairyRecipe yogurt = new DairyRecipe();
        yogurt.setTitle("Greek Yogurt");

        // 2. Create components (Leaves)
        SingleRecipeComponent leafEggs = new SingleRecipeComponent(eggs);
        SingleRecipeComponent leafYogurt = new SingleRecipeComponent(yogurt);

        // 3. Create a Nested Structure (Composite)
        MealPlanComponent breakfastSweets = new MealPlanComponent("Sweets");
        breakfastSweets.add(leafYogurt);

        MealPlanComponent fullBreakfast = new MealPlanComponent("Full Breakfast");
        fullBreakfast.add(leafEggs);
        fullBreakfast.add(breakfastSweets); // Nesting the composite

        // 4. Verify the flattening logic
        List<Recipe> result = fullBreakfast.getRecipes();

        assertEquals(2, result.size(), "Should find 2 recipes total including nested ones");
        assertTrue(result.contains(eggs));
        assertTrue(result.contains(yogurt));
        assertEquals("Full Breakfast", fullBreakfast.getName());
    }

    @Test
    void testEmptyPlan() {
        MealPlanComponent empty = new MealPlanComponent("Empty");
        assertTrue(empty.getRecipes().isEmpty());
    }
}