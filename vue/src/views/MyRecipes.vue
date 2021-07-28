<template>
  <div>
    <h1>My Recipes</h1>
    <my-recipes />
  </div>
</template>

<script>
import MyRecipes from "../components/MyRecipes.vue";

export default {
  components: { MyRecipes },
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