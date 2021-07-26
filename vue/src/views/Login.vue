<template>
  <div id="login" class="text-center">
    <form class="form-signin" @submit.prevent="login">
      <h1 class="h3 mb-3 font-weight-normal">Scrummy Meal Planner</h1>
      <div
        id="sign-in-message"
        class="user-message"
        v-if="!this.$route.query.registration"
      >
        Sign in for full functionality,<br />
        or click above to Search Recipes Incognito.
      </div>
      <div class="alert alert-danger" role="alert" v-if="invalidCredentials">
        Invalid username or password!
      </div>
      <div
        class="alert alert-success user-message"
        role="alert"
        v-if="this.$route.query.registration"
      >
        Thank you for registering, please sign in.
      </div>
      <div class="label-input-div">
        <label
          id="username-field"
          for="username"
          class="login-register-input-label"
          >Username</label
        >
        <input
          type="text"
          id="username"
          class="form-control"
          placeholder="Username"
          v-model="user.username"
          required
          autofocus
        />
      </div>
      <div class="label-input-div">
        <label for="password" class="login-register-input-label"
          >Password</label
        >
        <input
          type="password"
          id="password"
          class="form-control"
          placeholder="Password"
          v-model="user.password"
          required
        />
      </div>
      <button class="dark-green-btns" type="submit">Sign in</button>
      <button
        id="register"
        class="dark-green-btns"
        @click="$router.push('register')"
      >
        Need an account?
      </button>
    </form>
  </div>
</template>

<script>
import authService from "../services/AuthService";

export default {
  name: "login",
  components: {},
  data() {
    return {
      user: {
        username: "",
        password: "",
      },
      invalidCredentials: false,
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then((response) => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$router.push("/");
          }
        })
        .catch((error) => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    },
  },
};
</script>
<style scoped>
h1 {
  padding: 15px;
  font-size: 24pt;
}

button {
  margin-top: 30px;
  display: block;
  margin-left: auto;
  margin-right: auto;
}

#register,
#searchAnonymously {
  margin-bottom: 30px;
  margin-top: 15px;
}

.user-message {
  margin-bottom: 20px;
}
</style>
