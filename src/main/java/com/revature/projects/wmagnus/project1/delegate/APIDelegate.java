package com.revature.projects.wmagnus.project1.delegate;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.projects.wmagnus.project1.dao.EmployeeDAO;
import com.revature.projects.wmagnus.project1.dao.EmployeeDAOImpl;
import com.revature.projects.wmagnus.project1.dao.RequestDAO;
import com.revature.projects.wmagnus.project1.dao.RequestDAOImpl;
import com.revature.projects.wmagnus.project1.internals.Validation;
import com.revature.projects.wmagnus.project1.models.Employee;
import com.revature.projects.wmagnus.project1.models.ManagementLink;
import com.revature.projects.wmagnus.project1.models.Request;
import com.revature.projects.wmagnus.project1.models.UnencryptedToken;
import com.revature.projects.wmagnus.project1.util.AuthCryptoUtil;
import com.revature.projects.wmagnus.project1.util.HashUtil;

public class APIDelegate {

	final ObjectMapper map = new ObjectMapper();
	DBInternalStorageDelegate db;
	AuthDelegate ad;
	RequestDAO rdao;
	EmployeeDAO edao;
	
	public class NeatRequest implements Serializable{
		public static final long serialVersionUID = 1L;
		public Request request;
		public String postingEmployeeName;
		public String resolvingManagerName;
	}
	
	public APIDelegate()
	{
		super();
	}
	
	public APIDelegate(AuthDelegate ad, DBInternalStorageDelegate db)
	{
		this.db = db;
		this.ad = ad;
		rdao = new RequestDAOImpl();
		edao = new EmployeeDAOImpl();
	}
	
	public void handleAPIMapping(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String uri = request.getRequestURI().substring(request.getContextPath().length());
		System.out.println("happens here api");
		switch(uri)
		{
		case "/api/self":
			this.returnEmployeeSelf(request, response);
			break;
		case "/api/subordinates":
			this.returnEmployeeSubordinateHierarchy(request, response);
			break;
		case "/api/readableRequests":
			this.returnRequests(request, response);
			break;
		case "/api/createRequest":
			this.createRequest(request, response);
			break;
		case "/api/updateUser":
			this.updateUser(request, response);
			break;
		default:
			if (uri.startsWith("/api/") && !uri.startsWith("/api/approval/"))
			{
				try
				{
					Integer id = Integer.parseInt(uri.substring(5));
					returnRequestNumber(request, response, id);
				} catch (NumberFormatException e)
				{
					System.out.println("Invalid number");
					return;
				}
			}
			else if(uri.startsWith("/api/approval/"))
			{
				try
				{
					Integer id = Integer.parseInt(uri.substring(14));
					System.out.println(id);
					if(attemptApproval(request, response, id))
					{
						System.out.println("UPDATING...");
						db.init();
					}
				} catch (NumberFormatException e)
				{
					System.out.println("Invalid number");
					return;
				}
				
			}
			else response.sendError(404, "Static Resource Not Found");
		}
	}
	
	public void updateUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		UnencryptedToken ut = ad.authTokenFull(request, response);
		if(ut == null)
		{
			response.sendError(403, "No such employee at token. Likely invalid token.");
			return;
		}
		Employee e = db.getEmployeeById(ut.employeeId);
		if (e == null)
		{
			response.sendError(403, "No such employee at token. This... shouldn't happen.");
			return;
		}
		String nemail = request.getParameter("email");
		String npass = request.getParameter("pass");
		System.out.println(nemail);
		System.out.println(npass);
		
		if(nemail != null && !Validation.validEmail(nemail))
		{
			response.sendError(401, "Invalid Email.");
			return;
		}
		if(npass != null && !Validation.validPassword(npass))
		{
			response.sendError(401, "Invalid Password.");
			return;
		}
		if(npass == null && nemail == null)
		{
			response.sendError(401, "No information to update with.");
			return;
		}

		if(nemail != null)
		{
			e.setEmail(nemail);
		}

		if(npass != null)
		{
			String hashpass = HashUtil.hashStr(npass);
			e.setHashpass(hashpass);
		}
		
		System.out.println(e);
		int ret = edao.updateEmployee(e);
		if(ret == 0) {
			response.sendError(500, "Database Anomaly."); 
			System.out.println("what in the database...");
		}
		if(ret > 1) db.init();
		
		if(npass != null)
		{
			ut = ad.assembleToken(e.getUsername(), npass, request.getRemoteAddr(), new Timestamp(System.currentTimeMillis()));
			System.out.println("nanomachines son");
			response.setStatus(200);
			response.setHeader("auth",AuthCryptoUtil.encrypt(ut.toString()));
		}
		
	}
	
	public void createRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		UnencryptedToken ut = ad.authTokenFull(request, response);
		if(ut == null)
		{
			response.sendError(403, "No such employee at token. Likely invalid token.");
			return;
		}
		Employee e = db.getEmployeeById(ut.employeeId);
		if (e == null)
		{
			response.sendError(403, "No such employee at token. This... shouldn't happen.");
			return;
		}
		
		String subj = request.getParameter("subject");
		String expl = request.getParameter("explanation");
		String amtStr = request.getParameter("amount");
		
		Double amount;
		try {
			amount = Double.parseDouble(amtStr);
		}
		catch(NumberFormatException err)
		{
			response.sendError(400);
			return;
		}
		
		if (Validation.validSubject(subj) && Validation.validExplanation(expl) && Validation.validAmount(amount))
		{
			System.out.println(subj + " " + expl + " " + amount);

			Request req = new Request();
			req.setAmount(amount);
			req.setApprovalState('P');
			req.setExplanation(expl);
			req.setPostingID(ut.employeeId);
			req.setResolvingManagerID(0);
			req.setSubject(subj);
			req.setRequestID(0);
			System.out.println(req);
			
			int res = rdao.createRequest(req);
			System.out.println(res);

			if(res >= 1) db.init(); // necessary because there is no workaround to get accurate requestIds without re-summoning db data.
		}
		else
		{
			response.sendError(400, "Malformed or invalid input.");
		}
	}
	
	public void returnRequestNumber(HttpServletRequest request, HttpServletResponse response, int id) throws ServletException, IOException
	{
		UnencryptedToken ut = ad.authTokenFull(request, response);
		if(ut == null)
		{
			response.sendError(404, "No such employee at token. Likely invalid token.");
			return;
		}
		Employee e = db.getEmployeeById(ut.employeeId);
		if (e == null)
		{
			response.sendError(404, "No such employee at token. This... shouldn't happen.");
			return;
		}
		NeatRequest nr = new NeatRequest();
		Request r = db.getRequestById(id);
		
		nr.request = r;
		e = db.getEmployeeById(r.getPostingID());
		nr.postingEmployeeName = e.getFirstName() + " " + e.getLastName();
		if(r.getResolvingManagerID() != 0)
		{
			e = db.getEmployeeById(r.getResolvingManagerID());
			nr.resolvingManagerName = e.getFirstName() + " " + e.getLastName();
		}
		else 
		{
			nr.resolvingManagerName = "N/A";
		}
	
		String approvable = "N";
		if(r.getApprovalState() == 'P')
		{
			System.out.println("p-cwalking");
			List<ManagementLink> authing = db.getManagementLinksBySuperiorDeep(ut.employeeId);
			for (ManagementLink ml : authing)
			{
				if (ml.getSubordinateID() == r.getPostingID() && ut.employeeId != r.getPostingID())
				{
					approvable="Y";
				
					break;
				}
			}
		}
		PrintWriter pw = response.getWriter();
		pw.write("{\"request\":" + map.writeValueAsString(nr) +",\"approvable\":\"" + approvable + "\"}");
		
		pw.close();
	}
	
	public void returnEmployeeSelf(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("rip me");
		UnencryptedToken ut = ad.authTokenFull(request, response);
		if (ut == null)
		{
			response.sendError(404, "No such employee at token. Likely invalid token.");
			return;
		}
		
		Employee e = db.getEmployeeById(ut.employeeId);
		if (e == null)
		{
			response.sendError(404, "No such employee at token. This... shouldn't happen.");
			return;
		}
		e = new Employee(e);
		e.setHashpass("");

		List<Employee> superiors = db.getDirectManagers(e.getEmployeeID());
		for(Employee sup : superiors)
		{
			sup.setHashpass("");
		}
		
		PrintWriter pw = response.getWriter();
		pw.write(map.writeValueAsString(e));
		
		for(Employee sup: superiors)
		{
			pw.write("\r\n" + map.writeValueAsString(sup));
			System.out.println(sup);
		}
		
		pw.close();
	}
	
	public void returnEmployeeSubordinateHierarchy(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		UnencryptedToken ut = ad.authTokenFull(request, response);
		if (ut == null)
		{
			response.sendError(404, "No such employee at token. Likely invalid token.");
			return;
		}
		
		Employee e = db.getEmployeeById(ut.employeeId);
		if (e == null)
		{
			response.sendError(404, "No such employee at token. This... shouldn't happen.");
			return;
		}
		e = new Employee(e);
		e.setHashpass("");

		List<Employee> subordinateHierarchy = db.getSubordinatesRecursive(ut.employeeId);
		for(Employee sub: subordinateHierarchy)
		{
			sub.setHashpass("");
		}

		PrintWriter pw = response.getWriter();
		pw.write(map.writeValueAsString(e));
		
		for(Employee sub: subordinateHierarchy)
		{
			pw.write("\r\n" + map.writeValueAsString(sub));
			System.out.println(sub);
		}
		pw.close();
	}
	
	public void returnRequests(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		List<NeatRequest> nrlist = new ArrayList<NeatRequest>();
		List<Request> allowedRequests = new ArrayList<Request>();
		
		UnencryptedToken ut = ad.authTokenFull(request, response);
		if(ut == null || db.getEmployeeById(ut.employeeId) == null)
		{
			response.sendError(404, "Invalid token.");
			return;
		}
		
		List<Employee> acceptableEmployees = new ArrayList<Employee>();
		acceptableEmployees.add(new Employee(db.getEmployeeById(ut.employeeId)));
		
		acceptableEmployees.addAll(db.getSubordinatesRecursive(ut.employeeId));
		
		for(Employee e : acceptableEmployees)
		{
			allowedRequests.addAll(db.getRequestsByEmployeeId(e.getEmployeeID()));
		}
		
		NeatRequest nr;
		Employee em = new Employee();
		for(Request r : allowedRequests)
		{
			nr = new NeatRequest();
			nr.request = r;
			em = db.getEmployeeById(r.getPostingID());
			nr.postingEmployeeName = em.getFirstName() + " " + em.getLastName();
			if(r.getResolvingManagerID() != 0)
			{
				em = db.getEmployeeById(r.getResolvingManagerID());
				nr.resolvingManagerName = em.getFirstName() + " " + em.getLastName();
			}
			else
			{
				nr.resolvingManagerName = "N/A";
			}
			nrlist.add(nr);
		}
		if(!nrlist.isEmpty())
		{
			PrintWriter pw = response.getWriter();
			pw.write(map.writeValueAsString(nrlist));
			pw.close();	
		}
	}
	
	public boolean attemptApproval(HttpServletRequest request, HttpServletResponse response, int id) throws ServletException, IOException
	{
		UnencryptedToken ut = ad.authTokenFull(request, response);
		if(ut == null || db.getEmployeeById(ut.employeeId) == null)
		{
			response.sendError(403, "Invalid token.");
			System.out.println("Oddity");
			System.out.println(ut);
			return false;
		}
		
		System.out.println("Token");
		
		List<Employee> acceptableEmployees = new ArrayList<Employee>();
		if(db.getRequestById(id) != null) acceptableEmployees = db.getSuperiorsRecursive(db.getRequestById(id).getPostingID());
		else return false;
		
		
		System.out.println("Acceptables: " +acceptableEmployees);
		boolean proceed = false;
		for(Employee em : acceptableEmployees)
		{
			if(em.getEmployeeID() == ut.employeeId)
			{
				System.out.println("Acceptable!");
				proceed = true;
				break;
			}
		}
		
		if(proceed)
		{
			System.out.println("Proceeding");
			String stateChange = request.getParameter("state");
			System.out.println(stateChange);
			if(stateChange != null && ("N".equals(stateChange) || "Y".equals(stateChange)))
			{
				System.out.println("StateChanging");
				Request r = db.getRequestById(id);
				if (r != null)
				{
					System.out.println("RequestAltering");

					r.setApprovalState(stateChange.charAt(0));
					r.setResolvingManagerID(ut.employeeId);
					int upd = rdao.updateRequest(r);
					if (upd >= 1) return true;
				}
			}
		}
		else
		{
			response.sendError(403, "Change is forbidden.");
		}
		
		return false;
	}
	
}
