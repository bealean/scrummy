package com.bealean.scrummy.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.bealean.scrummy.model.RecipeAPI;
import com.bealean.scrummy.service.RecipeAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class RecipeAPIController {

    @Autowired
    RecipeAPIService recipeAPIService;

    @RequestMapping(path = "/recipes", method = RequestMethod.GET)
    public List<RecipeAPI> recipeAPIList(@RequestParam String ingredients, @RequestParam int limitNumber) throws JsonProcessingException {
        return recipeAPIService.getRecipesSearchByIngredients(ingredients, limitNumber);
    }

}
