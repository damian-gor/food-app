$(document).ready(function () {
    var authentication = auth

    $("#login-redirect").attr("href", window.location.origin + '/ui/login?referer=' + window.location.href);

    $(".header-brand").attr("href", window.location.origin + '/ui');

    $("#header-profilesLink").attr("href", window.location.origin + '/ui/profiles/');
    $("#header-productsLink").attr("href", window.location.origin + '/ui/search-products');
    $("#header-recipesLink").attr("href", window.location.origin + '/ui/search-recipes');

    $("#profilePage").attr("href", window.location.origin + '/ui/profiles/' + auth.principal.id);
    $("#profileDetails").attr("href", window.location.origin + '/ui/profiles/' + auth.principal.id + '/details');
    $("#profileDiary").attr("href", window.location.origin + '/ui/profiles/' + auth.principal.id + '/food-diary');
    $("#logout").attr("href", window.location.origin + '/logout');

    if (authentication.name != "anonymousUser" && authentication.name != "admin") {
        $("#user").attr({
            href: window.location.origin + '/ui/profiles/' + authentication.principal.id,
            title: "Go to your profile's page"
        });
    }
});