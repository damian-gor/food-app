package com.damgor.foodapp.controller;

import com.damgor.foodapp.model.Message;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@Api(tags = "0. Home", description = "Starting page")
public class HomeController {

    @GetMapping("/")
    @ApiOperation(value= "Homepage",
            notes = "Starting page, available to everyone.",
            response = Message.class)
    public Message getHomePage() {
        Message message = new Message("Welcome to food-app! Feel free to check up food-app's features :) Some of them" +
                " are available below:");
        message.add(
                linkTo(methodOn(RecipeController.class).getRecipesByText(5,0,"Insert-text-here", null)).withRel("Search for recipes"),
                linkTo(methodOn(ProductController.class).getGeneralProduct("Insert-product-name-here", null)).withRel("Search for products"),
                linkTo(methodOn(ProfileController.class).getAllProfiles()).withRel("Check our profiles. Advanced " +
                        "functionalities of this module are available only to logged in users ")
        );
        return message;
    }
}
