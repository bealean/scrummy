package com.techelevator.dao;

import com.techelevator.model.RecipeIngredient;

import java.util.List;

public interface RecipeIngredientDAO {

    List<RecipeIngredient> getRecipeIngredientsByRecipe(long recipeId);

    void addRecipeIngredient(RecipeIngredient recipeIngredient);

}
