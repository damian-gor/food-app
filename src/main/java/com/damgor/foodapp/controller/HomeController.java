package com.damgor.foodapp.controller;

import com.damgor.foodapp.model.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class HomeController {

    @GetMapping("/")
    public Message getHomePage() {
        Message message = new Message("Welcome to food-app! Feel free to check up food-app's features :) Some of them" +
                " are available below:");
        message.add(
                linkTo(methodOn(RecipeController.class).getRecipesByText("Insert-text-here", null)).withRel("Search for recipes"),
                linkTo(methodOn(ProductController.class).getGeneralProducts("Insert-product-name-here", null)).withRel("Search for products"),
                linkTo(methodOn(ProfileController.class).getAllProfiles()).withRel("Check our profiles. Advanced " +
                        "functionalities of this module are available only to logged in users ")
        );
        return message;
    }
}
