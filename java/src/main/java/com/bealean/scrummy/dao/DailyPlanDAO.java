package com.bealean.scrummy.dao;

import com.bealean.scrummy.exceptions.DailyPlanNotFoundException;
import com.bealean.scrummy.exceptions.RecipeNotFoundException;
import com.bealean.scrummy.model.DailyPlan;

import java.util.List;

public interface DailyPlanDAO {

    List<DailyPlan> getDailyPlansByMealPlanId(long mealPlanId) throws RecipeNotFoundException;

    DailyPlan getDailyPlanByDailyPlanId(long dpId) throws RecipeNotFoundException, DailyPlanNotFoundException;

    void addDailyPlanToMealPlan(DailyPlan dailyPlan);

    void updateDailyPlan(DailyPlan dailyPlan);

    void deleteDailyPlan(long id);

}
