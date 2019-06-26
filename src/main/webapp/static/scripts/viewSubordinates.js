function getSubordinatesCall(data)
{
    listfill = document.getElementById("listfill");
    console.log(data.response);
    returnedData = data.response.split("\r\n");
    contents = "";
    you = JSON.parse(returnedData[0]);
    contents = contents + "<p>These are all who stand below you in the company, " + you.firstName + " " + you.lastName +".</p>" + "\n"+"<ul>";

    segment = "<li>"
    for (i = 1; i < returnedData.length; i ++)
    {
        you = JSON.parse(returnedData[i]);
        segment = segment + you.firstName + " " + you.lastName + "</li>";

        contents = contents + segment;
        segment = "<li>";
    }

    contents = contents += "</ul>";
    listfill.innerHTML = contents;
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

sendAuthGet("http://localhost:8080/project-01/api/subordinates", getSubordinatesCall);