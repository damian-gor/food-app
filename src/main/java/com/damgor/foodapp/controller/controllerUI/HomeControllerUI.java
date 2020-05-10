package com.damgor.foodapp.controller.controllerUI;

import com.damgor.foodapp.model.Message;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@ApiIgnore
public class HomeControllerUI {

    @GetMapping("/ui")
    public ModelAndView getHomePage(Model model) {
        ModelAndView modelAndView = new ModelAndView("home");
        Message message = new Message("Welcome to food-app! Feel free to check up food-app's features :) Some of them" +
                " are available below:");
        message.add(
                linkTo(methodOn(RecipeControllerUI.class).getRecipesByText(5,0,"cake with carrots without nuts", null)).withRel("Search for recipes"),
                linkTo(methodOn(ProductControllerUI.class).getGeneralProduct("goat milk", null)).withRel("Search for products"),
                linkTo(methodOn(ProfileControllerUI.class).getAllProfiles()).withRel("Check our profiles")
        );
        modelAndView.addObject("message", message);
        return modelAndView;
    }
}
