function makeReqCallback(data)
{
    console.log(data.response);
    window.location.assign("http://localhost:8080/project-01/viewRequests");
}

function sendAuthSet(url, callback)
{
    console.log("are you even trying");
	if (sessionStorage.getItem("token") && document.getElementById("subject").value && document.getElementById("amount").value) 
	{
		let xhr = new XMLHttpRequest();
		xhr.open("POST", url);
		xhr.onreadystatechange = function() {
			if (this.readyState == 4 && !(this.status == 200))
			{
				//sessionStorage.removeItem("token");
				//window.location.href = "http://localhost:8080/project-01/login";
			} else if (this.readyState == 4)
			{
				callback(this);
			}
		}
		xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
		let token = sessionStorage.getItem("token");
        let requestBody = "authtoken=" + token + "&subject=" + document.getElementById("subject").value + "&explanation=" + document.getElementById("explanation").value + "&amount=" + document.getElementById("amount").value;
        console.log(requestBody);
		xhr.send(requestBody);
	}
}

//sendAuthGet("http://localhost:8080/project-01/api/createRequest", getRequestsCall);
document.getElementById("submit-internal").addEventListener("click",function(){sendAuthSet("http://localhost:8080/project-01/api/createRequest", makeReqCallback);});

