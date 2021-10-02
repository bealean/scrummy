<template>
  <div>
    <h1>My Meal Plans</h1>
    <div id="my-meal-plans-buttons-div">
      <button class="large-button" @click="$router.push('create-meal-plan')">
        Create Meal Plan
      </button>
      <div v-if="selectedMealPlanIds">
        <button
          id="my-meal-plans-grocery-list-button"
          class="large-button"
          v-on:click="createGroceryList"
        >
          Create Grocery List <br />for Selected Plans
        </button>
      </div>
    </div>

    <div>
      <table
        id="my-meal-plans-table"
        class="table-numbered-rows"
        v-show="mealPlanList.length > 0"
      >
        <tr v-for="mealPlan in mealPlanList" v-bind:key="mealPlan.mealPlanId">
          <td class="row-number-cell meal-plan-number-cell">
            {{ mealPlan.place }}
          </td>
          <td class="link-cell meal-plan-link-cell">
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
          <td class="delete-btn-cell">
            <button
              class="delete-btn dark-green-btns"
              v-on:click.prevent="deleteMealPlan(mealPlan.mealPlanId)"
            >
              Delete
            </button>
          </td>
          <td class="plain-checkbox">
            <input
              type="checkbox"
              v-on:click="
                updateSelectedMealPlanIds($event, mealPlan.mealPlanId)
              "
            />
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
      selectedMealPlanIds: [],
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
    updateSelectedMealPlanIds(event, mealPlanId) {
      if (event.target.checked === true) {
        if (!this.selectedMealPlanIds.includes(mealPlanId)) {
          this.selectedMealPlanIds.push(mealPlanId);
        }
      } else {
        this.selectedMealPlanIds = this.selectedMealPlanIds.filter((id) => {
          return id !== mealPlanId;
        });
      }
    },
    createGroceryList() {
      if (this.selectedMealPlanIds.length > 0) {
        this.$router.push({
          path: "grocery-list",
          query: { id: this.selectedMealPlanIds },
        });
      } else {
        alert(
          "No meal plans selected. Please check the box for a meal plan before generating grocery list"
        );
      }
    },
  },
  created() {
    this.$store.commit("SET_IS_LOADING", true);
    mealPlanService
      .getAllMealPlans()
      .then((response) => {
        this.mealPlanList = response.data;
        for (let i = 0; i < this.mealPlanList.length; i++) {
          this.mealPlanList[i].place = i + 1;
        }
        this.$store.commit("SET_IS_LOADING", false);
      })
      .catch((error) => {
        this.$store.commit("SET_IS_LOADING", false);
        if (error.response && error.response.status === 401) {
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
#my-meal-plans-buttons-div {
  display: flex;
  justify-content: center;
}

#my-meal-plans-grocery-list-button {
  font-size: 19px;
}

td {
  background-color: rgba(255, 253, 253, 0.472);
  border-width: 2px;
}

.plain-checkbox {
  background-color: #94c973;
  border-width: 0px;
}

.meal-plan-link-cell {
  padding-left: 80px;
  padding-right: 80px;
}

.meal-plan-number-cell {
  padding-left: 10px;
  padding-right: 10px;
}

.delete-btn-cell {
  padding: 0px;
  border: none;
  background-color: unset;
  /* If line-height (minimum) is not specified as 0, 
   button is not centered vertically in cell 
   due to extra space allocated around button based on font-size. */
  line-height: 0px;
}

.delete-btn {
  background-color: #1a4314;
  width: 75px;
  height: 10%;
  padding: 0px;
  font-size: 15px;
  margin: 0px;
}
</style>