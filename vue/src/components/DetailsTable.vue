<template>
  <div class="recipe-details">
    <table>
      <th id="recipe-header">
        {{ recipe.name }}
      </th>
      <div>&nbsp;</div>
      <tr id="servings-data">
        {{
          recipe.numberOfServings
        }}
        Servings
      </tr>

      <th id="ingredients-header">Ingredients</th>
      <tr
        id="ingredients-data"
        v-for="ingredient in recipe.recipeIngredients"
        v-bind:key="ingredient.id"
      >
        {{
          ingredient.measurementQuantity
        }}
        {{
          ingredient.measurementType
        }}
        {{
          ingredient.ingredientName
        }}
      </tr>

      <th id="directions-header">Directions</th>
      <tr id="directions-data">
        {{
          recipe.directions
        }}
      </tr>
    </table>
    <br />
    <span v-if="$store.state.token != ''">
      <router-link
        id="edit-btn"
        tag="button"
        class="dark-green-btns"
        v-bind:to="{ name: 'addRecipe', params: { id: recipe.id } }"
        >Edit Recipe</router-link
      >
    </span>
    <button class="dark-green-btns" @click="$router.go(-1)">Back</button>
  </div>
</template>

<script>
import spoonacularService from "../services/SpoonacularService.js";

export default {
  data() {
    return {
      recipe: {},
      user: {
        userName: this.$store.state.user.username,
        id: this.$store.state.user.id,
      },
    };
  },
  created() {
    this.$store.commit("SET_IS_LOADING", true);
    spoonacularService
      .getRecipeDetails(this.$route.params.id)
      .then((recipeData) => {
        this.recipe = recipeData.data;
        this.recipe.id = this.$route.params.id;
        this.recipe.directions = this.recipe.directions.replace(
          /<\/?[^>]+>/gi,
          ""
        );
        this.$store.commit("SET_IS_LOADING", false);
      });
  },
};
</script>

<style scoped>
.recipe-details {
  margin-left: auto;
  margin-right: auto;
  width: 50%;
  text-align: center;
}

#recipe-header {
  font-size: 2em;
  text-align: center;
}

table {
  border-collapse: separate;
  border-spacing: 0 15px;
}

th,
td,
tr {
  text-align: center;
  border: 1px solid black;
  padding: 5px;
}
</style>