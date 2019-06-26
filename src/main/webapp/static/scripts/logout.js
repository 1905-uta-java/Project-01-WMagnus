function logout()
{
    sessionStorage.clear();
    window.location.assign("http://localhost:8080/project-01/login");
}


document.getElementById("logout-button").addEventListener("click", logout);
