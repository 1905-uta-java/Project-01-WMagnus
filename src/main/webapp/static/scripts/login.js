function loginMakeRequest()
{
	let url = "http://localhost:8080/project-01/login";
	let xhr = new XMLHttpRequest();
	xhr.open("POST", url);
	xhr.onreadystatechange = function() {
		if(this.readyState === 4 && this.status === 200)
		{
			var authToken = xhr.getResponseHeader("auth");
			sessionStorage.setItem("token", authToken);
			if(sessionStorage.getItem("token")) window.location.assign( "http://localhost:8080/project-01/homeEmployee");
		}
	}
	let user = document.getElementById("username").value;
	let pass = document.getElementById("pass").value;

	xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	let requestBody = "username="+user+"&password="+pass;
	console.log(requestBody);
	xhr.send(requestBody);
}
document.getElementById("login-button").addEventListener("click", loginMakeRequest);
