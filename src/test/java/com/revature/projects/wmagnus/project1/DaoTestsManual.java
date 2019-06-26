package com.revature.projects.wmagnus.project1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.revature.projects.wmagnus.project1.dao.EmployeeDAO;
import com.revature.projects.wmagnus.project1.dao.EmployeeDAOImpl;
import com.revature.projects.wmagnus.project1.dao.ManagementLinkDAO;
import com.revature.projects.wmagnus.project1.dao.ManagementLinkDAOImpl;
import com.revature.projects.wmagnus.project1.dao.RequestDAO;
import com.revature.projects.wmagnus.project1.dao.RequestDAOImpl;
import com.revature.projects.wmagnus.project1.models.Employee;
import com.revature.projects.wmagnus.project1.models.ManagementLink;
import com.revature.projects.wmagnus.project1.models.Request;
import com.revature.projects.wmagnus.project1.util.HashUtil;

public class DaoTestsManual {

	public DaoTestsManual()
	{
		super();
	}
	
	@Test
	public void EmployeeDaoImplTestManualReadOnly()
	{
		System.out.println("EmployeeDAOImplTestManualReadOnly:");
		EmployeeDAO e = new EmployeeDAOImpl();
		Employee first = e.getEmployeeById(1);
		Employee byuid = e.getEmployeeByUsername("munterling");
		System.out.println(first);
		
		List<Employee> l = e.getAllEmployees();
		for (Employee emp : l)
		{
			System.out.println(emp);
		}
		
		assertTrue(l.contains(first));
		assertTrue(l.contains(byuid));
	}
	
	@Test
	public void EmployeeDaoImplTestManualWrite()
	{
		EmployeeDAO e = new EmployeeDAOImpl();
		Employee newEmp = new Employee(10, "Test", "Guy", "wolfemagnus@nevada.unr.edu", "tguy", HashUtil.hashStr("password"), 'y');
		e.createEmployee(newEmp);
		Employee gotEmp = e.getEmployeeByUsername("tguy");
		assertTrue(gotEmp != null);
		newEmp.setEmployeeID(gotEmp.getEmployeeID());
		assertEquals(newEmp, gotEmp);
		
		newEmp.setNeedsReset('n');
		e.updateEmployee(newEmp);
		gotEmp = e.getEmployeeByUsername("tguy");
		assertTrue(gotEmp != null);
		newEmp.setEmployeeID(gotEmp.getEmployeeID());
		assertEquals(newEmp, gotEmp);
		
		e.deleteEmployee(gotEmp.getEmployeeID());
		gotEmp = e.getEmployeeByUsername("tguy");
		assertEquals(null, gotEmp);
	}
	
	@Test
	public void ManagementLinkDaoImplManualReadOnly()
	{
		System.out.println("ManagementLinkDAOImplTestManualReadOnly:");
		ManagementLinkDAO m = new ManagementLinkDAOImpl();
		ManagementLink first = m.getManagementLinkById(1);
		List<ManagementLink> subs = m.getManagementLinksBySubordinate(5);
		List<ManagementLink> sups = m.getManagementLinksBySuperior(6);
		List<ManagementLink> all = m.getAllManagementLinks();
		
		String out = null;
		for(ManagementLink ml : all)
		{
			out = ml.toString();
			if(subs.contains(ml)) out = out+" sub=5";
			if(sups.contains(ml)) out = out+" sup=6";
			System.out.println(out);
		}
		assertTrue(all.contains(first));
	}
	
	@Test
	public void ManagementLinkDaoImplManualWrite()
	{
		ManagementLinkDAO m = new ManagementLinkDAOImpl();
		EmployeeDAO e = new EmployeeDAOImpl();
		Employee newEmp = new Employee(10, "Test", "Guy", "wolfemagnus@nevada.unr.edu", "tguy", HashUtil.hashStr("password"), 'y'); //Init a new guy
		e.createEmployee(newEmp);
		Employee gotEmp = e.getEmployeeByUsername("tguy");

		
		ManagementLink newML = new ManagementLink(11, gotEmp.getEmployeeID(), 9); //Subordinate new guy to big boss
		m.createManagementLink(newML);
		List<ManagementLink> oneLink = m.getManagementLinksBySubordinate(gotEmp.getEmployeeID());
		assertTrue(oneLink.size() == 1);
		ManagementLink gotML = oneLink.get(0);
		newML.setManagementLinkID(gotML.getManagementLinkID());
		assertEquals(newML,gotML);
		
		newML.setSuperiorID(8); //Change his subordination
		m.updateManagementLink(newML);
		oneLink = m.getManagementLinksBySubordinate(gotEmp.getEmployeeID());
		assertTrue(oneLink.size() == 1);
		gotML = oneLink.get(0);
		newML.setManagementLinkID(gotML.getManagementLinkID());
		assertEquals(newML,gotML);
		
		m.deleteManagementLink(newML.getManagementLinkID());
		List<ManagementLink> noLink = m.getManagementLinksBySubordinate(gotEmp.getEmployeeID());
		e.deleteEmployee(gotEmp.getEmployeeID());

		assertTrue(noLink.size() == 0);
	}
	
	@Test
	public void RequestDaoImplManualReadOnly()
	{
		RequestDAO r = new RequestDAOImpl();
		List<Request> all = r.getAllRequests();
		Request first = r.getRequestById(1);
		List<Request> byPoster = r.getRequestsByPostEmployee(1);
		List<Request> byApproval = r.getRequestsByApproval('Y');
		
		String out = null;
		for (Request req : all)
		{
			out = req.toString();
			if (byPoster.contains(req)) out = out+" byPoster(1)";
			if (byApproval.contains(req)) out = out+" byApproval(YES)";
			System.out.println(out);
		}
		assertTrue(all.contains(first));
	}
	
	@Test
	public void RequestDaoImplManualWrite()
	{
		ManagementLinkDAO m = new ManagementLinkDAOImpl();
		EmployeeDAO e = new EmployeeDAOImpl();
		Employee newEmp = new Employee(10, "Test", "Guy", "wolfemagnus@nevada.unr.edu", "tguy", HashUtil.hashStr("password"), 'y'); //Init a new guy
		e.createEmployee(newEmp);
		Employee gotEmp = e.getEmployeeByUsername("tguy");
		ManagementLink newML = new ManagementLink(11, gotEmp.getEmployeeID(), 9); //Subordinate new guy to big boss
		m.createManagementLink(newML);
		ManagementLink gotML = m.getManagementLinksBySubordinate(gotEmp.getEmployeeID()).get(0);

		RequestDAO r= new RequestDAOImpl();
		Request newReq = new Request(7, gotEmp.getEmployeeID(), 0, 'P', "Test Subject", "Test Explanation", 1.50);
		List<Request> reqList = null;
		r.createRequest(newReq);
		reqList = r.getRequestsByPostEmployee(gotEmp.getEmployeeID());
		assertTrue(reqList.size() == 1);
		newReq = reqList.get(0);
		
		newReq.setApprovalState('Y');
		newReq.setResolvingManagerID(newML.getSuperiorID());
		r.updateRequest(newReq);
		reqList = r.getRequestsByPostEmployee(gotEmp.getEmployeeID());
		assertTrue(reqList.size() == 1);
		assertTrue(reqList.contains(newReq));

		r.deleteRequest(newReq.getRequestID());
		reqList = r.getRequestsByPostEmployee(gotEmp.getEmployeeID());
		assertTrue(reqList.size() == 0);
		
		//Cleanup
		m.deleteManagementLink(gotML.getManagementLinkID());
		e.deleteEmployee(gotEmp.getEmployeeID());
	}
}
