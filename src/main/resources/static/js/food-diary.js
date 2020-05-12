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
        error: function (xhr, e) {
            if (xhr.status == 401) alert('Unauthorized! Please log in');
            else if (xhr.status == 403) alert('Forbidden!');
            else alert("Error!");
            console.log("ERROR: ", e);
        }
    });
});