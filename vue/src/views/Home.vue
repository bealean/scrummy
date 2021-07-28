<template>
  <div class="home">
    <br />
    <h1 class="home-header">Scrummy Meal Planner</h1>
    <br />
    <h2>Welcome, {{ $store.state.user.username }}!</h2>
    <br /><br />
    <div class="home-btn-container">
      <div>
        <button class="home-btns" @click="$router.push('search-recipes')">
          Search Recipes
        </button>
      </div>
      <div>
        <button class="home-btns" @click="$router.push('create-recipe')">
          Create Recipe
        </button>
      </div>
      <div>
        <button class="home-btns" @click="$router.push('my-recipes')">
          My Recipes
        </button>
      </div>
      <div>
        <button class="home-btns" @click="$router.push('my-meal-plans')">
          My Meal Plans
        </button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "home",
};
</script>
<style scoped>
header {
  text-align: center;
}

h2 {
  background-color: rgba(255, 253, 253, 0.85);
  color: #264653;
  width: 40%;
  border-radius: 25px;
  display: inline-block;
  font-size: 26px;
  margin-bottom: 0px;
}

.home-btn-container {
  margin: 50px 0px 0px 0px;
}

.home-header {
  font-size: 36pt;
  color: #e76f51;
  background-color: #264653;
}

.home-btns {
  margin: 10px;
}
</style>
