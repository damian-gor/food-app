$(document).ready(function () {

    /////////// RECIPES

    //// SHOW RECIPES BUTTON
    var areRecipesLoaded = false;
    $("#showRecipes").change(function () {
        $("#recipes-container").toggle();
        if (areRecipesLoaded == false) {
            ajaxGetFavouritesRecipesList();
            areRecipesLoaded = true;
        }
    });


    // LOAD FAVOURITE RECIPES LIST
    function ajaxGetFavouritesRecipesList() {
        if (favouriteRecipes != null) {
            favouriteRecipes.forEach(recipeId => {
                var ajaxLink = window.location.origin + "/recipes/" + recipeId;
                var recipeLink = window.location.origin + "/ui/recipes/" + recipeId;
                $.ajax({
                    type: "GET",
                    url: ajaxLink,
                    success: function (textStatus, xhr, response) {
                        var recipe = response.responseJSON;
                        if (xhr == 'success') {
                            $("#recipesTable tr:last").after(
                                "<tr>" +
                                "<td>" + recipe.recipeTitle + "</td>" +
                                "<td><a href=" + recipeLink + "><button>See recipe details</button></a></td>" +
                                "<td class='removable'><button type='button' class='btn btn-default btn-sm confirm delete-favourite-recipe' value=" + recipeId + ">" +
                                "<img src='/images/trash-alt-solid.svg'></button></td>" +
                                "</tr>");
                        }
                    },
                    error: function (xhr) {
                        handleAjaxError(xhr);
                    }
                });
            });
        } else {
            $("#productsTable").append(
                "<p style='font-style: italic'>No favorite recipes added<p>"
            )
        }
    };


    // REMOVE RECIPE FROM FAVOURITES
    $(document).on("click", ".delete-favourite-recipe", function (event) {
        event.preventDefault();
        if (window.confirm("Are you sure?")) {
            var recipeId = jQuery(this).find("button").prevObject[0].value;
            ajaxDeleteFavourite(recipeId, null, this.parentNode.parentNode);
        }
    });

    /////////// PRODUCTS

    //// SHOW PRODUCTS BUTTON
    var areProductsLoaded = false;
    $("#showProducts").change(function () {
        $("#products-container").toggle();
        if (areProductsLoaded == false) {
            ajaxGetFavouritesProductsList();
            areProductsLoaded = true;
        }
    });

    // LOAD FAVOURITE PRODUCTS LIST
    function ajaxGetFavouritesProductsList() {
        if (favouriteProducts != null) {
            favouriteProducts.forEach(productId => {
                var ajaxLink = window.location.origin + "/products/" + productId;
                var productLink = window.location.origin + "/ui/products/" + productId;
                $.ajax({
                    type: "GET",
                    url: ajaxLink,
                    success: function (textStatus, xhr, response) {
                        var product = response.responseJSON;
                        if (xhr == 'success') {
                            $("#productsTable tr:last").after(
                                "<tr>" +
                                "<td>" + product.productName + "</td>" +
                                "<td><a href=" + productLink + "><button>See product details</button></a></td>" +
                                "<td class='removable'><button type='button' class='btn btn-default btn-lg confirm delete-favourite-product' value=" + productId + ">" +
                                "<img src='/images/trash-alt-solid.svg'></button></td>" +
                                "</tr>");
                        }
                    },
                    error: function (xhr) {
                        handleAjaxError(xhr);
                    }
                });
            });
        } else {
            $("#productsTable").append(
                "<p style='font-style: italic'>No favorite products added<p>"
            )
        }
    };


    // REMOVE PRODUCT FROM FAVOURITES BUTTON
    $(document).on("click", ".delete-favourite-product", function (event) {
        event.preventDefault();
        if (window.confirm("Are you sure?")) {
            var productId = jQuery(this).find("button").prevObject[0].value;
            ajaxDeleteFavourite(null, productId, this.parentNode.parentNode);
        }
    });



    //// ADD PRODUCT TO FAVOURITES BUTTON
    $("#addToFavourites-product").click(function () {
        var autorization = auth;
        var productId = $("#productId").text();
        if (autorization.name == "anonymousUser") alert('Unauthorized! Please log in');
        else {
            var userId = autorization.principal.id;
            if (userId == 99999) {
                alert("Hello admin! Which user should I add the product to?")
                $("#product-admin-select-profile-id").toggle();
            } else {
                var productId = $("#productId").text();
                ajaxAddToFavourites(null, productId, userId);
            }
        }
    });

    //// ADD RECIPE TO FAVOURITES BUTTON
    $("#addToFavourites-recipe").click(function () {
        var autorization = auth;
        var recipeId = $("#recipeId").text();
        if (autorization.name == "anonymousUser") alert('Unauthorized! Please log in');
        else {
            var userId = autorization.principal.id;
            if (userId == 99999) {
                alert("Hello admin! Which user should I add the recipe to?")
                $("#recipe-admin-select-profile-id").toggle();
            } else {
                var recipeId = $("#recipeId").text();
                ajaxAddToFavourites(recipeId, null, userId);
            }
        }
    });

    // AJAX REMOVE RECIPE / PRODUCT FROM FAVOURITES
    function ajaxDeleteFavourite(recipeIds, productIds, row) {
        var queryParams = {
            recipesIds: recipeIds,
            productsIds: productIds
        }

        $.ajax({
            url: window.location.href + "/favourites?" + $.param(queryParams),
            type: 'DELETE',
            success: function (textStatus, xhr) {
                if (xhr == 'success') {
                    row.remove();
                }
            },
            error: function (xhr) {
                handleAjaxError(xhr);
            }
        });
    };

    // ADD RECIPE / PRODUCT TO FAVOURITES
    function ajaxAddToFavourites(recipeIds, productIds, profileId) {
        var queryParams = {
            recipesIds: recipeIds,
            productsIds: productIds
        }
        $.ajax({
            url: window.location.origin + "/ui/profiles/" + profileId + "/favourites?" + $.param(queryParams),
            type: 'GET',
            success: function (textStatus, xhr) {
                if (xhr == 'success') {
                    alert("Product has been added to favourites successfully!")
                }
            },
            error: function (xhr) {
                handleAjaxError(xhr);
            }
        });
    };

    // INPUT BUTTON FOR ADMIN - PRODUCT
    $("#product-addToThis-profile-id").click(function () {
        var profileId = $("#product-input-profile-id").val();
        var productId = $("#productId").text();
        ajaxAddToFavourites(null, productId, profileId);
        $("#product-admin-select-profile-id").toggle();
    });

    // INPUT BUTTON FOR ADMIN - RECIPE
    $("#recipe-addToThis-profile-id").click(function () {
        var profileId = $("#recipe-input-profile-id").val();
        var recipeId = $("#recipeId").text();
        ajaxAddToFavourites(recipeId, null, profileId);
        $("#recipe-admin-select-profile-id").toggle();
    });

});