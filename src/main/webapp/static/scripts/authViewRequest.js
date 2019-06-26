function viewEmps()
{
    console.log("Mission accomplished Viewer");
}
let token = sessionStorage.getItem("token");
if (token) {
    sendAuth("http://localhost:8080/project-01/viewRequest",viewEmps);
}
else
{
    window.location.assign("http://localhost:8080/project-01/login");
}