package com.revature.projects.wmagnus.project1.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.projects.wmagnus.project1.delegate.APIDelegate;
import com.revature.projects.wmagnus.project1.delegate.AuthDelegate;
import com.revature.projects.wmagnus.project1.delegate.DBInternalStorageDelegate;
import com.revature.projects.wmagnus.project1.delegate.ViewDelegate;

public class RequestHelper {

	private DBInternalStorageDelegate db;
	
	private ViewDelegate vd;
	private AuthDelegate ad;
	private APIDelegate apid;
	
	public RequestHelper()
	{
		super();
		vd = new ViewDelegate();
		db = new DBInternalStorageDelegate();
		ad = new AuthDelegate(db);
		apid = new APIDelegate(ad, db);
	}
	
	public void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String uri = request.getRequestURI().substring(request.getContextPath().length());
		System.out.println("gethere:" + uri);

		if(uri.startsWith("/static/"))
		{
			System.out.println(uri);
			
			vd.returnView(request, response);
		}
		else if(uri.startsWith("/api/"))
		{
			System.out.println(uri);
			apid.handleAPIMapping(request, response);
		}
		else
		{
			vd.returnView(request, response);
		}
	}
	
	public void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String uri = request.getRequestURI().substring(request.getContextPath().length());
		System.out.println("posthere:" + uri);
		String token;
		
		if(uri.startsWith("/api/")) apid.handleAPIMapping(request, response);

		switch(uri)
		{
		case "/login":
			token = request.getParameter("authtoken");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			System.out.println(request.getContentLength());
			if (token != null)
			{
				System.out.println("authtoken");
				System.out.println(token);
				ad.authTokenMinimal(request, response);
			}
			else if(username!=null && password!=null)
			{
				System.out.println("username="+username +"&password="+password);
				ad.authLogin(request, response);
			}
			break;
		case "/homeEmployee":
		case "/viewEmployees":
		case "/makeRequest":
		case "/viewRequest":
		case "/viewRequests":
			token = request.getParameter("authtoken");
			if(token == null) response.sendError(403, "Forbidden - No Auth Token.");
			else
			{
				ad.authTokenMinimal(request, response);
			}
			break;
		default:
			break;
		}
		
	}
	
	public void processPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String uri = request.getRequestURI().substring(request.getContextPath().length());
		System.out.println("posthere:" + uri);
		
		if(uri.startsWith("/api/")) apid.handleAPIMapping(request, response);
		
	}
}
