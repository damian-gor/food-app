$(document).ready(function () {

    // ADD NEW MEAL
    $("#add-meal").click(function (event) {
        event.preventDefault();
        ajaxPostMeal();
    });

    function ajaxPostMeal() {
        $.ajax({
            type: "POST",
            url: window.location.href,
            contentType: "application/json",
            data: JSON.stringify({}),
            dataType: 'json',
            success: function (textStatus, xhr, result) {
                var meal = result.responseJSON;
                if (xhr == "success") {
                    $(".table tr:last").after(
                        "<tr style='background-color:rgba(226, 224, 186, 0.966)'>" +
                        "<td>" + meal.id.mealNumber + "</td>" +
                        "<td>" + meal.mealKcal + "</td>" +
                        "<td><a href=" + meal.links[0].href + "><button>Meal details</button></a></td>" +
                        "</tr>");
                }
            },
            error: function (xhr) {;
                handleAjaxError(xhr)
            }
        });
    };
})