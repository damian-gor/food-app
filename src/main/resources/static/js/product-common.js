$(document).ready(function () {
    var url = window.location.origin;
    

    var btnProductGeneral = document.getElementById("search-product-general");
    btnProductGeneral.addEventListener("click", function () {
        var input = $("#input-product-general").val();
        window.open(url + "/ui/products/search-general/" + input, "_self");
    });

    var btnProductSpecific = document.getElementById("search-product-specific");
    btnProductSpecific.addEventListener("click", function () {
        var input = $("#input-product-specific").val();
        window.open(url + "/ui/products/search-specific/" + input, "_self");
    });

    var btnProductId = document.getElementById("search-product-id");
    btnProductId.addEventListener("click", function () {
        var input = $("#input-product-id").val();
        window.open(url + "/ui/products/" + input, "_self");
    });

});