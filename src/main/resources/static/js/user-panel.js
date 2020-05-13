$(document).ready(function () {
    var authentication = auth
    
    $("#login-redirect").attr("href", window.location.origin+'/ui/login?referer='+window.location.href);

    $("#logout").attr("href", window.location.origin+'/logout');

    if (authentication.name != "anonymousUser" && authentication.name != "admin") {
        $("#user").attr({
            href: window.location.origin+'/ui/profiles/' + authentication.principal.id,
            title: "Go to your profile's page"
        });
    }
});