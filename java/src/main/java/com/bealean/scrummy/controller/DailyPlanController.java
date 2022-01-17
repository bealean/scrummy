package com.bealean.scrummy.controller;

import com.bealean.scrummy.dao.DailyPlanDAO;
import com.bealean.scrummy.exceptions.DailyPlanNotFoundException;
import com.bealean.scrummy.exceptions.RecipeNotFoundException;
import com.bealean.scrummy.model.DailyPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bealean.scrummy.controller.RecipeController.logTimestamp;

@RestController
@PreAuthorize("isAuthenticated()")
@CrossOrigin
public class DailyPlanController {

    @Autowired
    DailyPlanDAO dailyPlanDAO;

    @RequestMapping(path = "/my-daily-plans", method = RequestMethod.GET)
    public List<DailyPlan> returnMyDailyPlans(@RequestParam long mealPlanId) throws RecipeNotFoundException {
        logTimestamp("Getting my daily plans");
        return dailyPlanDAO.getDailyPlansByMealPlanId(mealPlanId);
    }

    @RequestMapping(path = "/daily-plans/{id}", method = RequestMethod.GET)
    public DailyPlan returnDailyPlanById(@PathVariable long id) throws RecipeNotFoundException, DailyPlanNotFoundException {
        logTimestamp("Getting daily plan details");
        return dailyPlanDAO.getDailyPlanByDailyPlanId(id);
    }

    @RequestMapping(path = "/add-new-daily-plan", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void addDailyPlan(@RequestBody DailyPlan dailyPlan) {
        dailyPlanDAO.addDailyPlanToMealPlan(dailyPlan);
        logTimestamp("Adding new daily plan to meal plan");
    }

    @RequestMapping(path = "/update-daily-plan/{dpId}", method = RequestMethod.PUT)
    public void editDailyPlan(@PathVariable long dpId, @RequestBody DailyPlan dailyPlan) {
        dailyPlanDAO.updateDailyPlan(dailyPlan);
        logTimestamp("Updating daily plan");
    }

    @RequestMapping(path = "/delete-daily-plan/{dpId}", method = RequestMethod.DELETE)
    public void deleteMyDailyPlan(long dpId) {
        dailyPlanDAO.deleteDailyPlan(dpId);
        logTimestamp("Deleting daily plan");
    }

}
