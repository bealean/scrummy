<template>
  <div>
    <h1>Create Weekly Meal Plan</h1>
    <form class="meal-plan-form" v-on:submit.prevent="addNewMealPlan">
      <div class="new-meal-title">
        <input
          id="title"
          type="text"
          placeholder="enter name of meal plan here"
          minlength="1"
          maxlength="50"
          size="50"
          v-model="mealPlanName"
        />
      </div>
      <button
        class="meal-plan-btns dark-green-btns"
        type="submit"
        value="Submit"
      >
        Submit
      </button>
      <button
        class="meal-plan-btns dark-green-btns"
        v-on:click.prevent="cancelAddMealPlan()"
        type="cancel"
      >
        Go Back to Meal Plans
      </button>
    </form>
  </div>
</template>

<script>
import mealPlanService from "../services/MealPlanService.js";
export default {
  data() {
    return {
      mealPlanName: "",
    };
  },
  methods: {
    cancelAddMealPlan() {
      this.$router.push("/my-meal-plans");
    },
    addNewMealPlan() {
      mealPlanService
        .newMealPlan(this.mealPlanName)
        .then((response) => {
          if (response.status === 201) {
            this.$router.push(`/meal-plan-details/${response.data}`);
          }
        })
        .catch((error) => {
          alert("There was a problem adding the meal plan. Please retry.");
          console.error("Problem adding meal plan:" + error);
        });
    },
  },
};
</script>

<style scoped>
.meal-plan-btns {
  margin-left: 1rem;
  margin-right: 1rem;
}

.new-meal-title {
  margin-bottom: 1.5rem;
}

.meal-plan-form {
  padding: 2rem;
  width: fit-content;
}
</style>