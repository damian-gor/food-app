 function handleAjaxError (xhr) {
    var error = xhr.responseJSON;
    var apiError = error.apierror;
    if (xhr.status == 401) alert('Unauthorized! Please log in first');
    else if (xhr.status == 403) alert("403: Forbidden!\nYou can edit only your own profile's records");
    else if (typeof apiError === 'undefined' || typeof apiError === null) {
        alert(error.status + ": " + error.message + ".\nFor details more check console.");
        console.log("Error details: ", error)
    } else {
        alert(apiError.message + "\nFor more details check console.");
        console.log("Debug message: ", apiError.debugMessage);
    }
}