package com.techelevator.model;

public class RecipeIngredient {

    private long recipeIngredientId;
    private long recipeId;
    private String ingredientName;
    private double measurementQuantity;
    private String measurementType;

    public RecipeIngredient(long recipeIngredientId, long recipeId, String ingredientName, double measurementQuantity, String measurementType) {
        this.recipeIngredientId = recipeIngredientId;
        this.recipeId = recipeId;
        this.ingredientName = ingredientName;
        this.measurementQuantity = measurementQuantity;
        this.measurementType = measurementType;
    }

    public long getRecipeIngredientId() {
        return recipeIngredientId;
    }

    public long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(long recipeId) {
        this.recipeId = recipeId;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public double getMeasurementQuantity() {
        return measurementQuantity;
    }

    public String getMeasurementType() {
        return measurementType;
    }

    @Override
    public String toString() {
        return "RecipeIngredient{" +
                "recipeIngredientId=" + recipeIngredientId +
                ", recipeId=" + recipeId +
                ", ingredientName='" + ingredientName + '\'' +
                ", measurementQuantity=" + measurementQuantity +
                ", measurementType='" + measurementType + '\'' +
                '}';
    }
}
