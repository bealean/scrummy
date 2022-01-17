package com.bealean.scrummy.dao;

import com.bealean.scrummy.exceptions.RecipeNotFoundException;
import com.bealean.scrummy.model.Recipe;

import java.util.List;

public interface RecipeDAO {

    List<Recipe> getMyRecipes(long userId);

    Recipe getRecipeById(long recipeId, long userId) throws RecipeNotFoundException;

    Recipe getRecipeById(long recipeId) throws RecipeNotFoundException;

    void addRecipe(Recipe recipe);

    void addRecipeToMeal(long recipeId, long mealId);

    void updateRecipe(Recipe recipe);

    void removeRecipeFromMeal(long recipeId, long mealId);

    void deleteRecipe(long recipeId);

}
