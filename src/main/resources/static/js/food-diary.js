$(document).ready(function () {

    // Getting profile name
    var profileLink = $("#profileLink").prop('href').replace('ui/', '');
    $.ajax({
        type: "GET",
        url: profileLink,
        success: function (textStatus, xhr, response) {
            var profileName = response.responseJSON.name;
            if (xhr == 'success') {
                $('#profileName').text(profileName);
            }
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
});