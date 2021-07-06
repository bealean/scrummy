package com.techelevator.dao;

import com.techelevator.exceptions.DailyPlanNotFoundException;
import com.techelevator.exceptions.RecipeNotFoundException;
import com.techelevator.model.DailyPlan;
import com.techelevator.model.Meal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JDBCDailyPlanDAO implements DailyPlanDAO {

    @Autowired
    MealDAO mealDAO;

    private JdbcTemplate jdbcTemplate;

    public JDBCDailyPlanDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<DailyPlan> getDailyPlansByMealPlanId(long mealPlanId) throws RecipeNotFoundException {
        String sql = "SELECT dp.dp_id, dp.meal_plan_id, w.weekday FROM daily_plan dp " +
                "JOIN weekday w ON dp.weekday_id = w.weekday_id " +
                "WHERE meal_plan_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, mealPlanId);

        return mapResultsToDailyPlanList(results);
    }

    @Override
    public DailyPlan getDailyPlanByDailyPlanId(long dpId) throws RecipeNotFoundException, DailyPlanNotFoundException {
        String sql = "SELECT * FROM daily_plan WHERE dp_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, dpId);

        if (result.next()) {
            return mapRowToDailyPlan(result);
        } else {
            throw new DailyPlanNotFoundException();
        }
    }

    @Override
    public void addDailyPlanToMealPlan(DailyPlan dailyPlan) {
        String weekday = dailyPlan.getWeekday();
        String sqlWeekday = "SELECT weekday_id FROM weekday WHERE weekday = ?";
        Long weekdayId = jdbcTemplate.queryForObject(sqlWeekday, Long.class, weekday);
        String sql = "INSERT INTO daily_plan (meal_plan_id, weekday_id) VALUES(?, ?)";
        jdbcTemplate.update(sql, dailyPlan.getMealPlanId(), weekdayId);

        String sqlSelect = "SELECT dp_id FROM daily_plan WHERE meal_plan_id = ? AND weekday_id = ?";
        Long dailyPlanId = jdbcTemplate.queryForObject(sqlSelect, Long.class,
                dailyPlan.getMealPlanId(), weekdayId);
        List<Meal> listOfMeals = dailyPlan.getMeals();
        for (Meal meal : listOfMeals) {
            meal.setDailyPlanId(dailyPlanId);
            mealDAO.addMeal(meal);
        }
    }

    @Override
    public void updateDailyPlan(DailyPlan dailyPlan) {
        List<Meal> listOfMeals = dailyPlan.getMeals();
        for (Meal meal : listOfMeals) {
            mealDAO.updateMeal(meal);
        }
        String weekday = dailyPlan.getWeekday();
        String sqlWeekday = "SELECT weekday_id FROM weekday WHERE weekday = ?";
        Long weekdayId = jdbcTemplate.queryForObject(sqlWeekday, Long.class, weekday);
        String sql = "UPDATE daily_plan SET meal_plan_id = ?, weekday_id = ? WHERE dp_id = ?";
        jdbcTemplate.update(sql, dailyPlan.getMealPlanId(), weekdayId, dailyPlan.getDpId());
    }

    @Override
    public void deleteDailyPlan(long dpId) {
        String sqlSelect = "SELECT meal_id FROM meals WHERE dp_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelect, dpId);
        List<Long> mealIdsList = mapRowToMealIds(results);
        for (Long mealId : mealIdsList) {
            mealDAO.deleteMeal(mealId);
        }
        String sql = "DELETE FROM daily_plan WHERE dp_id = ?";
        jdbcTemplate.update(sql, dpId);
    }

    private DailyPlan mapRowToDailyPlan(SqlRowSet result) throws RecipeNotFoundException {
        long dpId = result.getLong("dp_id");
        long mealPlanId = result.getLong("meal_plan_id");
        String weekday = result.getString("weekday");
        List<Meal> listOfMeals = mealDAO.getMealsByDailyPlan(dpId);
        return new DailyPlan(dpId, weekday, listOfMeals, mealPlanId);
    }

    private List<DailyPlan> mapResultsToDailyPlanList(SqlRowSet results) throws RecipeNotFoundException {
        List<DailyPlan> dailyPlanList = new ArrayList<>();
        while (results.next()) {
            dailyPlanList.add(mapRowToDailyPlan(results));
        }
        return dailyPlanList;
    }

    private List<Long> mapRowToMealIds(SqlRowSet result) {
        List<Long> mealIdList = new ArrayList<>();
        while (result.next()) {
            long mealId = result.getLong("meal_id");
            mealIdList.add(mealId);
        }
        return mealIdList;
    }
}
