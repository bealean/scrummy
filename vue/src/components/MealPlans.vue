<template>
  <div>
    <h1>My Meal Plans</h1>
    <div>
      <button class="large-button" @click="$router.push('create-meal-plan')">
        Create Meal Plan
      </button>
    </div>

    <div>
      <table
        id="my-meal-plans-table"
        class="table-numbered-rows"
        v-show="mealPlanList.length > 0"
      >
        <tr v-for="mealPlan in mealPlanList" v-bind:key="mealPlan.mealPlanId">
          <td class="row-number-cell" id="meal-plan-number-cell">
            {{ mealPlan.place }}
          </td>
          <td id="meal-plan-link-cell" class="link-cell">
            <router-link
              class="link"
              
              v-bind:to="{
                name: 'mealPlanDetails',
                params: { mealPlanId: mealPlan.mealPlanId },
              }"
            >
              {{ mealPlan.mealPlanName }}
            </router-link>
          </td>
          <td id="delete-btn-cell">
            <button
              class="delete-btn dark-green-btns"
              v-on:click.prevent="deleteMealPlan(mealPlan.mealPlanId)"
            >
              Delete
            </button>
          </td>
        </tr>
      </table>
    </div>
  </div>
</template>

<script>
import mealPlanService from "../services/MealPlanService.js";
export default {
  data() {
    return {
      mealPlanList: [],
      user: {
        userName: this.$store.state.user.username,
        id: this.$store.state.user.id,
      },
    };
  },
  methods: {
    deleteMealPlan(mealPlanId) {
      const choice = confirm("Are you sure you want to delete meal plan?");
      if (choice == true) {
        mealPlanService.deleteMealPlan(mealPlanId).then(() => {
          this.$router.go();
        });
      }
    },
  },
  created() {
    mealPlanService.getAllMealPlans().then((response) => {
      this.mealPlanList = response.data;
      for (let i = 0; i < this.mealPlanList.length; i++) {
        this.mealPlanList[i].place = i + 1;
      }
    }).catch((error)=> {
      if (error.response.status === 401) {
        alert("Session expired. Please sign in again.");
        this.$router.push("/login");
      } else {
        alert("Problem loading meal plans.");
        console.log("Problem loading meal plans: " + error);
      }
    });
  },
};
</script>

<style scoped>
td {
  background-color: rgba(255, 253, 253, 0.472);
  border-width: 2px;
}

#meal-plan-link-cell {
  padding-left: 80px;
  padding-right: 80px;
}

#meal-plan-number-cell {
  padding-left: 10px;
  padding-right: 10px;
}

#delete-btn-cell {
  padding: 0px;
  border: none;
  background-color: unset;
  vertical-align: center;
}

.delete-btn {
  background-color: #1a4314;
  width: 75px;
  height: 10%;
  padding: 0px;
  font-size: 12pt;
}
</style>