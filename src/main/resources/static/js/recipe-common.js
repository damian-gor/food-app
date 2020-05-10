$(document).ready(function () {
    var url = window.location.origin + "/ui/recipes/";

    $("#search-recipe-random").click(function () {
        window.open(url + "random", "_self");
    });

    $("#search-recipe-byIngredients").click(function () {
        var input = $("#input-recipe-byIngredients").val();
        window.open(url + "byIngredients/" + input, "_self");
    });

    $("#search-recipe-byId").click(function () {
        var input = $("#input-recipe-byId").val();
        window.open(url + input, "_self");
    });

    $("#search-recipe-byText").click(function () {
        var input = $("#input-recipe-byText").val();
        window.open(url + "search/" + input, "_self");
    });

    $("#search-recipe-suitable").click(function () {
        var input = $("#input-recipe-suitable").val();
        window.open(url + "suitable/" + input, "_self");
    });

    $("#search-recipe-compromise").click(function () {
        var input = $("#input-recipe-compromise").val();
        window.open(url + "compromise/" + input, "_self");
    });
});