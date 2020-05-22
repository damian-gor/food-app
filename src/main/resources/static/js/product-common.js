$(document).ready(function () {
    var url = window.location.origin;
    var alertText = "Please enter query first";

    $("#search-product-general").click(function () {
        var input = $("#input-product-general").val();
        if (input == "") alert(alertText);
        else window.open(url + "/ui/products/search-general/" + input, "_self");
    });

    $("#search-product-specific").click(function () {
        var input = $("#input-product-specific").val();
        if (input == "") alert(alertText);
        else window.open(url + "/ui/products/search-specific/" + input, "_self");
    });

    $("#search-product-id").click(function () {
        var input = $("#input-product-id").val();
        if (input == "") alert(alertText);
        else window.open(url + "/ui/products/" + input, "_self");
    });

});