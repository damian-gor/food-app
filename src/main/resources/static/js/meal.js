$(document).ready(function () {

    var productsArray = productsOnStart;
    var indexes = [];
    for (let index = 1; index <= productsArray.length; index++) {
        indexes.push(index);
    }

    // BUTTON: show new product form 
    $("#show-newProduct-form").click(function () {
        $("#newProduct-form-container").toggleClass("hidden");
        if ($("#show-img").attr("src") == "/images/sort-down-solid.svg") $("#show-img").attr("src", "/images/sort-up-solid.svg");
        else $("#show-img").attr("src", "/images/sort-down-solid.svg");
    });



    // BUTTON: ADD NEW PRODUCT TO MEAL
    $("#newProductForm").submit(function (event) {
        event.preventDefault();
        var productId = $("#productId").val();
        var productAmount = $("#productAmount").val();
        productsArray.forEach(element => {
            if (element.productId == productId) {
                productAmount = parseInt(productAmount) + parseInt(element.grams);
                var indexToRemove = indexes.indexOf(parseInt($("table#mealElements tr#" + productId).find(".index").text()));
                if (indexToRemove !== -1) indexes.splice(indexToRemove, 1);
                $("table#mealElements tr#" + productId).remove();
            }
        });
        ajaxAddProduct(productId, productAmount);
    });



    // AJAX: ADD NEW PRODUCT TO MEAL
    function ajaxAddProduct(productId, productAmount) {
        var data = "{\"" + productId + "\":" + productAmount + "}"

        $.ajax({
            type: "PATCH",
            url: window.location.href,
            contentType: "application/json",
            data: data,
            dataType: 'json',
            success: function (textStatus, xhr, result) {
                if (xhr == "success") {
                    var meal = result.responseJSON;
                    productsArray = meal.products;
                    var product;
                    var doesProductExist = true;
                    productsArray.forEach(element => {
                        if (element.productName == null) {
                            doesProductExist = false;
                        }
                        if (element.productId == productId) {
                            product = element;
                        }
                    });

                    if (doesProductExist == true) {
                        $("#mealElements tr:last").before(
                            "<tr style='background-color:rgba(226, 224, 186, 0.966)' id=" + productId + ">" +
                            "<td class='index'>" + assignIndex(productsArray.length) + "</td>" +
                            "<td>" + product.productName + "</td>" +
                            "<td>" + product.grams + "</td>" +
                            "<td>" + product.productKcal + "</td>" +
                            "<td>" + product.productProtein + "</td>" +
                            "<td>" + product.productCarbs + "</td>" +
                            "<td>" + product.productFat + "</td>" +
                            "<td class='removable'><button type='button' class='btn btn-default btn-lg confirm deleteProduct' value=" + productId + ">" +
                            "<img src='/images/trash-alt-solid.svg'></button></td>" +
                            "<td><a href=" + product.links[0].href + "><button>Product details</button></a></td>" +
                            "</tr>");
                        actualizeResults(meal);
                    } else {
                        alert("There is no product with ID " + productId + " in our database. Please try agane.")
                    }
                }
            },
            error: function (xhr) {
                handleAjaxError(xhr);
            }
        });
    };

    // BUTTON: REMOVE PRODUCT FROM MEAL
    $(document).on("click", ".deleteProduct", function (event) {
        event.preventDefault();
        if (window.confirm("Are you sure?")) {
            var productId = jQuery(this).find("button").prevObject[0].value;
            ajaxDeleteProduct(productId, this.parentNode.parentNode);
        }
    });

    // AJAX: REMOVE PRODUCT FROM MEAL
    function ajaxDeleteProduct(productId, row) {
        var map = new Map();
        productsArray.forEach(element => {
            if (element.productId != productId) {
                map.set(element.productId, element.grams);
            }
        });
        $.ajax({
            type: "PUT",
            url: window.location.href,
            contentType: "application/json",
            data: JSON.stringify(Object.fromEntries(map)),
            dataType: 'json',
            success: function (textStatus, xhr, result) {
                if (xhr == 'success') {
                    var meal = result.responseJSON;
                    productsArray = meal.products;
                    actualizeResults(meal);
                    var indexToRemove = indexes.indexOf(parseInt($("table#mealElements tr#" + productId).find(".index").text()));
                    if (indexToRemove !== -1) indexes.splice(indexToRemove, 1);
                    row.remove();
                }
            },
            error: function (xhr) {
                handleAjaxError(xhr);
            }
        });
    };

    // Actualize summary after ajax call
    function actualizeResults(meal) {
        $(".total-kcal").text(meal.mealKcal);
        $(".total-protein").text(meal.mealProtein);
        $(".total-carbs").text(meal.mealCarbs);
        $(".total-fat").text(meal.mealFat);
    };

    // Assign index in table
    function assignIndex(numberOfProducts) {
        for (let index = 1; index <= numberOfProducts; index++) {
            if (!indexes.includes(index)) {
                indexes.push(index);
                return index;
            }
        };
        indexes.push(numberOfProducts + 1);
        return numberOfProducts + 1
    };

    // BUTTON: REMOVE MEAL
    $("#deleteMeal").click(function (event) {
        event.preventDefault();
        if (window.confirm("Are you sure?")) {
            ajaxDeleteMeal();
        }
    });

    // AJAX REMOVE MEAL
    function ajaxDeleteMeal() {
        var mealsLink = $("#mealsLink").attr("href");
        $.ajax({
            url: window.location.href,
            type: 'DELETE',
            success: function () {
                alert(
                    "Meal has been removed successfuly. Redirecting to the list of meals."
                );
                window.location.replace(mealsLink);
            },
            error: function (xhr) {
                handleAjaxError(xhr);
            }
        });
    };

    // BUTTON: search for products

    $("#btn-search-products").click(function () {
        $("#search-products").toggle();
    });

});