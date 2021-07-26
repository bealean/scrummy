<template>
  <div>
    <h1>My Recipes</h1>
    <my-recipes-list />
  </div>
</template>

<script>
import MyRecipesList from "../components/MyRecipesList.vue";

export default {
  components: { MyRecipesList },
  created() {
    this.$store.commit("SET_IS_LOADING", true);
  },
  mounted() {
    this.$nextTick(function () {
      this.$store.commit("SET_IS_LOADING", false);
    });
  },
};
</script>

<style>
</style>