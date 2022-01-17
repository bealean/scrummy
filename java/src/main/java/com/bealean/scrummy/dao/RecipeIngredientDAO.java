package com.bealean.scrummy.dao;

import com.bealean.scrummy.model.RecipeIngredient;

import java.util.List;

public interface RecipeIngredientDAO {

    List<RecipeIngredient> getRecipeIngredientsByRecipe(long recipeId);

    void addRecipeIngredient(RecipeIngredient recipeIngredient);

}
