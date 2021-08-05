<template>
  <div>
    <h1 id="meal-plan-details-header">
      Meal Plan Details: {{ mealPlan.mealPlanName }}
    </h1>
    <div v-if="mealPlan.mealPlanId" id="div-for-buttons-meal-plan-details">
      <router-link
        v-bind:to="{
          name: 'groceries',
          params: { mealPlanId: mealPlan.mealPlanId },
        }"
        tag="button"
        >Create Grocery List</router-link
      >
      <router-link
        v-bind:to="{
          name: 'myMealPlans',
          params: { mealPlanId: mealPlan.mealPlanId },
        }"
        tag="button"
        >Go Back to Meal Plans</router-link
      >
    </div>
    <div
      class="weekday-div"
      v-for="dailyPlan in mealPlan.dailyPlanList"
      v-bind:key="dailyPlan.dpId"
    >
      {{ dailyPlan.weekday }}

      <div
        class="meal-type-div"
        v-for="meal in dailyPlan.meals"
        v-bind:key="meal.mealId"
      >
        <div class="meal-and-add-recipe-row">
          <router-link
            class="link underlined-link"
            v-bind:to="{
              name: 'mealDetails',
              params: { mealId: meal.mealId },
            }"
            >{{ meal.mealType }}</router-link
          >
          &nbsp;
          <router-link
            tag="button"
            class="add-recipe-btn"
            v-bind:to="{
              name: 'mealDetails',
              params: { mealPlanId: dailyPlan.mealPlanId, mealId: meal.mealId },
            }"
          >
            Add Recipe</router-link
          >
        </div>
        <div
          class="recipe-link-and-remove-button-div"
          v-for="recipe in meal.recipes"
          v-bind:key="recipe.recipeId"
        >
          <router-link
            class="link underlined-link"
            v-bind:to="{
              name: 'myRecipeDetails',
              params: { id: recipe.recipeId },
            }"
          >
            {{ recipe.name }}</router-link
          >&nbsp;
          <button
            class="remove-recipe-btn"
            v-on:click.prevent="deleteRecipe(meal.mealId, recipe.recipeId)"
          >
            Remove
          </button>
        </div>
      </div>
    </div>
    <br />
  </div>
</template>

<script>
import mealPlanService from "../services/MealPlanService.js";
import recipeService from "../services/RecipeService.js";
export default {
  data() {
    return {
      mealPlan: {},
      user: {
        userName: this.$store.state.user.username,
        id: this.$store.state.user.id,
      },
    };
  },
  methods: {
    deleteRecipe(mealId, recipeId) {
      recipeService.removeRecipeFromMeal(mealId, recipeId).then(() => {
        this.$router.go();
      });
    },
  },
  created() {
    mealPlanService
      .getMealPlanById(this.$route.params.mealPlanId)
      .then((response) => {
        this.mealPlan = response.data;
      });
  },
};
</script>

<style scoped>
#meal-plan-details-header {
  font-size: 40px;
}

#div-for-buttons-meal-plan-details {
  margin-bottom: 20px;
}
.weekday-div {
  font-size: 30px;
  background: #94c973;
  border-radius: 25px;
  text-align: center;
  padding-bottom: 5px;
  margin: auto;
  margin-top: 10px;
  color: #1a4314;
  font-weight: bold;
  width: 50%;
  min-width: fit-content;
}

.meal-type-div {
  background-color: rgba(255, 253, 253, 0.3);
  font-size: 20px;
  border-radius: 25px;
  margin: 5px 10px 5px 10px;
  text-align: center;
  padding: 5px 30px 5px 30px;
}

.meal-and-add-recipe-row {
  background-color: rgba(255, 253, 253, 0.4);
  border-radius: 25px;
  padding: 5px 10px 5px 10px;
  display: inline-flex;
  align-items: center;
  margin: 5px 0px 5px 0px;
}

.add-recipe-btn {
  width: fit-content;
  height: fit-content;
  padding: 0px 5px 0px 5px;
  margin-bottom: 0px;
  margin-left: 10px;
  margin-right: 0px;
  font-size: 16px;
}

.underlined-link {
  text-decoration: underline;
}

.recipe-link-and-remove-button-div {
  text-align: left;
  font-size: 18px;
  padding: 0px;
}

.remove-recipe-btn {
  width: 70px;
  height: 10%;
  padding: 0px;
  font-size: 12pt;
}
</style>