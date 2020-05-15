$(document).ready(function () {

    // BUTTON: show diary page form 
    $("#show-diaryPage-form").click(function () {
        $("#diaryPage-form-container").toggle();
        if ($("#show-img").attr("class") == "fas fa-sort-down") $("#show-img").attr("class", "fas fa-sort-up");
        else $("#show-img").attr("class", "fas fa-sort-down");
    });

    // Calendar 
    $("#datepicker").datepicker({
        dateFormat: 'yy-mm-dd'
    });

    // SUBMIT FORM
    $("#diaryPageForm").submit(function (event) {
        event.preventDefault();
        var date = $("#datepicker").val();
        var isContains = $('.table').text().indexOf(date) > -1;
        if (isContains== false){
        ajaxPostDiaryPage(date);
        } else {
        alert("Diary page for " + date + " already exists. Please pick up another date.")
        }
    });

    function ajaxPostDiaryPage(date) {
        $.ajax({
            type: "POST",
            url: window.location.href + '?date=' + date,
            dataType: 'json',
            success: function (textStatus, xhr, result) {
                var diaryPage = result.responseJSON;
                if (xhr == "success") {
                    $("#show-diaryPage-form").click();
                    $(".table tr:last").after(
                        "<tr style='background-color:rgba(226, 224, 186, 0.966)'>" +
                        "<td>" + diaryPage.id.date + "</td>" +
                        "<td>" + diaryPage.caloricIntakeGoal + "</td>" +
                        "<td>" + diaryPage.kcalLeft + "</td>" +
                        "<td><a href=" + diaryPage.links[0].href + "><button>Page details</button></a></td>" +
                        "<td><a href=" + diaryPage.links[2].href + "><button>Page meals</button></a></td>" +
                        "</tr>");
                }
            },
            error: function (xhr) {
                handleAjaxError(xhr);
            }
        });

    };

})