package com.revature.projects.wmagnus.project1.delegate;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewDelegate {

	public void returnView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		//Strip URI of contextpath
		String path = request.getRequestURI().substring(request.getContextPath().length());
		switch(path) {
		case "/":
		case "/login":
		case "/login.html":
			request.getRequestDispatcher("/static/login.html").forward(request, response);
			break;
		case "/viewEmployees":
		case "/viewEmployees.html":
			request.getRequestDispatcher("/static/viewEmployees.html").forward(request, response);;
			break;
		case "/homeEmployee":
		case "/homeEmployee.html":
			request.getRequestDispatcher("/static/home-employee.html").forward(request, response);
			break;
		case "/viewRequest":
		case "/viewRequest.html":
			request.getRequestDispatcher("/static/viewRequest.html").forward(request, response);
			break;
		case "/viewRequests":
		case "/viewRequests.html":
			request.getRequestDispatcher("/static/viewRequests.html").forward(request, response);
			break;
		case "/makeRequest":
		case "/makeRequest.html":
			request.getRequestDispatcher("/static/makeRequest.html").forward(request, response);
			break;
			
		case "/scripts/auth.js":
			request.getRequestDispatcher("/static/scripts/auth.js").forward(request, response);
			break;
		case "/scripts/authLogin.js":
			request.getRequestDispatcher("/static/scripts/authLogin.js").forward(request, response);
			break;
		case "/scripts/login.js":
			request.getRequestDispatcher("/static/scripts/login.js").forward(request, response);
			break;
		case "/scripts/authEmployeeHome.js":
			request.getRequestDispatcher("/static/scripts/authEmployeeHome.js").forward(request, response);
			break;
		case "/scripts/logout.js":
			request.getRequestDispatcher("/static/scripts/logout.js").forward(request, response);
			break;
		case "/scripts/authViewEmployees.js":
			request.getRequestDispatcher("/static/scripts/authViewEmployees.js").forward(request, response);
			break;
		case "/scripts/viewEmployees.js":
			request.getRequestDispatcher("/static/scripts/viewEmployees.js").forward(request, response);
			break;
		case "/scripts/authMakeRequest.js":
			request.getRequestDispatcher("/static/scripts/authMakeRequest.js").forward(request, response);
			break;
		case "/scripts/authViewRequest.js":
			request.getRequestDispatcher("/static/scripts/authViewRequest.js").forward(request, response);
			break;
		case "/scripts/authViewRequests.js":
			request.getRequestDispatcher("/static/scripts/authViewRequests.js").forward(request, response);
			break;
		case "/scripts/getSelf.js":
			request.getRequestDispatcher("/static/scripts/getSelf.js").forward(request, response);
			break;
		case "/scripts/viewSubordinates.js":
			request.getRequestDispatcher("/static/scripts/viewSubordinates.js").forward(request, response);
			break;
		case "/scripts/viewAvailableRequests.js":
			request.getRequestDispatcher("/static/scripts/viewAvailableRequests.js").forward(request, response);
			break;
		case "/scripts/singleRequestView.js":
			request.getRequestDispatcher("/static/scripts/singleRequestView.js").forward(request, response);
			break;
		case "/scripts/makeRequest.js":
			request.getRequestDispatcher("/static/scripts/makeRequest.js").forward(request, response);
			break;
		case "/scripts/updateUser.js":
			request.getRequestDispatcher("/static/scripts/updateUser.js").forward(request, response);
			break;
		default:
			response.sendError(404, "Static Resource Not Found");
		}
	}
}
