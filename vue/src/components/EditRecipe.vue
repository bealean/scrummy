<template>
  <div>
    <head>
      <link
        href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.14.0/css/all.min.css"
        rel="stylesheet"
      />
    </head>

    <form id="edit-form" v-on:submit.prevent="editRecipe()">
      <span v-if="!isNewRecipe">
        <h1>Edit Recipe</h1>
      </span>
      <span v-if="isNewRecipe">
        <h1>Edit/Save Recipe</h1>
      </span>
      <div>
        <label for="name">Name </label>
        <div>
          <br />
          <input
            id="name"
            type="text"
            v-model="recipe.name"
            minlength="1"
            maxlength="500"
            size="75"
          />
        </div>
        <div><br /><label for="servings">Servings </label></div>
        <div>
          <br />
          <input type="text" id="servings" v-model="recipe.numberOfServings" />
        </div>
        <br />
        <label for="ingredients">Ingredients</label>
        <table>
          <tr
            class="ingredients-list-boxes"
            v-for="(input, k) in inputs"
            :key="k"
          >
            <!--Looping starts for ingredients:-->
            <input
              v-model="input.measurementQuantity"
              type="text"
              placeholder="quantity"
            />
            <input
              v-model="input.measurementType"
              type="text"
              placeholder="measurement type"
            />
            <input
              v-model="input.ingredientName"
              placeholder="ingredient"
              type="text"
              minlength="1"
              maxlength="500"
              size="50"
            />
            <span>
              <i
                class="fas fa-minus-circle"
                @click="remove(k)"
                v-show="k || (!k && inputs.length > 1)"
              ></i>
              <i
                class="fas fa-plus-circle"
                @click="add(k)"
                v-show="k == inputs.length - 1"
              ></i>
            </span>
          </tr>
        </table>
        <br />
        <label for="directions">Directions</label>
        <div style="margin-top: 10px">
          <textarea
            rows="20"
            cols="200"
            style="width: 500px"
            v-model="recipe.directions"
          />
        </div>
      </div>
      <br />
      <span v-if="!isNewRecipe">
        <button class="dark-green-btns" type="submit" value="Submit">
          Save
        </button>
      </span>
      <span v-if="isNewRecipe">
        <button class="dark-green-btns" type="submit" value="Submit">
          Save to My Recipes
        </button>
      </span>
      <button class="dark-green-btns" v-on:click="goBack" type="cancel">
        Cancel
      </button>
    </form>
  </div>
</template>

<script>
import recipeService from "../services/RecipeService.js";

export default {
  data() {
    return {
      isNewRecipe: false,
      recipe: {
        recipeId: "",
        name: "",
        directions: "",
        recipeIngredients: [],
        numberOfServings: "",
      },
      inputs: [
        {
          measurementQuantity: "",
          measurementType: "",
          ingredientName: "",
          recipeId: this.$route.params.id,
          recipeIngredientId: "",
        },
      ],
    };
  },
  methods: {
    editRecipe() {
      const editedRecipe = {
        recipeId: this.recipe.recipeId,
        userId: this.$store.state.user.id,
        name: this.recipe.name,
        directions: this.recipe.directions,
        recipeIngredients: this.inputs,
        numberOfServings: this.recipe.numberOfServings,
      };
      if (this.isNewRecipe) {
        recipeService
          .newRecipe(editedRecipe)
          .then((response) => {
            if (response.status === 201) {
              alert("Recipe Added!");
              this.$router.push("/my-recipes");
            }
          })
          .catch((error) => {
            if (error.response.status === 401) {
              alert("Session expired. Please sign in again.");
              this.$router.push("/login");
            } else {
              alert("There was a problem adding the recipe. Please retry.");
              console.error("Problem adding recipe." + error);
            }
          });
      } else {
        recipeService
          .updateRecipe(editedRecipe)
          .then((response) => {
            if (response.status === 200) {
              alert("Recipe updated!");
              this.$router.push("/my-recipes");
            }
          })
          .catch((error) => {
            alert("There was a problem updating the recipe. Please retry.");
            console.error(error + " problem updating recipe.");
          });
      }
    },
    add() {
      this.inputs.push({
        measurementQuantity: "",
        measurementType: "",
        ingredientName: "",
        recipeId: this.recipe.recipeId,
        recipeIngredientId: "",
      });
    },
    remove(index) {
      this.inputs.splice(index, 1);
    },
    goBack() {
      this.$router.push(this.$store.state.prevRoute);
    }
  },
  created() {
    if (
      this.$store.state.prevRoute !=
      `/recipe-details/${this.$route.params.id}/${this.$route.params.newOrExisting}`
    ) {
      alert("Edit Recipe must be opened from Recipe Details.");
      this.$router.push("/");
    } else {
      this.$store.commit("SET_IS_LOADING", true);
      //Check for Session Expired befor user makes edits
      recipeService
        .getAllRecipes()
        .then(() => {})
        .catch((error) => {
          if (error.response.status === 401) {
            this.$store.commit("SET_IS_LOADING", false);
            alert("Session Expired. Please sign in again.");
            this.$router.push("/login");
          }
        });
      this.recipe = this.$store.state.recipe;
      this.$store.commit("SET_RECIPE", {});
      this.inputs = this.recipe.recipeIngredients;
      if (this.$route.params.newOrExisting === "new") {
        this.isNewRecipe = true;
        this.recipe.directions = this.recipe.directions.replace(
          /<\/?[^>]+>/gi,
          ""
        );
      }
    }
  },
  mounted() {
    this.$store.commit("SET_IS_LOADING", false);
  },
};
</script>

<style scoped>
#edit-form {
  background: #94c973;
  margin: 4em auto;
  width: 90%;
  max-width: 1080px;
  text-align: center;
  padding: 5px;
}

tr {
  text-align: left;
}

table {
  margin-bottom: 0px;
  padding: 0px;
  border-spacing: 1px;
}
</style>