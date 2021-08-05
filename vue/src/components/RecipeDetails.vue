<template>
  <div id="recipe-details-div">
    <div v-if="!recipe.name">
      <h1>Recipe Details</h1>
    </div>
    <div v-if="recipe.name">
      <h1>{{ recipe.name }}</h1>
      <div id="servings-div">{{ recipe.numberOfServings }} Servings</div>
      <h2>Ingredients</h2>
      <table id="recipe-ingredients-table">
        <tr
          id="ingredients-data"
          v-for="ingredient in recipe.recipeIngredients"
          v-bind:key="ingredient.id"
        >
          <td>
            {{ ingredient.measurementQuantity }}
            {{ ingredient.measurementType }}
            {{ ingredient.ingredientName }}
          </td>
        </tr>
      </table>
      <h2>Directions</h2>
      <div id="directions-div">{{ recipe.directions }}</div>

      <span
        v-if="
          $store.state.token != '' && $route.path.startsWith('/recipe-details')
        "
      >
        <button class="dark-green-btns" v-on:click.prevent="editRecipe()">
          Save Recipe
        </button>
      </span>
      <span
        v-if="
          $store.state.token != '' &&
          $route.path.startsWith('/my-recipe-details')
        "
      >
        <button class="dark-green-btns" v-on:click.prevent="editRecipe()">
          Edit Recipe
        </button>
      </span>
      <span v-if="$route.path.startsWith('/my-recipe-details')">
        <button
          class="dark-green-btns"
          v-on:click.prevent="deleteRecipe(recipe.recipeId)"
        >
          Delete Recipe
        </button>
      </span>
      <button class="dark-green-btns" v-on:click="goBack">Back</button>
    </div>
  </div>
</template>

<script>
import recipeService from "../services/RecipeService.js";
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
  methods: {
    deleteRecipe(recipeId) {
      const choice = confirm("Are you sure you want to delete this recipe?");
      if (choice == true) {
        recipeService
          .deleteRecipe(recipeId)
          .then(() => {
            this.$router.push("/my-recipes");
          })
          .catch((error) => {
            alert("There was a problem deleting the recipe. Please retry.");
            console.error("Problem deleting recipe." + error);
          });
      }
    },
    editRecipe() {
      this.$store.commit("SET_RECIPE", this.recipe);
      if (this.$route.path.startsWith("/recipe-details")) {
        this.$router.push({
          name: "saveRecipe",
          params: { id: this.recipe.id },
        });
      } else {
        this.$router.push({
          name: "editRecipe",
          params: { id: this.recipe.id },
        });
      }
    },
    /* Back button goes to previous screen, except for the case
     where the user got to Recipe Details by cancelling Edit Recipe */
    goBack() {
      if (this.$route.path.startsWith("/recipe-details")) {
        this.$router.push({ name: "searchRecipes" });
      } else if (this.$store.state.prevRoute.startsWith("/meal-plan-details")) {
        this.$router.push(this.$store.state.prevRoute);
      } else {
        this.$router.push({ name: "myRecipes" });
      }
    },
  },
  created() {
    this.$store.commit("SET_IS_LOADING", true);
    if (this.$route.path.startsWith("/recipe-details")) {
      spoonacularService
        .getRecipeDetails(this.$route.params.id)
        .then((response) => {
          this.recipe = response.data;
          this.recipe.id = this.$route.params.id;
          this.recipe.directions = this.recipe.directions.replace(
            /<\/?[^>]+>/gi,
            ""
          );
        })
        .catch((error) => {
          if (error.response && error.response.status === 500) {
            alert("Server error. Please retry.");
            console.log("Server error: " + error);
          } else {
            alert("Error retrieving recipe details.");
            console.log("Problem retrieving recipe details: " + error);
          }
        })
        .finally(() => {
          this.$store.commit("SET_IS_LOADING", false);
        });
    } else {
      recipeService
        .getRecipe(this.$route.params.id)
        .then((response) => {
          this.recipe = response.data;
          this.recipe.id = this.$route.params.id;
        })
        .catch((error) => {
          if (error.response && error.response.status === 404) {
            alert("Recipe ID not found in your recipe box.");
            console.log("Recipe not found: " + error);
          } else {
            alert("Problem retrieving recipe details.");
            console.log("Problem retrieving recipe details: " + error);
          }
        })
        .finally(() => {
          this.$store.commit("SET_IS_LOADING", false);
        });
    }
  },
};
</script>

<style>
#recipe-details-div {
  background: #94c973;
  margin: 2em auto;
  width: 65vw;
  text-align: center;
  border-radius: 25px;
  padding-bottom: 25px;
  color: #1a4314;
  font-size: 16pt;
}

@media only screen and (max-width: 1024px) {
  #recipe-details-div {
    width: 90vw;
  }
}

@media only screen and (max-width: 340px) {
  #recipe-details-div {
    width: 100vw;
  }
}

#recipe-ingredients-table {
  margin-top: 20px;
  margin-bottom: 20px;
  border: 5px;
  border-style: dotted;
}
tr {
  text-align: left;
}

#servings-div {
  margin-bottom: 15px;
}

#directions-div {
  width: 90%;
  margin: auto;
  margin-top: 20px;
  margin-bottom: 30px;
  word-wrap: break-word;
  border: 5px;
  border-style: dotted;
  border-radius: 25px;
  padding: 15px;
}
</style>