<template>
  <div>
    <h1 id="save-recipe-header">Save Recipe</h1>
    <div id="save-recipe-message-div">
      <div id="save-recipe-message-label">
      Make any adjustments below before saving.
      </div>
    </div>
    <recipe-form />
  </div>
</template>

<script>
import RecipeForm from "../components/RecipeForm.vue";
export default {
  components: { RecipeForm },
};
</script>

<style scoped>
#save-recipe-message-div {
  background-color: #94c973;
  border-radius: 25px;
  width: fit-content;
  max-width: 80%;
  margin-right: auto;
  margin-left: auto;
  padding: 20px;
}

#save-recipe-message-label {
  padding: 10px;
  background-color: rgba(255, 253, 253, 0.5);
  border-radius: 25px;
  
}

</style>