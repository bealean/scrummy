package com.bealean.scrummy.dao;

import com.bealean.scrummy.exceptions.RecipeNotFoundException;
import com.bealean.scrummy.model.Meal;

import java.util.List;

public interface MealDAO {

    List<Meal> getMealsByDailyPlan(long dailyPlanId) throws RecipeNotFoundException;

    Meal getMealById(long mealId) throws RecipeNotFoundException;

    Integer addMeal(Meal meal);

    Integer updateMeal(Meal meal);

    Integer deleteMeal(long mealId);

}
