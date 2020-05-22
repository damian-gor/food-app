$(document).ready(function () {

    var profileLink = $("#profileLink").prop('href');
    var profileJSONLink = $("#profileLink").prop('href').replace('ui/', '');
    
    // Getting profile name
    $.ajax({
        type: "GET",
        url: profileJSONLink,
        success: function (textStatus, xhr, response) {
            var profileName = response.responseJSON.name;
            if (xhr == 'success') {
                $('#profileName').text(profileName);
            }
        },
        error: function (xhr) {
            handleAjaxError(xhr);
        }
    });

        // DELETE FOOD-DIARY BUTTON
        $("#delete").click(function (event) {
            event.preventDefault();
            if (window.confirm("Are you sure?")) {
                ajaxDeleteFoodDiary();
            }
        });

        // DELETE FOOD-DIARY
        function ajaxDeleteFoodDiary() {
            $.ajax({
                url: window.location.href,
                type: 'DELETE',
                success: function (result) {
                    console.log(result);
                    alert(
                        'FoodDiary has been removed successfuly. Redirecting to Profile.'
                    );
                    window.location.replace(profileLink);
                },
                error: function (xhr) {
                    handleAjaxError(xhr);
                }
            });
        };
});