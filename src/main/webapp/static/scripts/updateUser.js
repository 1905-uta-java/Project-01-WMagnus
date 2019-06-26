function sendUserUpdate(url, callback)
{
    console.log("are you even trying");
	if (sessionStorage.getItem("token")) 
	{
		let xhr = new XMLHttpRequest();
		xhr.open("POST", url);
		xhr.onreadystatechange = function() {
			if (this.readyState == 4 && !(this.status == 200))
			{
				sessionStorage.removeItem("token");
				//window.location.href = "http://localhost:8080/project-01/login";
			} else if (this.readyState == 4)
			{
                if(document.getElementById("newPass").value)
                {
                    console.log("newtoken");
                    sessionStorage.removeItem("token");
                    var authToken = xhr.getResponseHeader("auth");
                    sessionStorage.setItem("token", authToken);    
                }
                callback(this);
			}
		}
		xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
        let token = sessionStorage.getItem("token");
        let requestBody = "authtoken=" + token;
        if(document.getElementById("newEmail").value)
        {
            requestBody = requestBody + "&email=" + document.getElementById("newEmail").value;
        }
        if(document.getElementById("newPass").value)
        {
            requestBody = requestBody + "&pass=" + document.getElementById("newPass").value;
        }
        if(requestBody == "authtoken=" + token) return;
        console.log(requestBody);
		xhr.send(requestBody);
	}
}

function updateCall(data)
{
    sendAuthGet("http://localhost:8080/project-01/api/self", getSelfCall);
    document.getElementById("newPass").value = "";
    document.getElementById("newEmail").value = "";

}

function makeUpdate()
{
    console.log("stuff");
    sendUserUpdate("http://localhost:8080/project-01/api/updateUser", updateCall);
}

document.getElementById("saves").addEventListener("click", makeUpdate);
