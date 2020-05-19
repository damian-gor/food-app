$(document).ready(function () {
    var url = window.location.origin + "/ui/recipes/";
    var resultsInput = "";
    var pageNumberInput = "";

    $("#search-recipe-random").click(function () {
        window.open(url + "random", "_self");
    });

    $("#search-recipe-byIngredients").click(function () {
        var input = $("#input-recipe-byIngredients").val();
        addSearchingParams();
        window.open(url + "byIngredients/" + input + resultsInput + pageNumberInput, "_self");
    });

    $("#search-recipe-byId").click(function () {
        var input = $("#input-recipe-byId").val();
        window.open(url + input, "_self");
    });

    $("#search-recipe-byText").click(function () {
        var input = $("#input-recipe-byText").val();
        addSearchingParams();
        window.open(url + "search/" + input + resultsInput + pageNumberInput, "_self");
    });

    $("#search-recipe-suitable").click(function () {
        var input = $("#input-recipe-suitable").val();
        addSearchingParams();
        window.open(url + "suitable/" + input + resultsInput + pageNumberInput, "_self");
    });

    $("#search-recipe-compromise").click(function () {
        var input = $("#input-recipe-compromise").val();
        addSearchingParams();
        window.open(url + "compromise/" + input + resultsInput + pageNumberInput, "_self");
    });

    // Add page number and inputs on page params
    function addSearchingParams(){
        if ($("#input-result-number").length && $("#input-page").length){
            resultsInput = "?number=" + $("#input-result-number").val();
            pageNumberInput = "&offset=" + ($("#input-page").val() - 1);
        }
    };
});