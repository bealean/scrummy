package com.techelevator.dao;

import com.techelevator.exceptions.RecipeNotFoundException;
import com.techelevator.model.Meal;

import java.util.List;

public interface MealDAO {

    List<Meal> getMealsByDailyPlan(long dailyPlanId) throws RecipeNotFoundException;

    Meal getMealById(long mealId) throws RecipeNotFoundException;

    Integer addMeal(Meal meal);

    Integer updateMeal(Meal meal);

    Integer deleteMeal(long mealId);

}
