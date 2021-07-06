package com.techelevator.model;

import java.util.List;

public class Recipe {
    private long recipeId;
    private String name;
    private String directions;
    private List<RecipeIngredient> recipeIngredients;
    private long userId;
    private double numberOfServings;

    public Recipe(long recipeId, String name, String directions, List<RecipeIngredient> recipeIngredients, long userId, double numberOfServings) {
        this.recipeId = recipeId;
        this.name = name;
        this.directions = directions;
        this.recipeIngredients = recipeIngredients;
        this.userId = userId;
        this.numberOfServings = numberOfServings;
    }

    public long getRecipeId() {
        return recipeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirections() {
        return directions;
    }

    public List<RecipeIngredient> getRecipeIngredients() {
        return recipeIngredients;
    }

    public long getUserId() {
        return userId;
    }

    public double getNumberOfServings() {
        return numberOfServings;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "recipeId=" + recipeId +
                ", name='" + name + '\'' +
                ", directions='" + directions + '\'' +
                ", recipeIngredients=" + recipeIngredients +
                '}';
    }
}
