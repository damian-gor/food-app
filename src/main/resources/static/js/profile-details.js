$(document).ready(function () {

    for (i = new Date().getFullYear(); i > 1900; i--) {
        $('#yearpicker').append($('<option />').val(i).html(i));
    }

    $('.input-range-height').on('input', function () {
        $(this).next('.range-value').html(this.value + " cm");
    });

    $('.input-range-weight').on('input', function () {
        $(this).next('.range-value').html(this.value + " kg");
    });

    $("#btn-edit").click(function () {
        $("#form").toggleClass("hidden");
        $("#yearpicker").val($('#profileYearOfBirth').text());
        if ($("#profileSex").text() == "Female") $("#sex-female").prop("checked", true);
    });


    // SUBMIT FORM
    $("#detailsForm").submit(function (event) {
        event.preventDefault();
        ajaxPatch();
    });

    function ajaxPatch() {

        var formData = {
            height: $("#height").val(),
            weight: $("#weight").val(),
            yearOfBirth: $("#yearpicker").val(),
            sex: $(".sex_form:checked").val(),
            aim: $("#aim").val(),
            activityLevel: $("#activityLevel").val()
        }

        $.ajax({
            type: "PATCH",
            contentType: "application/json",
            url: window.location.href,
            data: JSON.stringify(formData),
            dataType: 'json',
            success: function (data, textStatus, xhr) {
                var profileDetails = xhr.responseJSON;
                console.log(profileDetails);
                console.log(sexDisplayNamesMap);
                if (xhr.status == 200) {
                    alert("Profile details has  been updated successfully!");
                    $("#form").toggle();
                    $("#profileHeight").text(profileDetails.height);
                    $("#profileWeight").text(profileDetails.weight);
                    $("#profileYearOfBirth").text(profileDetails.yearOfBirth);
                    $("#profileSex").text(sexDisplayNamesMap[profileDetails.sex]);
                    $("#profileAim").text(aimDisplayNamesMap[profileDetails.aim]);
                    $("#profileAim").prop('title', aimDescriptionsMap[profileDetails.aim]);
                    $("#profileActivityLevel").text(activityLevelDisplayNamesMap[
                        profileDetails.activityLevel]);
                    $("#profileActivityLevel").prop('title', activityLevelDescriptionsMap[
                        profileDetails.activityLevel]);
                    $("#profileBMI").text(profileDetails.bmi);
                    $("#profileBMI").prop('title', profileDetails.bmiDescription);
                    $("#profileBMR").text(profileDetails.bmr);
                    $("#profileRecommendedCaloricIntake").text(profileDetails
                        .recommendedCaloricIntake);
                }
            },
            error: function (xhr) {
                handleAjaxError(xhr);
            }
        });
    };


    // Setting Activity level description in form
    $('#activityLevel').change(function () {
        var selectedActivityLevel = $("#activityLevel option:selected").val();
        var selectedActivityLevelDesc = activityLevelDescriptionsMap[selectedActivityLevel];
        $('#desc-activityLevel').text(selectedActivityLevelDesc);
    });


});