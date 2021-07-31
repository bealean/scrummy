<template>
  <nav id="nav">
    <span v-if="this.$route.path != '/login' && this.$route.path != '/'">
      <router-link class="link" v-bind:to="{ name: 'home' }">Home</router-link>
      <span v-if="$store.state.token != ''"> &nbsp;|&nbsp; </span>
    </span>
    <span v-if="$store.state.token != '' && this.$route.path != '/login'">
      <router-link class="link" v-bind:to="{ name: 'logout' }"
        >Logout</router-link
      >
    </span>
    <span v-if="this.$route.path == '/login'">
      <router-link class="link" v-bind:to="{ name: 'searchRecipes' }"
        >Search Recipes Incognito</router-link
      >
    </span>
  </nav>
</template>

<script>
export default {};
</script>

<style>
#nav {
  font-size: 17pt;
  background-color: rgba(255, 253, 253, 0.85);
  border-radius: 25px;
  width: 80%;
  margin-left: auto;
  margin-right: auto;
  margin-top: 15px;
  margin-bottom: 10px;
}
</style>