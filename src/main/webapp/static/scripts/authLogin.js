function loginCallback()
{
    console.log("Mission accomplished");
    window.location.assign("http://localhost:8080/project-01/homeEmployee");
}
let token = sessionStorage.getItem("token");
if (token) {

    sendAuth("http://localhost:8080/project-01/login",loginCallback);
}