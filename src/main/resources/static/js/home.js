$(document).ready(function () {

// SEARCH RECIPE
    var btnRecipe = document.getElementById("search-recipe");
    btnRecipe.addEventListener("click", function () {
        var input = $("#input-recipe").val();
        window.open(window.location + "/recipes/search/" + input);
        $("#input-recipe").val("");
    });


// SEARCH PRODUCTS
    var btnProduct = document.getElementById("search-product");
    btnProduct.addEventListener("click", function () {
        var input = $("#input-product").val();
        window.open(window.location + "/products/search-general/" + input);
        $("#input-product").val("");
    });

    // SEARCH PROFILE
    var btnProfile = document.getElementById("search-profile");
    btnProfile.addEventListener("click", function () {
        var input = $("#input-profile").val();
        window.open(window.location + "/profiles/" + input);
        $("#input-profile").val("");
    });

    // RESET INPUTS
    $("button").click(function () {
        $("table").trigger("reset");
    });
});