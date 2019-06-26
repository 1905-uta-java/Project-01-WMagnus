package com.revature.projects.wmagnus.project1.delegate;

import java.util.ArrayList;
import java.util.List;

import com.revature.projects.wmagnus.project1.dao.EmployeeDAO;
import com.revature.projects.wmagnus.project1.dao.EmployeeDAOImpl;
import com.revature.projects.wmagnus.project1.dao.ManagementLinkDAO;
import com.revature.projects.wmagnus.project1.dao.ManagementLinkDAOImpl;
import com.revature.projects.wmagnus.project1.dao.RequestDAO;
import com.revature.projects.wmagnus.project1.dao.RequestDAOImpl;
import com.revature.projects.wmagnus.project1.models.Employee;
import com.revature.projects.wmagnus.project1.models.ManagementLink;
import com.revature.projects.wmagnus.project1.models.Request;

public class DBInternalStorageDelegate {

	EmployeeDAO ed = new EmployeeDAOImpl();
	ManagementLinkDAO ml = new ManagementLinkDAOImpl();
	RequestDAO rd = new RequestDAOImpl();
	List<Employee> emplist = null;
	List<ManagementLink> mllist = null;
	List<Request> reqlist = null;

	public DBInternalStorageDelegate()
	{
		super();
		init();
	}
	
	public void init()
	{
		emplist = ed.getAllEmployees();
		mllist = ml.getAllManagementLinks();
		reqlist = rd.getAllRequests();
	}

	public List<ManagementLink> getAllManagementLinks()
	{
		return mllist;
	}
	
	public List<Request> getAllRequests()
	{
		return reqlist;
	}
	
	public List<Employee> getAllEmployees()
	{
		return emplist;
	}
	
	public List<Employee> getDirectManagers(int id)
	{
		List<Employee> list = new ArrayList<Employee>();
		List<ManagementLink> mlist = this.getManagementLinksBySubordinate(id);
		
		for(ManagementLink mlink : mlist)
		{
			list.add(new Employee(this.getEmployeeById(mlink.getSuperiorID())));
		}
		return list;
	}
	
	public List<Employee> getSubordinatesRecursive(int id)
	{
		List<Employee> list = new ArrayList<Employee>();
		List<Employee> metalist = new ArrayList<Employee>();
		List<ManagementLink> mlist = this.getManagementLinksBySuperiorDeep(id);
		
		Employee e;
		for(ManagementLink mlink : mlist)
		{
			e = this.getEmployeeById(mlink.getSubordinateID());
			if(!list.contains(e))
			{
				list.add(e);
			}
		}
		for (Employee em : list)
		{
			metalist.add(new Employee(em));
		}
		return metalist;
	}
	
	public List<Employee> getSuperiorsRecursive(int id)
	{
		List<Employee> list = new ArrayList<Employee>();
		List<Employee> metalist = new ArrayList<Employee>();
		List<ManagementLink> mlist = this.getManagementLinksBySubordinateDeep(id);
		
		Employee e;
		for(ManagementLink mlink : mlist)
		{
			e = this.getEmployeeById(mlink.getSuperiorID());
			if(!list.contains(e))
			{
				list.add(e);
			}
		}
		for (Employee em : list)
		{
			metalist.add(new Employee(em));
		}
		return metalist;
	}
	
	public List<Request> getRequestsByEmployeeId(int id)
	{
		List<Request> list = new ArrayList<Request>();
		
		for (Request req: reqlist)
		{
			if(req.getPostingID() == id)
			{
				list.add(req);
			}
		}
		return list;
	}
	
	public Employee getEmployeeById(int id)
	{
		for (Employee emp : emplist)
		{
			if (emp.getEmployeeID() == id)
			return emp;
		}
		
		return null;
	}
	
	public Employee getEmployeeByUsername(String username)
	{
		for (Employee emp : emplist)
		{
			if(emp.getUsername().equals(username)) return emp;
		}
		return null;
	}
	
	public List<ManagementLink> getManagementLinksBySuperior(int id)
	{
		List<ManagementLink> list = new ArrayList<ManagementLink>();
		for(ManagementLink m : mllist)
		{
			if(m.getSuperiorID() == id)
			{
				list.add(m);
			}
		}
		
		return list;
	}

	public List<ManagementLink> getManagementLinksBySubordinate(int id)
	{
		List<ManagementLink> list = new ArrayList<ManagementLink>();
		for(ManagementLink m : mllist)
		{
			if(m.getSubordinateID() == id)
			{
				list.add(m);
			}
		}
		
		return list;
	}

	public List<ManagementLink> getManagementLinksBySuperiorDeep(int id)
	{
		List<ManagementLink> list = new ArrayList<ManagementLink>();
		List<ManagementLink> currentPass = new ArrayList<ManagementLink>();
		List<ManagementLink> latestPass = new ArrayList<ManagementLink>();
		for (ManagementLink m : mllist)
		{
			if(m.getSuperiorID() == id)
			{
				list.add(m);
				latestPass.add(m);
			}
		}
		
		while(!latestPass.isEmpty())
		{
			for (ManagementLink m : latestPass)
			{
				currentPass.addAll(getManagementLinksBySuperior(m.getSubordinateID()));
			}
			list.addAll(currentPass);
			latestPass = currentPass;
			currentPass = new ArrayList<ManagementLink>();
		}
		
		return list;
	}
	
	public List<ManagementLink> getManagementLinksBySubordinateDeep(int id)
	{
		List<ManagementLink> list = new ArrayList<ManagementLink>();
		List<ManagementLink> currentPass = new ArrayList<ManagementLink>();
		List<ManagementLink> latestPass = new ArrayList<ManagementLink>();
		for (ManagementLink m : mllist)
		{
			if(m.getSubordinateID() == id)
			{
				list.add(m);
				latestPass.add(m);
			}
		}
		
		while(!latestPass.isEmpty())
		{
			for (ManagementLink m : latestPass)
			{
				currentPass.addAll(getManagementLinksBySubordinate(m.getSuperiorID()));
			}
			list.addAll(currentPass);
			latestPass = currentPass;
			currentPass = new ArrayList<ManagementLink>();
		}
		
		return list;
	}
	
	public ManagementLink getManagementLinkById(int id)
	{
		ManagementLink m = null;
		for(ManagementLink min: mllist)
		{
			if(min.getManagementLinkID() == id) return min;
		}
		return m;
	}
	
	public Request getRequestById(int id)
	{
		Request r = null;
		for(Request rin: reqlist)
		{
			if(rin.getRequestID() == id) return rin;
		}
		return r;
	}
}
