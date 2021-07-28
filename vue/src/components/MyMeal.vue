<template>
  <div>
    <h1 id="recipe-header">
      Meal Plan:
      {{ meal.mealType }}
    </h1>
    <div>
      <form v-on:submit.prevent="addRecipeToMeal()">
        <h2><label>Select Recipe</label></h2>
        <div class="custom-select">
          <select class="select" v-model="selectedRecipe">
            <option
              v-for="recipe in myRecipes"
              :key="recipe.recipeId"
              :value="{ id: recipe.recipeId }"
            >
              {{ recipe.name }}
            </option>
          </select>
          <br />
        </div>
        <button class="submit-btn dark-green-btns" type="submit" value="Submit">
          Save
        </button>
        <button
          class="submit-btn dark-green-btns"
          v-on:click.prevent="returnToMealPlanDetails()"
          type="cancel"
        >
          Go Back
        </button>
      </form>
    </div>
    <div v-if="meal.recipes">
      <div class="meal-plan-items-list" v-show="meal.recipes.length > 0">
        <ul
          class="meal-plan-list"
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
export default {
  data() {
    return {
      selectedRecipe: {
        id: "",
      },
      myRecipes: {},
      meal: {},
      user: {
        userName: this.$store.state.user.username,
        id: this.$store.state.user.id,
      },
    };
  },
  methods: {
    addRecipeToMeal() {
      recipeService
        .addRecipeToMeal(this.meal.mealId, this.selectedRecipe.id)
        .then((response) => {
          if (response.status === 201) {
            alert(`Recipe added to ${this.meal.mealType}!`);
            this.$router.go();
          }
        })
        .catch((error) => {
          alert("There was a problem adding the recipe. Please retry.");
          console.error(error + " problem adding recipe.");
        });
    },
    returnToMealPlanDetails() {
      this.$router.push(`/meal-plan-details/${this.$route.params.mealPlanId}`);
    },
  },
  created() {
    mealService
      .getMealById(this.$route.params.mealId)
      .then((response) => {
        this.meal = response.data;
      })
      .catch((error) => {
        alert("There was a problem retrieving the meal.");
        console.error("Problem retrieving the meal: " + error);
      });
    recipeService
      .getAllRecipes()
      .then((response) => {
        this.myRecipes = response.data;
      })
      .catch((error) => {
        alert("There was a problem retrieving the recipe list.");
        console.error("Problem retrieving the recipe list: " + error);
      });
  },
};
</script>

<style scoped>
.submit-btn {
  background-color: #1a4314;
  width: 100px;
  height: 25px;
  padding: 0px;
  margin: 15px;
}

.custom-select {
  position: relative;
}

.meal-plan-items-list {
  background: #94c973;
  margin: auto;
  width: 90%;
  max-width: 1080px;
  text-align: center;
  padding: 15px;
  border-radius: 25px;
  font-size: 14pt;
}

.meal-plan-list {
  list-style: none;
}

#recipe-header {
  font-size: 36pt;
}

.select {
  height: 25px;
  font-size: 16px;
}

label {
  font-size: 20pt;
}
</style>