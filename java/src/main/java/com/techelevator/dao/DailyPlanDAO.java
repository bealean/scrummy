package com.techelevator.dao;

import com.techelevator.exceptions.DailyPlanNotFoundException;
import com.techelevator.exceptions.RecipeNotFoundException;
import com.techelevator.model.DailyPlan;

import java.util.List;

public interface DailyPlanDAO {

    List<DailyPlan> getDailyPlansByMealPlanId(long mealPlanId) throws RecipeNotFoundException;

    DailyPlan getDailyPlanByDailyPlanId(long dpId) throws RecipeNotFoundException, DailyPlanNotFoundException;

    void addDailyPlanToMealPlan(DailyPlan dailyPlan);

    void updateDailyPlan(DailyPlan dailyPlan);

    void deleteDailyPlan(long id);

}
