<template>
  <div>
    <table class="table-numbered-rows" v-bind:key="user.id">
      <tr v-for="recipe in recipeList" v-bind:key="recipe.recipeId">
        <td class="row-number-cell">
          {{ recipe.place }}
        </td>
        <td class="link-cell">
          <router-link
            class="link"
            v-bind:to="{
              name: 'recipeDetails',
              params: { id: recipe.recipeId, newOrExisting: 'existing' },
            }"
            >{{ recipe.name }}</router-link
          >
          </td>
      </tr>
    </table>
    <button class="bottom-btn dark-green-btns" @click="$router.push('create-recipe')">
      Create New Recipe
    </button>
    <button class="bottom-btn dark-green-btns" @click="$router.push('/')">Back</button>
  </div>
</template>

<script>
import recipeService from "../services/RecipeService.js";
export default {
  data() {
    return {
      recipeList: [],
      user: {
        userName: this.$store.state.user.username,
        id: this.$store.state.user.id,
      },
    };
  },
  created() {
    recipeService.getAllRecipes().then((recipe) => {
      this.recipeList = recipe.data;
      for (let i = 0; i < this.recipeList.length; i++) {
        this.recipeList[i].place = i + 1;
      }
    }).catch((error) => {
      if (error.response.status === 401) {
        alert("Session expired. Please sign in again.");
        this.$router.push("/login");
      } else {
        alert("Error retrieving recipe list.");
        console.log("Error retrieving recipe list: " + error);
      }

    });
  },
};
</script>

<style scoped>

.bottom-btn {
  margin: 20px;
}

</style>