$(document).ready(function () {
    var url = window.location.origin;

    $("#set-numberAndOffset").click(function(){
        var resultsInput = $("#input-result-number").val();
        var pageNumberInput = $("#input-page").val() - 1;
        window.open(window.location.href.split('?')[0] + "?number=" + resultsInput + "&offset=" +
        pageNumberInput, "_self");
    });
});