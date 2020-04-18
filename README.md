# food-app
Overall description:
The application is designed to facilitate diet by calculating basic indicators (e.g. BMI), determining caloric 
demand, searching for recipes and products (from external databases), which can then be added to the nutrition 
diary, which allows you to track currently consumed meals in relation to for the nutritional purposes set by 
the user.

The application has been divided into the following modules:
1) Product
Search for food products by name or ID. General products like "milk", "eggs", or specific, like "Snickers Candy 
Bars". Products can also be saved as favorites for a given profile.

2) Recipe
Search for recipes by text, ingredients or id. Possibility to draw a recipe, search for a recipe corresponding 
to a given profile or a recipe corresponding to a selected group of profiles.

3) Proflie
The ability to manage profiles, specifying the name, type of diet, intolerances, preferred cuisine (choosing 
the available options, thanks to which you can then customize the recipe search). You can add your favorite 
products and recipes to the profile.

4) Profile Details
Determining height, weight, nutritional goal (e.g. weight loss) or activity level. On this basis, the application 
will calculate the caloric demand recommended to achieve the given goal.

5) Food Diary
Each profile can have a food diary. It consists of diary pages, corresponding to individual days, which in 
turn consist of meals. The diary owner can add meals by selecting products from the Product module and specifying 
their quantity. The application will calculate the calories consumed during the day and compare them with the 
caloric demand from the recommendation engine. Information on consumed macronutrients (carbs, proteins, fats) 
will also be available.

Security:
Recipes and Products modules are available to everyone, as well as the opportunity to see basic information 
about profiles (GET method). Operations (PUT / POST / PATCH / DELETE) on the profile and food diary can only be 
performed by the logged-in owner of the profile. Profile details (also the GET motoda) are only available to 
the profile owner because it contains sensitive information (such as weight). Diaries of other profiles can be 
viewed by any logged users.
Admin has access to all application functionalities.

Database:
H2 (In-Memory Database). Starting data is initialized by the application automatically.

Introduced among others:
- Exception handling
- HATEOAS Driven REST API concept (using Spring HATEOAS)
- Swagger (URL: http://localhost:8080/swagger-ui.html)

Other items to add:
- Tests

#### credentials ###

#admin
username: admin
password: pass
#profile1
username: user1
password: pass
#profile2
username: user2
password: pass
#profile3
username: user3
password: pass