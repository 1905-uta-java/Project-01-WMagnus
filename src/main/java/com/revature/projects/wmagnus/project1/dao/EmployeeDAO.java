package com.revature.projects.wmagnus.project1.dao;

import java.util.List;

import com.revature.projects.wmagnus.project1.models.Employee;

public interface EmployeeDAO {		
	public List<Employee> getAllEmployees();
	public Employee getEmployeeById(int id);
	public Employee getEmployeeByUsername(String username);
	
	public int createEmployee(Employee e);
	
	public int updateEmployee(Employee e);
	
	public int deleteEmployee(int id);
}
