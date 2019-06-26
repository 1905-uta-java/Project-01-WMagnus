function makeReqAuthCallback()
{
    console.log("Mission accomplished makereq");
}
let token = sessionStorage.getItem("token");
if (token) {
    sendAuth("http://localhost:8080/project-01/makeRequest",makeReqAuthCallback);
}
else
{
    window.location.assign("http://localhost:8080/project-01/login");
}