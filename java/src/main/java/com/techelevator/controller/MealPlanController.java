package com.techelevator.controller;

import com.techelevator.dao.DailyPlanDAO;
import com.techelevator.dao.MealPlanDAO;
import com.techelevator.dao.UserDAO;
import com.techelevator.exceptions.MealPlanNotFoundException;
import com.techelevator.exceptions.RecipeNotFoundException;
import com.techelevator.model.GroceryListItem;
import com.techelevator.model.MealPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

import static com.techelevator.controller.RecipeController.logTimestamp;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class MealPlanController {
    @Autowired
    MealPlanDAO mealPlanDAO;

    @Autowired
    DailyPlanDAO dailyPlanDAO;

    @Autowired
    UserDAO userDAO;

    @RequestMapping(path = "/meal-plans", method = RequestMethod.GET)
    public List<MealPlan> getMyMealPlans(Principal principal) throws RecipeNotFoundException {
        logTimestamp("Getting my meal plans");
        String username = principal.getName();
        long userId = userDAO.findIdByUsername(username);
        return mealPlanDAO.getAllMealPlansByUserId(userId);
    }

    @RequestMapping(path = "my-meal-plan", method = RequestMethod.GET)
    public MealPlan getMyMealPlan(Principal principal, @RequestParam String mealPlanName) throws MealPlanNotFoundException, RecipeNotFoundException {
        String username = principal.getName();
        long userId = userDAO.findIdByUsername(username);
        return mealPlanDAO.getMealPlanByName(userId, mealPlanName);
    }

    @RequestMapping(path = "/meal-plans/{mealPlanId}", method = RequestMethod.GET)
    public MealPlan getMealPlanById(@PathVariable long mealPlanId) throws MealPlanNotFoundException, RecipeNotFoundException {
        logTimestamp("Retrieving meal plan");
        return mealPlanDAO.getMealPlanById(mealPlanId);
    }

    @RequestMapping(path = "/grocery-list", method = RequestMethod.GET)
    public List<GroceryListItem> getIngredientsByMealPlanIds(@RequestParam long[] id) {
        logTimestamp("Retrieving grocery list");
        return mealPlanDAO.getIngredientsByMealPlanIds(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/add-new-meal-plan", method = RequestMethod.POST)
    public Long addNewMealPlan(@RequestParam String mealPlanName, Principal principal) {
        String username = principal.getName();
        long userId = userDAO.findIdByUsername(username);
        Long mealPlanId = mealPlanDAO.addMealPlan(mealPlanName, userId);
        logTimestamp("Adding new meal plan");
        return mealPlanId;
    }

    @RequestMapping(path = "/update-meal-plan/{mealPlanId}", method = RequestMethod.PUT)
    public void editMealPlan(@PathVariable long mealPlanId, @Valid @RequestBody MealPlan mealPlan) {
        mealPlanDAO.updateMealPlan(mealPlan);
        logTimestamp("Updating meal plan");
    }

    @RequestMapping(path = "/delete-meal-plan/{mealPlanId}", method = RequestMethod.DELETE)
    public void deleteMealPlan(@PathVariable long mealPlanId) {
        mealPlanDAO.deleteMealPlan(mealPlanId);
        logTimestamp("Deleting meal plan from database");
    }

}
