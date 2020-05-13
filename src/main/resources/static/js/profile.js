$(document).ready(function () {


    var defaultDescDiet = "Select diet to get description";

    // DELETE PROFILE BUTTON
    $("#delete").click(function (event) {
        event.preventDefault();
        if (window.confirm("Are you sure?")) {
            ajaxDelete();
        }
    });

    // SUBMIT FORM BUTTON
    $("#profileForm").submit(function (event) {
        event.preventDefault();
        ajaxUpdateProfile();
    });

    // DELETE PROFILE
    function ajaxDelete() {
        $.ajax({
            url: window.location.href,
            type: 'DELETE',
            success: function (result) {
                console.log(result);
                alert(
                    'Profile has been removed successfuly. Redirecting to all profiles list.'
                );
                window.location.replace(result.links[0].href);
            },
            error: function (xhr, e) {
                if (xhr.status == 401) alert('Unauthorized! Please log in');
                else if (xhr.status == 403) alert('Forbidden!');
                else alert("Error!");
                console.log("ERROR: ", e);
            }
        });
    };

    // UPDATE PROFILE
    function ajaxUpdateProfile() {

        var diet, cuisine, intolerance, name = null;
        if ($("#diet").val() != "none") diet = $("#diet").val();
        if ($("#cuisine").val() != "none") cuisine = $("#cuisine").val();
        if ($("#intolerance").val() != "none") intolerance = $("#intolerance").val();
        var formData = {
            name: $("#name").val(),
            diet: diet,
            cuisine: cuisine,
            intolerance: intolerance
        }

        $.ajax({
            type: "PATCH",
            contentType: "application/json",
            url: window.location.href,
            data: JSON.stringify(formData),
            dataType: 'json',
            success: function (data, textStatus, xhr) {
                var profile = xhr.responseJSON;
                if (xhr.status == 200) {
                    alert("Profile has  been updated successfully!");
                    $("#profile-form-container").toggle();
                    $("#profile-name").text(profile.name);
                    $("#profile-cuisine").text(cuisineDisplayNamesMap[profile.cuisine]);
                    $("#profile-diet").text(dietDisplayNamesMap[profile.diet]);
                    $("#profile-intolerance").text(intoleranceDisplayNamesMap[profile
                        .intolerance]);
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

    // Setting diet description in form
    $('#diet').change(function () {
        if ($("#diet").val() != "none") {
            var selectedDiet = $("#diet option:selected").val();
            var selectedDietDesc = dietDescriptionsMap[selectedDiet];
            $('#desc-diet').text(selectedDietDesc);
        } else {
            $('#desc-diet').text(defaultDescDiet);
        }
    });

    //  toggle buttons
    $("#btn-edit-profile").click(function () {
        $("#profile-form-container").toggle();
    });

    $("#btn-edit-recipes").click(function () {
        $("#recipes-form-container").toggle();
    });

    $("#btn-edit-products").click(function () {
        $("#products-form-container").toggle();
    });

    $("#btn-search-products").click(function () {
        $("#search-products").toggle();
    });

    $("#btn-search-recipes").click(function () {
        $("#search-recipes").toggle();
    });
});