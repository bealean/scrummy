<template>
  <div id="register" class="text-center">
    <form class="form-register" @submit.prevent="register">
      <h1 class="h3 mb-3 font-weight-normal">Create Account</h1>
      <div class="alert alert-danger" role="alert" v-if="registrationErrors">
        {{ registrationErrorMsg }}
      </div>
      <table id="register-input-table">
        <tr id="username-row">
          <td>
            <label for="username" class="login-register-input-label"
              >Username</label
            >
          </td>
          <td>
            <input
              type="text"
              id="username"
              class="form-control"
              placeholder="Username"
              v-model="user.username"
              required
              autofocus
              ref="focused"
            />
          </td>
        </tr>
        <tr>
          <td>
            <label for="password" class="login-register-input-label"
              >Password</label
            >
          </td>
          <td>
            <input
              type="password"
              id="password"
              class="form-control"
              placeholder="Password"
              v-model="user.password"
              required
            />
          </td>
        </tr>
        <tr id="confirm-password-row">
          <td></td>
          <td>
            <input
              type="password"
              id="confirmPassword"
              class="form-control"
              placeholder="Confirm Password"
              v-model="user.confirmPassword"
              required
            />
          </td>
        </tr>
      </table>
      <div id="login-link-div">
        <router-link class="login-btn" :to="{ name: 'login' }"
          >Have an account?</router-link
        >
      </div>
      <button class="btn btn-lg btn-primary btn-block dark-green-btns" type="submit">
        Create Account
      </button>
    </form>
  </div>
</template>

<script>
import authService from "../services/AuthService";

export default {
  name: "register",
  data() {
    return {
      user: {
        username: "",
        password: "",
        confirmPassword: "",
        role: "user",
      },
      registrationErrors: false,
      registrationErrorMsg: "There were problems registering this user.",
    };
  },
  methods: {
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = "Password & Confirm Password do not match.";
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.$router.push({
                path: "/login",
                query: { registration: "success" },
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = "Bad Request: Validation Errors";
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = "There were problems registering this user.";
    },
  },
    mounted() {
      this.$nextTick(function () {
      this.$refs.focused.focus();
    });
  },
};
</script>

<style scoped>
h1 {
  padding: 15px;
  font-size: 24pt;
}

button {
  margin: 30px;
  background-color: #1a4314;
}

.login-btn {
  font-size: 12pt;
  color: #264653;
}

#register-input-table {
  padding: 0px;
  margin-top: 0px;
  border-spacing: 0px;
}

#register-input-table > tr,
td {
  padding: 0;
  border: none;
}

#username-row > td {
  padding-bottom: 20px;
}

#confirm-password-row > td {
  padding-top: 5px;
}

#login-link-div {
  margin-top: 20px;
}
</style>
