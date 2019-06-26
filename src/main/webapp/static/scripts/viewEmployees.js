function viewEmployeesRedir()
{
    window.location.assign("http://localhost:8080/project-01/viewEmployees");
}

document.getElementById("view-employees-button").addEventListener("click", viewEmployeesRedir);