
function sendAuth(url, callback)
{
	if (sessionStorage.getItem("token")) 
	{
		let xhr = new XMLHttpRequest();
		xhr.open("POST", url);
		xhr.onreadystatechange = function() {
			if (this.readyState == 4 && !(this.status == 200))
			{
				sessionStorage.removeItem("token");
				window.location.href = "http://localhost:8080/project-01/login";
			} else if (this.readyState == 4)
			{
				callback(this);
			}
		}
		xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
		let token = sessionStorage.getItem("token");
		let requestBody = "authtoken=" + token;
		console.log(requestBody);
		xhr.send(requestBody);
	}
}
