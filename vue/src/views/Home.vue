<template>
  <div id="home-main-div">
    <h1 id="home-header">Scrummy Meal Planner</h1>

    <h2 class="transparent-background-on-image">Welcome, {{ $store.state.user.username }}!</h2>

    <div id="home-btn-container">
      <div>
        <button class="home-btns large-button" @click="$router.push('search-recipes')">
          Search Recipes
        </button>
      </div>
      <div>
        <button class="home-btns large-button" @click="$router.push('create-recipe')">
          Create Recipe
        </button>
      </div>
      <div>
        <button class="home-btns large-button" @click="$router.push('my-recipes')">
          My Recipes
        </button>
      </div>
      <div>
        <button class="home-btns large-button" @click="$router.push('my-meal-plans')">
          My Meal Plans
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import recipeService from "../services/RecipeService.js";
export default {
  name: "home",
  created() {
      recipeService.getAllRecipes().then(() => {
    }).catch((error) => {
      if (error.response && error.response.status === 401) {
        this.$store.commit("SET_AUTH_TOKEN", "");
        this.$router.push("/login");
      } 
    });
  }
};

</script>
<style scoped>

#home-main-div {
  margin-top: 30px;
}

#home-btn-container {
  margin: 50px 0px 50px 0px;
}

#home-header {
  font-size: 36pt;
  color: #e76f51;
  background-color: #264653;
}

.home-btns {
  margin: 10px;
}
</style>
