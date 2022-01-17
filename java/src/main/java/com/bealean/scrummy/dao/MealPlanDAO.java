package com.bealean.scrummy.dao;

import com.bealean.scrummy.exceptions.MealPlanNotFoundException;
import com.bealean.scrummy.exceptions.RecipeNotFoundException;
import com.bealean.scrummy.model.GroceryListItem;
import com.bealean.scrummy.model.MealPlan;

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
