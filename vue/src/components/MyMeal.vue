<template>
  <div>
    <h1>Meal: {{ dailyPlan.weekday }} {{ meal.mealType }}</h1>
    <div>
      <form id="select-recipe-form" v-on:submit.prevent="addRecipeToMeal()">
        <h2>Select Recipe</h2>
        <div class="custom-select-div">
          <select class="select" v-model="selectedRecipe">
            <option
              v-for="recipe in myRecipes"
              :key="recipe.recipeId"
              :value="{ id: recipe.recipeId }"
            >
              {{ recipe.name }}
            </option>
          </select>
        </div>
        <button class="dark-green-btns" type="submit" value="Submit">
          Save
        </button>
        <button
          class="dark-green-btns"
          v-on:click.prevent="returnToMealPlanDetails()"
          type="cancel"
        >
          Go Back
        </button>
      </form>
    </div>
    <div v-if="meal.recipes">
      <div class="meal-recipes-list-div" v-show="meal.recipes.length > 0">
        <ul
          class="meal-recipes-list"
          v-for="recipe in meal.recipes"
          v-bind:key="recipe.recipeId"
        >
          <li>{{ recipe.name }}</li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import mealService from "../services/MealService.js";
import recipeService from "../services/RecipeService.js";
import dailyPlanService from "../services/DailyPlanService.js";
export default {
  data() {
    return {
      selectedRecipe: {
        id: "",
      },
      myRecipes: {},
      meal: {},
      dailyPlan: {},
    };
  },
  methods: {
    addRecipeToMeal() {
      if (this.selectedRecipe.id !== "") {
        this.$store.commit("SET_IS_LOADING", true);
        recipeService
          .addRecipeToMeal(this.meal.mealId, this.selectedRecipe.id)
          .then((response) => {
            if (response.status === 201) {
              this.$router.go();
            } else {
              this.$store.commit("SET_IS_LOADING", false);
              alert("There was a problem adding the recipe.");
              console.error(
                "Problem adding recipe. Response status: " + response.statusText
              );
            }
          })
          .catch((error) => {
            this.$store.commit("SET_IS_LOADING", false);
            alert("There was a problem adding the recipe.");
            console.error(error + " problem adding recipe.");
          });
      } else {
        alert("Please select a recipe!");
      }
    },
    returnToMealPlanDetails() {
      this.$router.push(`/meal-plan-details/${this.$route.params.mealPlanId}`);
    },
  },
  created() {
    this.$store.commit("SET_IS_LOADING", true);
    mealService
      .getMealById(this.$route.params.mealId)
      .then((response) => {
        this.meal = response.data;
        dailyPlanService
          .getDailyPlanById(this.meal.dailyPlanId)
          .then((response) => {
            this.dailyPlan = response.data;
            Promise.all([request2]).then(() => {
              this.$store.commit("SET_IS_LOADING", false);
            });
          })
          .catch((error) => {
            this.$store.commit("SET_IS_LOADING", false);
            alert("There was a problem retrieving the daily plan.");
            console.error("Problem retrieving the daily plan: " + error);
          });
      })
      .catch((error) => {
        this.$store.commit("SET_IS_LOADING", false);
        alert("There was a problem retrieving the meal.");
        console.error("Problem retrieving the meal: " + error);
      });
    let request2 = recipeService
      .getAllRecipes()
      .then((response) => {
        this.myRecipes = response.data;
      })
      .catch((error) => {
        this.$store.commit("SET_IS_LOADING", false);
        alert("There was a problem retrieving the recipe list.");
        console.error("Problem retrieving the recipe list: " + error);
      });
  },
};
</script>

<style scoped>
#select-recipe-form {
  padding-top: 20px;
  padding-bottom: 30px;
  margin-top: 5px;
  width: 50%;
}

.custom-select-div {
  margin-top: 10px;
  margin-bottom: 40px;
}

.select {
  height: 25px;
  font-size: 16px;
}

.meal-recipes-list-div {
  background: #94c973;
  margin: auto;
  width: 90%;
  max-width: 1080px;
  text-align: center;
  padding: 15px;
  border-radius: 25px;
  font-size: 16pt;
  color: #1a4314;
}

.meal-recipes-list {
  list-style: none;
}
</style>