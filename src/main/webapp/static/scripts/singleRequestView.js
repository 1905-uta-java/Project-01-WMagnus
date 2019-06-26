function viewSingleReq(data)
{
    console.log(data.response);
    out = JSON.parse(data.response);
    if(out.approvable=='N')
    {
        console.log('heyyyyyyyyy')
        document.getElementById("approve").style.display = 'none';
        document.getElementById("deny").style.display = 'none';
    }
    document.getElementById("requestNumber").innerHTML = out.request.request.requestID;
    document.getElementById("poster").innerHTML = "Posted By: " + out.request.postingEmployeeName;
    document.getElementById("resolver").innerHTML = "Resolved By: " + out.request.resolvingManagerName;
    document.getElementById("status").innerHTML = "Status: " + out.request.request.approvalState;
    document.getElementById("subject").innerHTML = out.request.request.subject;
    document.getElementById("explanation").innerHTML = out.request.request.explanation;
    document.getElementById("cost").innerHTML = out.request.request.amount;
    updateListeners();
}

function sendAuthGet(url, callback)
{
    if (sessionStorage.getItem("token") && !sessionStorage.getItem("viewingRequest")) window.location.assign("./viewRequests");
	if (sessionStorage.getItem("token") && sessionStorage.getItem("viewingRequest")) 
	{
        url = url +sessionStorage.getItem("viewingRequest");
		let xhr = new XMLHttpRequest();
		xhr.open("POST", url);
		xhr.onreadystatechange = function() {
			if (this.readyState == 4 && !(this.status == 200))
			{
				sessionStorage.removeItem("token");
				//window.location.assign("http://localhost:8080/project-01/login");
			} else if (this.readyState == 4)
			{
				callback(this);
			}
		}
		xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
		let token = sessionStorage.getItem("token");
        let requestBody = "authtoken=" + token + "&requestid=" +sessionStorage.getItem("viewingRequest");
        console.log(requestBody);
		xhr.send(requestBody);
	}
}

sendAuthGet("http://localhost:8080/project-01/api/", viewSingleReq);

function sendApprovalChange(url, state, callback)
{
    if (sessionStorage.getItem("token") && !sessionStorage.getItem("viewingRequest")) window.location.assign("./viewRequests");
	if (sessionStorage.getItem("token") && sessionStorage.getItem("viewingRequest")) 
	{
        url = url +sessionStorage.getItem("viewingRequest");
		let xhr = new XMLHttpRequest();
		xhr.open("POST", url);
		xhr.onreadystatechange = function() {
			if (this.readyState == 4 && !(this.status == 200))
			{
				//sessionStorage.removeItem("token");
				//window.location.assign("http://localhost:8080/project-01/login");
			} else if (this.readyState == 4)
			{
				callback(this);
			}
		}
		xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
		let token = sessionStorage.getItem("token");
        let requestBody = "authtoken=" + token + "&requestid=" +sessionStorage.getItem("viewingRequest") + "&state="+state;
        console.log(requestBody);
		xhr.send(requestBody);
	}
}

function approved()
{
    console.log("approved");
    window.location.assign("http://localhost:8080/project-01/viewRequests");
}

function denied()
{
    console.log("denied");
    window.location.assign("http://localhost:8080/project-01/viewRequests");
}

function updateListeners()
{
    if (document.getElementById("approve").hidden == false && document.getElementById("deny").hidden == false)
    {
        document.getElementById("approve").addEventListener("click", function(){sendApprovalChange("http://localhost:8080/project-01/api/approval/","Y",approved)});
        document.getElementById("deny").addEventListener("click", function(){sendApprovalChange("http://localhost:8080/project-01/api/approval/","N",denied)});

    }    
}
