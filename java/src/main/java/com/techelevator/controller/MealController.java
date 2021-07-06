package com.techelevator.controller;

import com.techelevator.dao.MealDAO;
import com.techelevator.exceptions.RecipeNotFoundException;
import com.techelevator.model.Meal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.techelevator.controller.RecipeController.logTimestamp;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class MealController {

    @Autowired
    MealDAO mealDAO;

    @RequestMapping(path = "/meals/{mealId}", method = RequestMethod.GET)
    public Meal getMealById(@PathVariable long mealId) throws RecipeNotFoundException {
        logTimestamp("Getting meal by id");
        return mealDAO.getMealById(mealId);
    }

}
