package com.techelevator.dao;

import com.techelevator.model.RecipeIngredient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JDBCRecipeIngredientDAO implements RecipeIngredientDAO {

    private JdbcTemplate jdbcTemplate;

    public JDBCRecipeIngredientDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<RecipeIngredient> getRecipeIngredientsByRecipe(long recipeId) {
        String sql = "SELECT ri.recipe_ingr_id, ri.recipe_id, i.name, " +
                "ri.mt_quantity, mt.mt_name FROM recipe_ingredients ri " +
                "JOIN ingredients i ON ri.ingredient_id = i.ingredient_id " +
                "JOIN measurement_type mt ON ri.mt_type_id = mt.mt_id WHERE ri.recipe_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, recipeId);
        return mapResultsToRecipeIngredients(result);
    }

    @Override
    public void addRecipeIngredient(RecipeIngredient recipeIngredient) {
        String sqlCheck = "SELECT COALESCE(MAX(ingredient_id), 0) FROM ingredients WHERE lower(name) = ?";
        Long resultIngredientId = jdbcTemplate.queryForObject(sqlCheck, Long.class, recipeIngredient.getIngredientName().toLowerCase());
        String measCheck = "SELECT COALESCE(MAX(mt_id), 0) FROM measurement_type WHERE lower(mt_name) = ?";
        Long resultMeasurementId = jdbcTemplate.queryForObject(measCheck, Long.class, recipeIngredient.getMeasurementType().toLowerCase());
        if (resultIngredientId == 0) {
            resultIngredientId = insertIngredient(recipeIngredient);
        }
        if (resultMeasurementId == 0) {
            resultMeasurementId = insertMeasurement(recipeIngredient);
        }
        String sqlRecIngInsert = "INSERT INTO recipe_ingredients (recipe_id, ingredient_id, mt_type_id, mt_quantity)" +
                " VALUES(?, ?, ?, ?)";
        jdbcTemplate.update(sqlRecIngInsert, recipeIngredient.getRecipeId(), resultIngredientId, resultMeasurementId,
                recipeIngredient.getMeasurementQuantity());
    }

    private RecipeIngredient mapRowToRecipeIngredient(SqlRowSet result) {
        long id = result.getLong("recipe_ingr_id");
        long recipeId = result.getLong("recipe_id");
        String ingredientName = result.getString("name");
        double measurementQty = result.getDouble("mt_quantity");
        String measurementType = result.getString("mt_name");
        return new RecipeIngredient(id, recipeId, ingredientName, measurementQty, measurementType);
    }

    private List<RecipeIngredient> mapResultsToRecipeIngredients(SqlRowSet results) {
        List<RecipeIngredient> recipeIngredients = new ArrayList<>();
        while (results.next()) {
            recipeIngredients.add(mapRowToRecipeIngredient(results));
        }
        return recipeIngredients;
    }

    private Long insertIngredient(RecipeIngredient recipeIngredient) {
        String sqlIngInsert = "INSERT INTO ingredients (name) VALUES (?)";
        jdbcTemplate.update(sqlIngInsert, recipeIngredient.getIngredientName().toLowerCase());
        String sqlIngQuery = "SELECT ingredient_id FROM ingredients WHERE lower(name) = ?";
        return jdbcTemplate.queryForObject(sqlIngQuery, Long.class, recipeIngredient.getIngredientName().toLowerCase());
    }

    private Long insertMeasurement(RecipeIngredient recipeIngredient) {
        String sqlMeasInsert = "INSERT INTO measurement_type (mt_name) VALUES (?)";
        jdbcTemplate.update(sqlMeasInsert, recipeIngredient.getMeasurementType().toLowerCase());
        String sqlMeasQuery = "SELECT mt_id FROM measurement_type WHERE lower(mt_name) = ?";
        return jdbcTemplate.queryForObject(sqlMeasQuery, Long.class, recipeIngredient.getMeasurementType().toLowerCase());
    }

}
