package com.mcon152.recipeshare.domain;

import java.util.ArrayList;
import java.util.List;

public class MealPlanComponent implements RecipeComponent {
    private String name;
    private List<RecipeComponent> components = new ArrayList<>();

    public MealPlanComponent(String name) {
        this.name = name;
    }

    public void add(RecipeComponent component) {
        components.add(component);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Recipe> getRecipes() {
        List<Recipe> allRecipes = new ArrayList<>();
        for (RecipeComponent component : components) {
            allRecipes.addAll(component.getRecipes());
        }
        return allRecipes;
    }
}