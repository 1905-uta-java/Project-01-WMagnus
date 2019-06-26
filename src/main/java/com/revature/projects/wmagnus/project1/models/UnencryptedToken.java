package com.revature.projects.wmagnus.project1.models;

import java.io.Serializable;

public class UnencryptedToken implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public UnencryptedToken()
	{
		super();
	}
	
	public int employeeId;
	public boolean isManager;
	public String hashpass;
	public String ip;
	public String timestampAsString;
	
	@Override
	public String toString()
	{
		if (hashpass.length() != 64 || ip == null || timestampAsString == null)	return null;
		String out = "" + employeeId;
		out += ","+isManager;
		out += ","+hashpass;
		out += ","+ip;
		out += ","+timestampAsString;
		
		return out;
	}

	public static UnencryptedToken fromString(String s)
	{
		UnencryptedToken ut = new UnencryptedToken();
		String[] data = s.split(",");
		if (data.length < 5) return null;
		ut.employeeId = Integer.parseInt(data[0]);
		ut.isManager = Boolean.parseBoolean(data[1]);
		ut.hashpass = data[2];
		ut.ip = data[3];
		ut.timestampAsString = data[4];
		return ut;
	}
}
