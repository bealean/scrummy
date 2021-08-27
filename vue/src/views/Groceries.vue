<template>
  <div class="view-div">
    <h1>Grocery List</h1>
    <div id="save-ingredient-div">
      <form
        id="add-new-ingredient-to-grocery-list-form"
        v-on:submit.prevent="createGroceryItem()"
      >
        <input
          type="text"
          v-model="newItem"
          placeholder="Add grocery list item"
        />
        <button
          type="submit"
          id="btn-save-grocery-item"
          class="dark-green-btns"
        >
          Save
        </button>
      </form>
    </div>
    <div id="main-grocery-list-div">
      <table id="grocery-list-table">
        <th id="to-buy-header">To Buy</th>
        <tr>
          <div id="search-row-div">
            <td id="search-row">
              <div id="search-label-div">
                <label id="search-items-label" for="search-field"
                  >Search Items
                </label>
              </div>
              <input
                id="search-field"
                type="text"
                v-model="filterItems"
                placeholder="search items"
              />
            </td>
          </div>
        </tr>
        <tr v-for="item in filteredItems" v-bind:key="item.name">
          <div v-bind:class="item.done ? 'checked' : 'unchecked'">
            <td class="grocery-item-cells">
              <input
                type="checkbox"
                v-model="item.done"
                @change="moveItem(item)"
              />

              <label>
                {{ item.name }}
                <span class="delete" v-on:click="deleteGroceryItem(item)">
                  ×</span
                ></label
              >
            </td>
          </div>
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
      filterItems: "",
      newItem: "",
      groceryItems: [],
    };
  },
  methods: {
    deleteGroceryItem(itemToDelete) {
      this.groceryItems = this.groceryItems.filter((item) => {
        return item !== itemToDelete;
      });
    },

    createGroceryItem() {
      const newItemObject = { name: this.newItem, done: false };
      this.groceryItems.unshift(newItemObject);
      this.newItem = "";
    },

    moveItem(item) {
      if (item.done === true) {
        this.deleteGroceryItem(item);
        this.groceryItems.push(item);
      } else {
        this.deleteGroceryItem(item);
        this.groceryItems.unshift(item);
      }
    },
  },
  computed: {
    filteredItems() {
      return this.groceryItems.filter((item) => {
        return item.name.includes(this.filterItems);
      });
    },
  },
  created() {
    mealPlanService
      .getIngredientsByMealPlanId(this.$route.params.mealPlanId)
      .then((ingredient) => {
        this.groceryItems = ingredient.data;
      });
  },
};
</script>

<style scoped>
.view-div {
  width: 100%;
}

#save-ingredient-div {
  background: #94c973;
  border-radius: 25px;
  width: 90%;
  min-width: fit-content;
  max-width: 700px;
  margin: 0px auto 20px auto;
  padding: 10px;
  display: flex;
  align-content: center;
  justify-content: center;
}

#add-new-ingredient-to-grocery-list-form {
  width: 100%;
  border: 5px dotted;
  margin: 0px;
  padding-right: 15px;
  padding-left: 15px;
}

#btn-save-grocery-item {
  width: 100px;
  height: 30px;
  margin-left: 10px;
  margin-top: 5px;
  margin-bottom: 0px;
}

#main-grocery-list-div {
  width: 90%;
  max-width: 650px;
  min-width: fit-content;
  margin: auto;
}

#grocery-list-table {
  width: 100%;
}

#to-buy-header {
  border-radius: 25px;
}

#search-row-div {
  margin-top: 20px;
  margin-bottom: 20px;
  margin-left: auto;
  margin-right: auto;
  display: flex;
  align-content: center;
  justify-content: center;
}

#search-row {
  border: 5px dotted;
  border-radius: 25px;
  width: 90%;
  min-width: fit-content;
  padding: 10px;
  text-align: center;
}

#search-label-div {
  margin-bottom: 10px;
  margin-top: 10px;
  display: inline-block;
}

#search-items-label {
  background-color: #1a4314;
  color: white;
  padding-left: 10px;
  padding-right: 10px;
  margin: 5px;
}

#search-field {
  margin: 5px;
}

.grocery-item-cells {
  display: flex;
  align-items: center;
  margin-left: 10px;
}

input[type="checkbox"] {
  position: absolute;
}

.unchecked {
  text-align: left;
}

.checked {
  background-color: rgb(155, 153, 153);
  border-radius: 25px;
  text-decoration: line-through;
  text-align: left;
}

.delete {
  color: red;
  cursor: pointer;
  text-decoration: none;
}

input[type="checkbox"] ~ label {
  display: inline-block;
  border-radius: 15px;
  text-align: left;
  padding: 5px;
  margin-left: 25px;
}
</style>