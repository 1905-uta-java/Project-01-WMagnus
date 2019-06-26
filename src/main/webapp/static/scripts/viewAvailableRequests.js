function tableRedirection(redirValue)
{
    console.log(redirValue);
    sessionStorage.setItem("viewingRequest", redirValue);
    window.location.assign("./viewRequest");
}

var stateQual = "A";
var requestStore;

function getRequestsCall(data)
{
    requestStore = data;
    listfill = document.getElementById("table-requests");

    console.log(data.response);
    var full = JSON.parse(data.response);
    console.log(full);
    i = 0;
    table = document.getElementById("table-requests");
    for(var r = table.rows.length -1; r > 0; r--)
    {
        table.deleteRow(r);
    }

    for (req of full)
    {
        if((stateQual == "A" || req.request.approvalState == stateQual) && req.postingEmployeeName.includes(document.getElementById("nameQual").value))
        {

            let idval = "tr"+i;
            let row = document.createElement("tr");
            row.setAttribute("id", idval);
            let rid = req.request.requestID;
            let emp = document.createElement("td");
            let state = document.createElement("td");
            let mgr = document.createElement("td");
            let sbj = document.createElement("td");
            let amt = document.createElement("td");
            console.log(req);
            emp.innerHTML = req.postingEmployeeName;
            mgr.innerHTML = req.resolvingManagerName;
            state.innerHTML = req.request.approvalState;
            sbj.innerHTML = req.request.subject;
            amt.innerHTML = req.request.amount;

            row.appendChild(emp);
            row.appendChild(state);
            row.appendChild(mgr);
            row.appendChild(sbj);
            row.appendChild(amt);
            listfill.appendChild(row);
            document.getElementById(idval).addEventListener("click", Function('tableRedirection('+rid+');'));
            i++;
        }
    }
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

sendAuthGet("http://localhost:8080/project-01/api/readableRequests", getRequestsCall);

document.getElementById("stateAll").addEventListener("click", function(){stateQual="A"; getRequestsCall(requestStore);});
document.getElementById("stateDeny").addEventListener("click", function(){stateQual="N"; getRequestsCall(requestStore);});
document.getElementById("stateApprove").addEventListener("click", function(){stateQual="Y"; getRequestsCall(requestStore);});
document.getElementById("statePending").addEventListener("click", function(){stateQual="P"; getRequestsCall(requestStore);});
document.getElementById("nameQual").addEventListener("change", function(){getRequestsCall(requestStore);})