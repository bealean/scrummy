<template>
  <div class="my-recipe-details">
    <div v-if="!recipe.name">
      <h1>Recipe Details</h1>
    </div>
    <div v-if="recipe.name">
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
      <span v-if="$store.state.token != '' && newOrExisting === 'new'">
        <button class="dark-green-btns" v-on:click.prevent="editRecipe()">
          Edit/Save Recipe
        </button>
      </span>
      <span v-if="$store.state.token != '' && newOrExisting === 'existing'">
        <button class="dark-green-btns" v-on:click.prevent="editRecipe()">
          Edit Recipe
        </button>
      </span>
      <span v-if="newOrExisting === 'existing'">
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
      newOrExisting: "",
      prevRoute: null,
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
      this.$router.push({
        name: "editRecipe",
        params: { id: this.recipe.id, newOrExisting: this.newOrExisting },
      });
    },
    /* Back button goes to previous screen, except for the case
     where the user got to Recipe Details by cancelling Edit Recipe */
    goBack() {
      if (this.$route.params.newOrExisting === "new") {
        this.$router.push({name: "searchRecipes"});
      } else {
        this.$router.push({name: "myRecipes"});
      }
    },
  },
  created() {
    this.$store.commit("SET_IS_LOADING", true);
    if (this.$route.params.newOrExisting === "new") {
      this.newOrExisting = "new";
      spoonacularService
        .getRecipeDetails(this.$route.params.id)
        .then((response) => {
          this.recipe = response.data;
          this.recipe.id = this.$route.params.id;
          this.recipe.directions = this.recipe.directions.replace(
            /<\/?[^>]+>/gi,
            ""
          );
          this.$store.commit("SET_IS_LOADING", false);
        })
        .catch((error) => {
          if (error.response && error.response.status === 500) {
            alert("Server error. Please retry.");
            console.log("Server error: " + error);
          } else {
            alert("Error retrieving recipe details.");
            console.log("Problem retrieving recipe details: " + error);
          }
          this.$store.commit("SET_IS_LOADING", false);
        });
    } else if (this.$route.params.newOrExisting === "existing") {
      this.newOrExisting = "existing";
      recipeService
        .getRecipe(this.$route.params.id)
        .then((response) => {
          this.recipe = response.data;
          this.recipe.id = this.$route.params.id;
          this.$store.commit("SET_IS_LOADING", false);
        })
        .catch((error) => {
          if (error.response && error.response.status === 404) {
            alert("Recipe ID not found in your recipe box.");
            console.log("Recipe not found: " + error);
          } else {
            alert("Problem retrieving recipe details.");
            console.log("Problem retrieving recipe details: " + error);
          }
          this.$store.commit("SET_IS_LOADING", false);
        });
    } else {
      this.$store.commit("SET_IS_LOADING", false);
      alert("Invalid Address.");
      this.$router.push("/");
    }
  },
};
</script>

<style>
.my-recipe-details {
  background: #94c973;
  margin: 4em auto;
  width: 65%;
  text-align: center;
  border-radius: 25px;
}

#recipe-header {
  font-size: 2em;
  text-align: center;
}

.edit-recipe-btn {
  background-color: #1a4314;
  margin-bottom: 30px;
}

th {
  border-radius: 25px;
}
</style>