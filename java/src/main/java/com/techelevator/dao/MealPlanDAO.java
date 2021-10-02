package com.techelevator.dao;

import com.techelevator.exceptions.MealPlanNotFoundException;
import com.techelevator.exceptions.RecipeNotFoundException;
import com.techelevator.model.GroceryListItem;
import com.techelevator.model.MealPlan;

import java.util.List;

public interface MealPlanDAO {

    List<MealPlan> getAllMealPlansByUserId(long userId) throws RecipeNotFoundException;

    MealPlan getMealPlanByName(long userId, String mealName) throws RecipeNotFoundException, MealPlanNotFoundException;

    MealPlan getMealPlanById(long mealPlanId) throws RecipeNotFoundException, MealPlanNotFoundException;

    List<GroceryListItem> getIngredientsByMealPlanIds(long[] mealPlanIds);

    Long addMealPlan(String mealPlanName, long userId);

    void updateMealPlan(MealPlan mealPlan);

    void deleteMealPlan(long mealPlanId);

}
