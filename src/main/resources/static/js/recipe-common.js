$(document).ready(function () {
    var url = window.location.origin + "/ui/recipes/";
    var resultsInput = "";
    var pageNumberInput = "";
    var alertText = "Please enter query first";

    $("#search-recipe-random").click(function () {
        window.open(url + "random", "_self");
    });

    $("#search-recipe-byIngredients").click(function () {
        var input = $("#input-recipe-byIngredients").val();
        if (input == "") alert(alertText);
        else {
            addSearchingParams();
            window.open(url + "byIngredients/" + input + resultsInput + pageNumberInput, "_self");
        }
    });

    $("#search-recipe-byId").click(function () {
        var input = $("#input-recipe-byId").val();
        if (input == "") alert(alertText);
        else {
            window.open(url + input, "_self");
        }
    });

    $("#search-recipe-byText").click(function () {
        var input = $("#input-recipe-byText").val();
        if (input == "") alert(alertText);
        else {
            addSearchingParams();
            window.open(url + "search/" + input + resultsInput + pageNumberInput, "_self");
        }
    });

    $("#search-recipe-suitable").click(function () {
        var input = $("#input-recipe-suitable").val();
        if (input == "") alert(alertText);
        else {
            addSearchingParams();
            window.open(url + "suitable/" + input + resultsInput + pageNumberInput, "_self");
        }
    });

    $("#search-recipe-compromise").click(function () {
        var input = $("#input-recipe-compromise").val();
        if (input == "") alert(alertText);
        else {
            addSearchingParams();
            window.open(url + "compromise/" + input + resultsInput + pageNumberInput, "_self");
        }
    });

    // Add page number and inputs on page params
    function addSearchingParams() {
        if ($("#input-result-number").length && $("#input-page").length) {
            resultsInput = "?number=" + $("#input-result-number").val();
            pageNumberInput = "&offset=" + ($("#input-page").val() - 1);
        }
    };
});