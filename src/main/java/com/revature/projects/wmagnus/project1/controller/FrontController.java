package com.revature.projects.wmagnus.project1.controller;

import java.io.IOException;

import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.servlets.DefaultServlet;

import com.revature.projects.wmagnus.project1.util.AuthCryptoUtil;
import com.revature.projects.wmagnus.project1.util.ConnectionUtil;

public class FrontController extends DefaultServlet{

	private static final long serialVersionUID = 1L;
	private RequestHelper reqhelp = null;
	
	public FrontController()
	{
		super();
		AuthCryptoUtil.init();
		ConnectionUtil.initConnections();
		reqhelp = new RequestHelper();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Invoke defaultservlet's get handling when static resource is requested
		//Gets the end of the URL, stripping off the contextpath
		System.out.println(request.getRequestURI());
		if(request.getRequestURI().substring(request.getContextPath().length()).startsWith("/static/"))
		{
			super.doGet(request, response);
		}
		else
		{
			reqhelp.processGet(request, response);
			//request.getRequestDispatcher("/static/login.html").forward(request, response);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqhelp.processPost(request, response);
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		reqhelp.processPut(request, response);
	}
	
	public void destroy()
	{
		ConnectionUtil.destroyDriver();
	}
}
