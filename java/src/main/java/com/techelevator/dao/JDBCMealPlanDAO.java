package com.techelevator.dao;

import com.techelevator.exceptions.MealPlanNotFoundException;
import com.techelevator.exceptions.RecipeNotFoundException;
import com.techelevator.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JDBCMealPlanDAO implements MealPlanDAO {

    @Autowired
    DailyPlanDAO dailyPlanDAO;

    private JdbcTemplate jdbcTemplate;

    public JDBCMealPlanDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<MealPlan> getAllMealPlansByUserId(long userId) throws RecipeNotFoundException {
        String sql = "SELECT * FROM meal_plan WHERE user_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, userId);
        return mapResultsToMealPlanList(result);
    }

    @Override
    public MealPlan getMealPlanByName(long userId, String mealName) throws RecipeNotFoundException, MealPlanNotFoundException {
        String sql = "SELECT * FROM meal_plan WHERE mp_name = ? AND user_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, mealName, userId);
        if (result.next()) {
            return mapRowToMealPlan(result);
        } else {
            throw new MealPlanNotFoundException();
        }
    }

    @Override
    public MealPlan getMealPlanById(long mealPlanId) throws RecipeNotFoundException, MealPlanNotFoundException {
        String sql = "SELECT * FROM meal_plan WHERE meal_plan_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, mealPlanId);
        if (result.next()) {
            return mapRowToMealPlan(result);
        } else {
            throw new MealPlanNotFoundException();
        }
    }

    @Override
    public List<GroceryListItem> getIngredientsByMealPlanId(long mealPlanId) {
        String sql = "SELECT DISTINCT i.name AS \"name\" " +
                "FROM ingredients i " +
                "JOIN recipe_ingredients ri ON i.ingredient_id = ri.ingredient_id " +
                "JOIN recipes r ON ri.recipe_id = r.recipe_id " +
                "JOIN meals_recipes mr ON r.recipe_id = mr.recipe_id " +
                "JOIN meals m ON mr.meal_id = m.meal_id " +
                "JOIN daily_plan d ON m.dp_id = d.dp_id " +
                "WHERE d.meal_plan_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, mealPlanId);
        List<GroceryListItem> ingredients = new ArrayList<>();
        while (result.next()) {
            String ingredient = result.getString("name");
            GroceryListItem groceryListItem = new GroceryListItem(ingredient, false);
            ingredients.add(groceryListItem);
        }
        return ingredients;
    }

    @Override
    public Long addMealPlan(String mealPlanName, long userId) {
        String sql = "INSERT INTO meal_plan(user_id, mp_name) VALUES(?, ?)";
        jdbcTemplate.update(sql, userId, mealPlanName);
        String sqlSelect = "SELECT meal_plan_id FROM meal_plan WHERE user_id = ? AND mp_name = ?";
        Long mealPlanId = jdbcTemplate.queryForObject(sqlSelect, Long.class, userId,
                mealPlanName);
        String[] weekdays = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        String[] mealTypes = {"Breakfast", "Snack", "Lunch", "Snack", "Dinner", "Dessert"};
        List<Meal> meals = new ArrayList<>();
        List<Recipe> recipes = new ArrayList<>();
        for (String mealType : mealTypes) {
            Meal meal = new Meal(0, 0, mealType, recipes);
            meals.add(meal);
        }
        for (String weekday : weekdays) {
            DailyPlan dailyPlan = new DailyPlan(0, weekday, meals, mealPlanId);
            dailyPlanDAO.addDailyPlanToMealPlan(dailyPlan);
        }
        return mealPlanId;
    }

    @Override
    public void updateMealPlan(MealPlan mealPlan) {
        List<DailyPlan> dailyPlanList = mealPlan.getDailyPlanList();
        for (DailyPlan dailyPlan : dailyPlanList) {
            dailyPlanDAO.updateDailyPlan(dailyPlan);
        }
        String sql = "UPDATE meal_plan SET mp_name = ? WHERE meal_plan_id = ?";
        jdbcTemplate.update(sql, mealPlan.getMealPlanName(), mealPlan.getMealPlanId());
    }

    @Override
    public void deleteMealPlan(long mealPlanId) {
        String sqlSelect = "SELECT dp_id FROM daily_plan WHERE meal_plan_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelect, mealPlanId);
        List<Long> dailyPlanIdList = mapRowToDailyPlanIds(results);
        for (Long dailyPlanId : dailyPlanIdList) {
            dailyPlanDAO.deleteDailyPlan(dailyPlanId);
        }
        String sql = "DELETE FROM meal_plan WHERE meal_plan_id = ?";
        jdbcTemplate.update(sql, mealPlanId);
    }

    private MealPlan mapRowToMealPlan(SqlRowSet result) throws RecipeNotFoundException {
        long mealPlanId = result.getLong("meal_plan_id");
        long userId = result.getLong("user_id");
        String mealPlanName = result.getString("mp_name");
        List<DailyPlan> dailyPlanList = dailyPlanDAO.getDailyPlansByMealPlanId(mealPlanId);
        return new MealPlan(mealPlanId, userId, mealPlanName, dailyPlanList);
    }

    private List<MealPlan> mapResultsToMealPlanList(SqlRowSet results) throws RecipeNotFoundException {
        List<MealPlan> mealPlanList = new ArrayList<>();
        while (results.next()) {
            mealPlanList.add(mapRowToMealPlan(results));
        }
        return mealPlanList;
    }

    private List<Long> mapRowToDailyPlanIds(SqlRowSet result) {
        List<Long> dailyPlanIdList = new ArrayList<>();
        while (result.next()) {
            long dpId = result.getLong("dp_id");
            dailyPlanIdList.add(dpId);
        }
        return dailyPlanIdList;
    }

}
