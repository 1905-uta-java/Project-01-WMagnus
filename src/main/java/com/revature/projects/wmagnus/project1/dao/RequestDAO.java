package com.revature.projects.wmagnus.project1.dao;

import java.util.List;

import com.revature.projects.wmagnus.project1.models.Request;

public interface RequestDAO {
	
	public List<Request> getAllRequests();
	public Request getRequestById(int id);
	public List<Request> getRequestsByPostEmployee(int id);
	public List<Request> getRequestsByApproval(char approvalState);
	
	public int createRequest(Request r);
	
	public int updateRequest(Request r);
	
	public int deleteRequest(int id); 
}
