<template>
  <nav id="nav" class="transparent-background-on-image">
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
  font-size: 24px;
  border-radius: 25px;
  width: 80%;
    min-width: fit-content;
  margin-left: auto;
  margin-right: auto;
  margin-top: 15px;
  margin-bottom: 10px;

}
</style>