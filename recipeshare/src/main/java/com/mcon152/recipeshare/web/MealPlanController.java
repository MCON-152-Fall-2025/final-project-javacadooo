package com.mcon152.recipeshare.web;

import com.mcon152.recipeshare.domain.MealPlan;
import com.mcon152.recipeshare.domain.Recipe;
import com.mcon152.recipeshare.service.MealPlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meal-plans")
public class MealPlanController {

    private final MealPlanService mealPlanService;

    public MealPlanController(MealPlanService mealPlanService) {
        this.mealPlanService = mealPlanService;
    }

    // POST /api/meal-plans?name=HolidayDinner
    @PostMapping
    public ResponseEntity<MealPlan> create(@RequestParam String name) {
        return ResponseEntity.ok(mealPlanService.createMealPlan(name));
    }

    // POST /api/meal-plans/1/recipes/5
    @PostMapping("/{planId}/recipes/{recipeId}")
    public ResponseEntity<MealPlan> addRecipe(@PathVariable Long planId, @PathVariable Long recipeId) {
        return ResponseEntity.ok(mealPlanService.addRecipeToPlan(planId, recipeId));
    }

    // DELETE /api/meal-plans/1/recipes/5
    @DeleteMapping("/{planId}/recipes/{recipeId}")
    public ResponseEntity<MealPlan> removeRecipe(@PathVariable Long planId, @PathVariable Long recipeId) {
        return ResponseEntity.ok(mealPlanService.removeRecipeFromPlan(planId, recipeId));
    }

    // GET /api/meal-plans/1/flat
    @GetMapping("/{planId}/flat")
    public ResponseEntity<List<Recipe>> getFlattened(@PathVariable Long planId) {
        return ResponseEntity.ok(mealPlanService.getFlattenedRecipes(planId));
    }
}