package com.mcon152.recipeshare.service;

import com.mcon152.recipeshare.domain.MealPlan;
import com.mcon152.recipeshare.domain.Recipe;
import com.mcon152.recipeshare.pattern.MealPlanComponent;
import com.mcon152.recipeshare.pattern.SingleRecipeComponent;
import com.mcon152.recipeshare.repository.MealPlanRepository;
import com.mcon152.recipeshare.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MealPlanService {

    private final MealPlanRepository mealPlanRepository;
    private final RecipeRepository recipeRepository;

    @Autowired
    public MealPlanService(MealPlanRepository mealPlanRepository, RecipeRepository recipeRepository) {
        this.mealPlanRepository = mealPlanRepository;
        this.recipeRepository = recipeRepository;
    }

    public MealPlan createMealPlan(String name) {
        MealPlan plan = new MealPlan(name);
        return mealPlanRepository.save(plan);
    }

    @Transactional
    public MealPlan addRecipeToPlan(Long planId, Long recipeId) {
        MealPlan plan = mealPlanRepository.findById(planId)
                .orElseThrow(() -> new RuntimeException("Meal Plan not found"));
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        // Avoid duplicates if desired
        if (!plan.getRecipes().contains(recipe)) {
            plan.getRecipes().add(recipe);
        }
        return mealPlanRepository.save(plan);
    }

    @Transactional
    public MealPlan removeRecipeFromPlan(Long planId, Long recipeId) {
        MealPlan plan = mealPlanRepository.findById(planId)
                .orElseThrow(() -> new RuntimeException("Meal Plan not found"));
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        plan.getRecipes().remove(recipe);
        return mealPlanRepository.save(plan);
    }

    /**
     * Bridges the DB entity with the Composite Pattern logic.
     * This allows us to return a flattened list of recipes.
     */
    public List<Recipe> getFlattenedRecipes(Long planId) {
        MealPlan planEntity = mealPlanRepository.findById(planId)
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        // 1. Create the Composite Root
        MealPlanComponent compositePlan = new MealPlanComponent(planEntity.getName());

        // 2. Add Leaves (Single Recipes)
        for (Recipe r : planEntity.getRecipes()) {
            compositePlan.add(new SingleRecipeComponent(r));
        }

        // 3. Return flattened list via the Interface
        return compositePlan.getRecipes();
    }
}