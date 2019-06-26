function getSelfCall(data)
{
    console.log(data);
    dataset = data.response.split("\r\n");
    parsed = JSON.parse(dataset[0]);
    console.log(parsed);
    document.getElementById("name").innerHTML = parsed.firstName + " " +parsed.lastName;
    document.getElementById("username").innerHTML = parsed.username;
    document.getElementById("email").innerHTML = parsed.email;
    totality = "";
    if(dataset.length == 1)
    {
        document.getElementById("manager").innerHTML = "N/A";
        return;
    }
    for(i = 1; i < dataset.length; i ++)
    {
        parsed = JSON.parse(dataset[i]);
        totality = totality + parsed.firstName + " " + parsed.lastName;
        if(i != dataset.length-1) totality = totality + ", ";
    }
    console.log(totality);
    document.getElementById("manager").innerHTML = totality;
}

function sendAuthGet(url, callback)
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

sendAuthGet("http://localhost:8080/project-01/api/self", getSelfCall);