function homeEmpCallback()
{
    console.log("Mission accomplished Employee");
}
let token = sessionStorage.getItem("token");
if (token) {
    sendAuth("http://localhost:8080/project-01/homeEmployee",homeEmpCallback);
}
else
{
    window.location.assign("http://localhost:8080/project-01/login");
}