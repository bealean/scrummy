<template>
  <div>
    <head>
      <link
        href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.14.0/css/all.min.css"
        rel="stylesheet"
      />
    </head>
    <form class="meal-plan-form" v-on:submit.prevent="addNewMealPlan">
      <div class="new-meal-title">
        <div>
          <input
            id="title"
            type="text"
            placeholder="enter name of meal plan here"
            minlength="1"
            maxlength="500"
            size="75"
            v-model="newMealPlan.mealPlanName"
          />
        </div>
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
    <br />
    <div
      class="daily-plan"
      v-for="dailyPlan in newMealPlan.dailyPlanList"
      v-bind:key="dailyPlan.dpId"
    >
      {{ dailyPlan.weekday }}
      <div v-for="meal in dailyPlan.meals" v-bind:key="meal.mealId">
        {{ meal.mealType }}
      </div>
    </div>
  </div>
</template>

<script>
import mealPlanService from "../services/MealPlanService.js";
import recipeService from "../services/RecipeService.js";
export default {
  data() {
    return {
      newMealPlan: {
        mealPlanId: "",
        userId: "",
        mealPlanName: "",
        dailyPlanList: [
          {
            dpId: "",
            weekday: "",
            meals: [
              {
                recipes: [],
              },
            ],
            mealPlanId: "",
          },
        ],
      },
      myRecipes: [],
      user: {
        userName: this.$store.state.user.username,
        id: this.$store.state.user.id,
      },
    };
  },
  methods: {
    addNewMealPlan() {
      const createdMealPlan = {
        userId: this.$store.state.user.id,
        mealPlanName: this.newMealPlan.mealPlanName,
        dailyPlanList: [],
      };

      mealPlanService
        .newMealPlan(createdMealPlan)
        .then((response) => {
          if (response.status === 201) {
            mealPlanService
              .getMealPlanByName(this.newMealPlan.mealPlanName)
              .then((response) => {
                this.newMealPlan = response.data;
                this.$router.push(
                  `/meal-plan-details/${this.newMealPlan.mealPlanId}`
                );
              });
          }
        })
        .catch((error) => {
          alert("There was a problem adding the meal plan. Please retry.");
          console.error("Problem adding meal plan:" + error);
        });
    },
    cancelAddMealPlan() {
      this.$router.push("/my-meal-plans");
    },
  },
  created() {
    recipeService
      .getAllRecipes()
      .then((response) => {
        this.myRecipes = response.data;
      })
      .catch((error) => {
        alert("There was a problem retrieving recipes.");
        console.error("Problem retrieving recipes:" + error);
      });
  },
};
</script>

<style scoped>
.meal-plan-btns {
  background-color: #1a4314;
  width: 250px;
  height: 45px;
  margin: 20px;
  padding: 0px;
}

.meal-plan-form {
  padding-top: 50px;
}
</style>