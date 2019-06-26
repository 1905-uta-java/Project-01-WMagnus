package com.revature.projects.wmagnus.project1.delegate;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.projects.wmagnus.project1.internals.Validation;
import com.revature.projects.wmagnus.project1.models.Employee;
import com.revature.projects.wmagnus.project1.models.UnencryptedToken;
import com.revature.projects.wmagnus.project1.util.AuthCryptoUtil;
import com.revature.projects.wmagnus.project1.util.HashUtil;

public class AuthDelegate {

	DBInternalStorageDelegate db;
	
	public static final long TIMEOUT_MS = 900000;

	
	public AuthDelegate() {
		super();
	}
			
	public AuthDelegate(DBInternalStorageDelegate db)
	{
		super();
		this.db = db;
	}
	
	public boolean checkValidity(String username, String password)
	{
		if (!Validation.validUsername(username) || !Validation.validPassword(password)) return false;
		Employee e = db.getEmployeeByUsername(username);
		System.out.println(e);
		if (e == null) return false;
		String hashed = HashUtil.hashStr(password);
		if(!e.getHashpass().equalsIgnoreCase(hashed)) return false;
		
		return true;
	}
	
	public UnencryptedToken assembleToken(String username, String password, String ip, Timestamp ts)
	{
		if(!checkValidity(username,password)) return null;
		
		UnencryptedToken token = new UnencryptedToken();
		
		Employee e = db.getEmployeeByUsername(username);
		if (e == null) return null; // just in case things have changed since checkvalidity... somehow.
		token.employeeId = e.getEmployeeID();
		token.hashpass = e.getHashpass();
		token.ip = ip;
		token.timestampAsString = ts.toString();
		if (db.getManagementLinksBySuperior(e.getEmployeeID()).size() > 0) token.isManager = true;
		else token.isManager = false;
		
		//System.out.println(token);
		UnencryptedToken.fromString(token.toString());
		
		return token;
	}
	
	
	
	public boolean authLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("authlogin called");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		UnencryptedToken ut;
		if (checkValidity(username,password))
		{
			System.out.println("valid salad");
			
			ut = assembleToken(username, password, request.getRemoteAddr(), new Timestamp(System.currentTimeMillis()));
			
			String s = AuthCryptoUtil.encrypt(ut.toString());
			System.out.println(s);
			
			response.setStatus(200);
			response.setHeader("auth",s);
			//response.setStatus(200);
			//String d = AuthCryptoUtil.decrypt(s);
			//System.out.println(d);
			
			return true;
		}	
		response.sendError(401, "Incorrect Username/Password.");
		return false;
	}
	
	public boolean authTokenMinimal(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		UnencryptedToken ut = this.authTokenFull(request, response);
		if(ut == null) 
		{
			response.sendError(401, "Bad token or Invalid Login");
			return false;
		}
		
		response.setStatus(200);
		return true;
	}
	
	public UnencryptedToken authTokenFull(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("authtoken max called");
		
		UnencryptedToken ut;
		String token = request.getParameter("authtoken");
		if (token == null) return null;
		String dc = AuthCryptoUtil.decrypt(token);
		if(dc == null) 
		{
			return null;
		}
		System.out.println(dc);
		ut = UnencryptedToken.fromString(dc);
		Employee e = db.getEmployeeById(ut.employeeId);
		System.out.println(e);
		if(!e.getHashpass().equalsIgnoreCase(ut.hashpass))
		{
			return null;
		}
		if(!request.getRemoteAddr().equals(ut.ip)) return null;
		
		Timestamp curTimedout = new Timestamp(System.currentTimeMillis()-TIMEOUT_MS);
		Timestamp past = Timestamp.valueOf(ut.timestampAsString);
		if(past.before(curTimedout)) return null;
		
		return ut;
	}
}
