$(document).ready(function () {
    var defaultDescDiet = $('#desc-diet').text();

    // SUBMIT FORM
    $("#profileForm").submit(function (event) {
        event.preventDefault();
        ajaxPost();
    });

    function ajaxPost() {

        var diet, cuisine, intolerance = null;
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
            type: "POST",
            contentType: "application/json",
            url: "/ui/profiles",
            data: JSON.stringify(formData),
            dataType: 'json',
            success: function (data, textStatus, xhr) {
                var profile = xhr.responseJSON;
                var link = profile.links[0].href;
                if (xhr.status == 200) {
                    $("#profile-form-container").toggle();
                    $("#postresponseDiv").html(
                        "Profile " + profile.name +
                        ", with ID " + profile.id +
                        " has been created successfully! <br>");
                    $(".table tr:last").after(
                        "<tr style='background-color:rgba(226, 224, 186, 0.966)'>" +
                        "<td>" + profile.id + "</td>" +
                        "<td>" + profile.name + "</td>" +
                        "<td><a href=" + link + "><button>See profile</button></a></td>" +
                        "<td><a href=" + link + "/details><button>Details</button></a></td>" +
                        "<td><a href=" + link + "/food-diary><button>Food Diary</button></a></td>" +
                        "</tr>");
                } else {
                    $("#postresponseDiv").html("<strong>Error</strong>");
                }
            },
            error: function (xhr) {
                handleAjaxError(xhr);
            }
        });

    };

    $('#diet').change(function () {
        if ($("#diet").val() != "none") {
            var selectedDiet = $("#diet option:selected").val();
            var selectedDietDesc = dietDescriptionsMap[selectedDiet];
            $('#desc-diet').text(selectedDietDesc);
        } else {
            $('#desc-diet').text(defaultDescDiet);
        }
    });

    // buttons
    $("#btn-add-profile").click(function () {
        $("#profile-form-container").toggle();
    });

})