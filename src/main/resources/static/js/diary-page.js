$(document).ready(function () {

    // DELETE DIARY PAGE BUTTON
    $("#delete").click(function (event) {
        event.preventDefault();
        if (window.confirm("Are you sure?")) {
            ajaxDelete();
        }
    });

    // DELETE DIARY PAGE
    function ajaxDelete() {
        var diaryPagesLink = $("#diaryPagesLink").attr("href");
        $.ajax({
            url: window.location.href,
            type: 'DELETE',
            success: function () {
                alert(
                    "Diary page has been removed successfuly. Redirecting to profile's diary pages list."
                );
                window.location.replace(diaryPagesLink);
            },
            error: function (xhr) {
                handleAjaxError(xhr);
            }
        });
    };



});