package com.revature.projects.wmagnus.project1.models;

public class ManagementLink {
	private int managementLinkID;
	private int subordinateID;
	private int superiorID;
	
	public ManagementLink()
	{
		super();
	}
	
	public ManagementLink(int managementLinkID, int subordinateID, int superiorID)
	{
		this.managementLinkID = managementLinkID;
		this.subordinateID = subordinateID;
		this.superiorID = superiorID;
	}
	
	public int getManagementLinkID() {
		return managementLinkID;
	}
	public void setManagementLinkID(int managementLinkID) {
		this.managementLinkID = managementLinkID;
	}
	public int getSubordinateID() {
		return subordinateID;
	}
	public void setSubordinateID(int subordinateID) {
		this.subordinateID = subordinateID;
	}
	public int getSuperiorID() {
		return superiorID;
	}
	public void setSuperiorID(int superiorID) {
		this.superiorID = superiorID;
	}

	@Override
	public String toString()
	{
		return "managementLink={" + "managementLinkID:" + managementLinkID + ",subordinateID:" + subordinateID + ",superiorID:" + superiorID +"}";
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null) return false;
		if (getClass() != o.getClass()) return false;
		ManagementLink m = (ManagementLink) o;
		if(m.managementLinkID != this.managementLinkID) return false;
		if(m.subordinateID != this.subordinateID) return false;
		if(m.superiorID != this.superiorID) return false;
		return true;
	}
}
