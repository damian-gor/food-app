$(document).ready(function () {

    //// RECIPES - profile.html

// TO DO

    //// PRODUCTS - profile.html
    var areProductsLoaded = false;
    $("#showProducts").change(function () {
        $("#products-container").toggle();
        if (areProductsLoaded == false) {
            ajaxGetFavouritesProductsList();
            areProductsLoaded = true;
        }
    });

    function ajaxGetFavouritesProductsList() {
        if (favouriteProducts != null) {
            favouriteProducts.forEach(productId => {
                var ajaxLink = window.location.origin + "/products/" + productId;
                var productLink = window.location.origin + "/ui/products/" + productId;
                var deleteProductLink = window.location.origin + "/ui/products/" + productId;
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
                                "<td><button type='button' class='btn btn-default btn-lg confirm delete-favourite' value=" + productId + ">" +
                                "<i class='fas fa-trash-alt' aria-hidden='true'></i></button></td>" +
                                "</tr>");
                        }
                    },
                    error: function (e) {
                        console.log("ERROR: ", e);
                    }
                });
            });
        } else {
            $("#productsTable").append(
                "<p style='font-style: italic'>No favorite products added<p>"
            )
        }
    };


    // REMOVE PRODUCT FROM FAVOURITES
    $(document).on("click", ".delete-favourite", function (event) {
        event.preventDefault();
        if (window.confirm("Are you sure?")) {
            var productId = jQuery(this).find("button").prevObject[0].value;
            ajaxDeleteFavourite(null, productId, this.parentNode.parentNode);
        }
    });

    // REMOVE RECIPE FROM FAVOURITES

    // TO DO



    // AJAX DELETE FAVOURITES
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
            error: function (xhr, e) {
                if (xhr.status == 401) alert('Unauthorized! Please log in');
                else if (xhr.status == 403) alert('Forbidden!');
                else alert("Error!");
                console.log("ERROR: ", e);
            }
        });
    };



    //// RECIPES - recipes.html

// TO DO

    //// PRODUCTS - products.html


    $("#addToFavourites").click(function () {
        var autorization = auth;
        var productId = $("#productId").text();
        if (autorization.name == "anonymousUser") alert('Unauthorized! Please log in');
        else {
            var userId = autorization.principal.id;
            if (userId == 99999) {
                alert("Hello admin! Which user should I add the product to?")
                $("#admin-select-profile-id").toggle();
            } else {
                var productId = $("#productId").text();
                ajaxAddToFavourites(null, productId, userId);
            }
        }
    });


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
            error: function (xhr, e) {
                if (xhr.status == 401) alert('Unauthorized! Please log in');
                else if (xhr.status == 403) alert('Forbidden!');
                else alert("Error!");
                console.log("ERROR: ", e);
            }
        });
    };

    // INPUT BUTTON FOR ADMIN
    $("#addToThis-profile-id").click(function () {
        var profileId = $("#input-profile-id").val();
        var productId = $("#productId").text();
        ajaxAddToFavourites(null, productId, profileId);
        $("#admin-select-profile-id").toggle();
    });


});