package com.bealean.scrummy.controller;

import com.bealean.scrummy.dao.MealDAO;
import com.bealean.scrummy.exceptions.RecipeNotFoundException;
import com.bealean.scrummy.model.Meal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class MealController {

    @Autowired
    MealDAO mealDAO;

    @RequestMapping(path = "/meals/{mealId}", method = RequestMethod.GET)
    public Meal getMealById(@PathVariable long mealId) throws RecipeNotFoundException {
        RecipeController.logTimestamp("Getting meal by id");
        return mealDAO.getMealById(mealId);
    }

}
