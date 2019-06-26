package com.revature.projects.wmagnus.project1.dao;

import java.util.List;

import com.revature.projects.wmagnus.project1.models.ManagementLink;

public interface ManagementLinkDAO {

	public List<ManagementLink> getAllManagementLinks();
	public ManagementLink getManagementLinkById(int id);
	public List<ManagementLink> getManagementLinksBySuperior(int id);
	public List<ManagementLink> getManagementLinksBySubordinate(int id);
	
	public int createManagementLink(ManagementLink m);
	
	public int updateManagementLink(ManagementLink m);
	
	public int deleteManagementLink(int id);
}
