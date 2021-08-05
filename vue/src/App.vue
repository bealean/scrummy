<template>
  <div id="app">
    <loading-overlay v-if="$store.state.isLoading" />
    <div id="loaded-div">
      <top-navigation />
      <router-view />
      <the-footer />
    </div>
  </div>
</template>
<script>
import LoadingOverlay from "./components/LoadingOverlay.vue";
import TopNavigation from "./components/TopNavigation.vue";
import TheFooter from "./components/TheFooter.vue";

export default {
  components: { LoadingOverlay, TopNavigation, TheFooter },
  mounted() {
    document.onreadystatechange = () => {
      if (document.readyState === "complete") {
        this.$store.commit("SET_IS_LOADING", false);
      }
    };
  },
};
</script>

<style>
html {
  background: url("./assets/pexels-ready-made-smaller.jpg") center;
  /* https://images.pexels.com/photos/3850888/pexels-photo-3850888.jpeg?cs=srgb&dl=pexels-ready-made-3850888.jpg&fm=jpg */
  background-size: cover;
  background-color: rgba(255, 253, 253, 0.85);
  font-family: monospace;
  font-size: 14pt;
  text-align: center;
  height: 100%;
}

#loaded-div {
  min-height: 98vh;
  display: grid;
  min-width: 0;
  grid-template-rows: auto minmax(0, 1fr) auto;
}

button {
  display: inline-block;
  padding: 0.3em 1.2em;
  margin: 0 0.3em 0.3em 0;
  border-color:#264653;
  border-radius: 2em;
  box-sizing: border-box;
  text-decoration: none;
  font-family: monospace;
  font-size: 18px;
  font-weight:600;
  color: #264653;
  background-color: #94c973;
  text-align: center;
  transition: all 0.2s;
  width: 300px;
  height: 50px;
  cursor: pointer;
}

.large-button {
  width: 325px;
  height: 55px;
  font-size: 22px;
}

.dark-green-btns {
  background-color: #1a4314;
  color: #ffffff;
  font-weight:300;
}

button:hover,
.router-link-btns:hover {
  background-color: #e76f51;
}

h1 {
  color: #e76f51;
  background-color: #264653;
  font-size: 36pt;
  border-radius: 25px;
  width: 60%;
  display: inline-block;
  padding: 10px;
}

h2 {
  background-color: rgba(255, 253, 253, 0.5);
  color: #264653;
  min-width: fit-content;
  width: 40%;
  border-radius: 25px;
  display: inline-block;
  font-size: 26px;
  margin-bottom: 0px;
  margin-top: 15px;
  padding: 0px 10px 0px 10px;
}

.transparent-background-on-image {
  background-color: rgba(255, 253, 253, 0.75);
}

.link {
  text-decoration: none;
  color: #264653;
}

.link:hover {
  color: #e95c39;
}

table {
  background-color: #94c973;
  border-radius: 25px;
  margin: 15px 0px 0px 0px;
  padding: 15px;
  margin-left: auto;
  margin-right: auto;
  font-size: 16pt;
}

.table-numbered-rows {
  width: fit-content;
  border-collapse: separate;
  border-spacing: 10px 15px;
}

.table-numbered-rows td {
  border: 1px solid #1a4314;
  border-radius: 25px;
  padding-top: 5px;
  padding-bottom: 5px;
}

.table-numbered-rows th {
  padding: 5px 10px 5px 10px;
  border-radius: 25px;
  text-align: center;
}

.row-number-cell {
    text-align: center;
    color: #1a4314;
    padding-left: 5px;
    padding-right: 5px;
}

.link-cell {
  padding-left: 20px;
  padding-right: 20px;
  text-align: left;
  text-decoration: underline;
}

th {
  background-color: #1a4314;
  color: white;
}

.label-input-div {
  margin-top: 10px;
}

label {
  font-size: 14pt;
  background-color: rgba(255, 253, 253, 0.5);
  border-radius: 25px;
  text-align: center;
  padding: 5px;
}

.login-register-input-label {
  padding-left: 10px;
  padding-right: 10px;
  margin-right: 5px;
}

form {
  background: #94c973;
  margin: 2em auto;
  width: 90%;
  max-width: 1080px;
  text-align: center;
  padding: 15px;
  border-radius: 25px;
}

textarea {
  width: 90%;
  max-width: 500px;
}

input[type="password"],
input[type="text"],
input[type="number"] {
  font-size: 14px;
  font-family: monospace;
  display: inline-block;
  padding: 8px;
  margin: 0 auto;
}

.alert-danger {
  color: #fa3706;
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 15px;
}

router-link {
  cursor: pointer;
}
</style>