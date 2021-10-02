import Vue from 'vue'
import Router from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import Logout from '../views/Logout.vue'
import Register from '../views/Register.vue'
import store from '../store/index'
import SearchRecipes from '../views/SearchRecipes.vue'
import RecipeDetails from '../views/RecipeDetails.vue'
import MyRecipeDetails from '../views/MyRecipeDetails.vue'
import EditRecipe from '../views/EditRecipe.vue'
import SaveRecipe from '../views/SaveRecipe.vue'
import MyRecipes from '../views/MyRecipes.vue'
import CreateNewRecipe from '../views/CreateNewRecipe.vue'
import MyMealPlans from '../views/MyMealPlans.vue'
import CreateMealPlan from '../views/CreateMealPlan.vue'
import MealPlanDetails from '../views/MealPlanDetails.vue'
import MealDetails from '../components/MyMeal.vue'
import Groceries from '../views/Groceries.vue'


Vue.use(Router)

/**
 * The Vue Router is used to "direct" the browser to render a specific view component
 * inside of App.vue depending on the URL.
 *
 * It also is used to detect whether or not a route requires the user to have first authenticated.
 * If the user has not yet authenticated (and needs to) they are redirected to /login
 * If they have (or don't need to) they're allowed to go about their way.
 */

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/login",
      name: "login",
      component: Login,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/logout",
      name: "logout",
      component: Logout,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/register",
      name: "register",
      component: Register,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/search-recipes",
      name: "searchRecipes",
      component: SearchRecipes,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/recipe-details/:id",
      name: "recipeDetails",
      component: RecipeDetails,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/my-recipe-details/:id",
      name: "myRecipeDetails",
      component: MyRecipeDetails,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/edit-recipe/:id",
      name: "editRecipe",
      component: EditRecipe,
      meta: {
        requiresAuth: true,
      }
    },
    {
      path: "/save-recipe/:id",
      name: "saveRecipe",
      component: SaveRecipe,
      meta: {
        requiresAuth: true,
      }
    },
    {
      path: "/my-recipes",
      name: "myRecipes",
      component: MyRecipes,
      meta: {
        requiresAuth: true,
      }
    },
    {
      path: "/create-recipe",
      name: "createNewRecipe",
      component: CreateNewRecipe,
      meta: {
        requiresAuth: true,
      }
    },
    {
      path: "/my-meal-plans",
      name: "myMealPlans",
      component: MyMealPlans,
      meta: {
        requiresAuth: true,
      }
    },
    {
      path: "/create-meal-plan",
      name: "createMealPlan",
      component: CreateMealPlan,
      meta: {
        requiresAuth: true,
      }
    },
    {
      path: "/meal-plan-details/:mealPlanId",
      name: "mealPlanDetails",
      component: MealPlanDetails,
      meta: {
        requiresAuth: true,
      }
    },
    {
      path: "/meal-plan-details/:mealPlanId/meals/:mealId",
      name: "mealDetails",
      component: MealDetails,
      meta: {
        requiresAuth: true,
      }
    },
    {
      path: "/grocery-list",
      name: "groceriesForPlans",
      component: Groceries,
      meta: {
        requiresAuth: true,
      }
    },

  ],
  scrollBehavior() {
    return { x: 0, y: 0 }
  }
})

router.beforeEach((to, from, next) => {

  store.commit("SET_PREV_ROUTE", from.path);

  // Determine if the route requires Authentication
  const requiresAuth = to.matched.some(x => x.meta.requiresAuth);

  // If it does and they are not logged in, send the user to "/login"
  if (requiresAuth && store.state.token === '') {
    next("/login");
  } else {
    // Else let them go to their next destination
    next();
  }
});

export default router;
