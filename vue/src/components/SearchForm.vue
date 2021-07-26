<template>
  <div>
    <form id="search-form" v-on:submit.prevent="submitSearch()">
      <div id="instructions-div">
        Enter ingredient(s) separated by a comma with no spaces 
        (banana,peanut butter,bread):
      </div>
      <div class="search-input-div">
        <input
          size="50"
          type="text"
          placeholder="Ingredients"
          v-model="ingredients"
          required
        />
      </div>
      <div class="search-input-div" id="limit-div">
        Limit to
        <input
          type="number"
          min="1"
          max="100"
          v-model="limitNumber"
          placeholder="10"
        />
        results.
      </div>
      <button class="submit-btn dark-green-btns" type="submit" value="Submit">
        Submit
      </button>
      <router-link
        tag="button"
        class="dark-green-btns"
        v-bind:to="{ name: 'home' }"
        >Cancel</router-link
      >
    </form>
    <div v-show="searchSubmitted === true">
      <table class="table-numbered-rows">
        <tr>
          <th colspan="2">Search Results</th>
        </tr>
        <tr v-for="recipe in recipeList" v-bind:key="recipe.id">
          <td class="row-number-cell">
            {{ recipe.place }}
          </td>
          <td class="link-cell">
            <router-link
              class="link"
              v-bind:to="{
                name: 'recipeDetails',
                params: { id: recipe.id },
              }"
              >{{ recipe.title }}</router-link
            >
          </td>
        </tr>
      </table>
    </div>
  </div>
</template>

<script>
import spoonacularService from "../services/SpoonacularService.js";

export default {
  data() {
    return {
      ingredients: "",
      limitNumber: "10",
      recipeList: [],
      searchSubmitted: false,
    };
  },
  methods: {
    submitSearch() {
      this.$store.commit("SET_IS_LOADING", true);
      spoonacularService
        .getSearchResults(this.ingredients, this.limitNumber)
        .then((recipe) => {
          this.recipeList = recipe.data;
          if (this.recipeList.length === 0) {
            alert("No recipes found");
          }
          for (let i = 0; i < this.recipeList.length; i++) {
            this.recipeList[i].place = i + 1;
          }
          this.searchSubmitted = true;
          this.$store.commit("SET_IS_LOADING", false);
        });
    },
  },
  computed: {},
};
</script>

<style scoped>
#search-form {
  margin-top: 10px;
  font-size: 16pt;
  width: 40%;
  min-width: 500px;
  padding: 20px;
}

.search-input-div {
  margin-top: 20px;
}

#limit-div {
  margin-bottom: 20px;
}

</style>