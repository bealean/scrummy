package com.techelevator.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.techelevator.model.RecipeDetailsAPI;
import com.techelevator.service.RecipeDetailsAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class RecipeDetailsAPIController {

    @Autowired
    RecipeDetailsAPIService recipeDetailsAPIService;

    @RequestMapping(path = "/recipes/{id}", method = RequestMethod.GET)
    public RecipeDetailsAPI recipeDetailsById(@PathVariable long id) throws JsonProcessingException {
        return recipeDetailsAPIService.getRecipeDetailsById(id);
    }

}
