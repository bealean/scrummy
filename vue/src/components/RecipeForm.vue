<template>
  <div>
    <head>
      <link
        href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.14.0/css/all.min.css"
        rel="stylesheet"
      />
    </head>

    <form id="recipe-form" v-on:submit.prevent="submitRecipe()">
      <div id="div-for-recipe-form-fields">
        <div class="form-label">
          <label for="name">Name</label>
        </div>
        <div>
          <input
            id="name"
            type="text"
            v-model="recipe.name"
            minlength="1"
            maxlength="200"
            size="60%"
          />
        </div>

        <div class="form-label">
          <label for="servings">Servings</label>
        </div>
        <div>
          <input
            type="number"
            step="any"
            min="0"
            max="1000000000"
            id="servings"
            v-model="recipe.numberOfServings"
          />
        </div>
        <div class="form-label" id="label-for-ingredients">
          <label for="ingredients">Ingredients</label>
        </div>
        <table>
          <tr
            class="ingredients-list-boxes"
            v-for="(input, k) in inputs"
            :key="k"
          >
            <!--Looping starts for ingredients:-->
            <input
              v-model="input.measurementQuantity"
              type="number"
              step="any"
              min="0"
              max="1000000000"
              placeholder="quantity"
              size="20%"
            />
            <input
              v-model="input.measurementType"
              type="text"
              maxlength="60"
              placeholder="measurement type"
              size="20%"
            />
            <input
              v-model="input.ingredientName"
              placeholder="ingredient"
              type="text"
              minlength="1"
              maxlength="300"
              size="40%"
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
        <div class="form-label" id="label-for-directions">
          <label for="directions">Directions</label>
        </div>
        <div style="margin-top: 10px">
          <textarea
            id="directions"
            rows="20"
            cols="200"
            maxlength="10000"

            v-model="recipe.directions"
          />
        </div>
      </div>
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
        recipeIngredients: [
          {
            measurementQuantity: "",
            measurementType: "",
            ingredientName: "",
          },
        ],
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
    submitRecipe() {
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
            if (error.response && error.response.status === 401) {
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
    },
  },
  created() {
    if (
      this.$route.params.id &&
      !this.$store.state.prevRoute.endsWith(
        `recipe-details/${this.$route.params.id}`
      )
    ) {
      alert("Edit Recipe must be opened from Recipe Details.");
      this.$router.push("/");
    } else {
      this.$store.commit("SET_IS_LOADING", true);
      //Check for Session Expired before user makes edits
      recipeService
        .getAllRecipes()
        .then(() => {})
        .catch((error) => {
          this.$store.commit("SET_IS_LOADING", false);
          if (error.response && error.response.status === 401) {
            alert("Session Expired. Please sign in again.");
            this.$router.push("/login");
          }
        });
      if (this.$route.path != "/create-recipe") {
        this.recipe = this.$store.state.recipe;
        this.$store.commit("SET_RECIPE", {});
        if (
          this.recipe.recipeIngredients &&
          this.recipe.recipeIngredients.length > 0
        ) {
          this.inputs = this.recipe.recipeIngredients;
        }
      }

      if (!this.$route.path.startsWith("/edit-recipe")) {
        this.isNewRecipe = true;
        if (this.$route.path.startsWith("/save-recipe"))
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
#recipe-form {
  margin-top: 5px;
  margin-right: auto;
  margin-left: auto;
  max-width: 1000px;
  padding-top: 5px;
}

#div-for-recipe-form-fields {
    margin-bottom: 30px;
}

tr {
  text-align: left;
}

table {
  margin-bottom: 0px;
  padding: 0px;
  border-spacing: 1px;
}

.form-label {
  padding: 20px;
  margin-top: 20px;
}

#label-for-ingredients {
    padding-bottom: 5px;
}

#label-for-directions {
    padding-bottom: 10px;
}
</style>