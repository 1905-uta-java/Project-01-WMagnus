package com.revature.projects.wmagnus.project1.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.projects.wmagnus.project1.models.Employee;
import com.revature.projects.wmagnus.project1.util.ConnectionUtil;

public class EmployeeDAOImpl implements EmployeeDAO {

	public List<Employee> getAllEmployees() {
		List<Employee> list = new ArrayList<Employee>();
		
		String sql_cmd = "SELECT * FROM EMPLOYEES";
		
		try (Connection connection = ConnectionUtil.getConectionFromFile();
			 Statement statement = connection.createStatement();
			 ResultSet results = statement.executeQuery(sql_cmd);)
		{
			while(results.next())
			{
				int eid = results.getInt("EMPLOYEE_ID");
				String first = results.getString("FIRST_NAME");
				String last = results.getString("LAST_NAME");
				String email = results.getString("EMAIL");
				String username = results.getString("USERNAME");
				String hashpass = results.getString("HASHPASS");
				char needsReset = results.getString("NEEDS_RESET").charAt(0);
				list.add(new Employee(eid,first,last,email,username,hashpass,needsReset));
			}
		} catch (SQLException | IOException err) {
			err.printStackTrace();
		}
		
		return list;
	}

	public Employee getEmployeeById(int id) {
		
		String sql_cmd = "SELECT * FROM EMPLOYEES WHERE EMPLOYEE_ID = ?";
		Employee e = null;
		
		try (Connection connection = ConnectionUtil.getConectionFromFile();
			 PreparedStatement pstatement = connection.prepareStatement(sql_cmd);)
		{
			pstatement.setInt(1, id);
			ResultSet results = pstatement.executeQuery();
			
			while (results.next())
			{
				int eid = id;
				String first = results.getString("FIRST_NAME");
				String last = results.getString("LAST_NAME");
				String email = results.getString("EMAIL");
				String username = results.getString("USERNAME");
				String hashpass = results.getString("HASHPASS");
				char needsReset = results.getString("NEEDS_RESET").charAt(0);
				e = new Employee(eid,first,last,email,username,hashpass,needsReset);
			}
			results.close();
		} catch (SQLException | IOException err) {
			err.printStackTrace();
		}
		return e;
	}

	public Employee getEmployeeByUsername(String username) {
		
		String sql_cmd = "SELECT * FROM EMPLOYEES WHERE USERNAME = ?";
		Employee e = null;
		
		try (Connection connection = ConnectionUtil.getConectionFromFile();
			 PreparedStatement pstatement = connection.prepareStatement(sql_cmd);)
		{
			pstatement.setString(1, username);
			ResultSet results = pstatement.executeQuery();
			
			while (results.next())
			{
				int eid = results.getInt("EMPLOYEE_ID");
				String first = results.getString("FIRST_NAME");
				String last = results.getString("LAST_NAME");
				String email = results.getString("EMAIL");
				String hashpass = results.getString("HASHPASS");
				char needsReset = results.getString("NEEDS_RESET").charAt(0);
				e = new Employee(eid,first,last,email,username,hashpass,needsReset);
			}
			results.close();
		} catch (SQLException | IOException err) {
			err.printStackTrace();
		}
		return e;
	}

	public int createEmployee(Employee e) {
		String sql_cmd = "INSERT INTO EMPLOYEES (EMPLOYEE_ID, FIRST_NAME, LAST_NAME, EMAIL, USERNAME, HASHPASS, NEEDS_RESET) VALUES (?,?,?,?,?,?,?)";
		int rowchg = 0;
		try (Connection connection = ConnectionUtil.getConectionFromFile();
			 PreparedStatement pstatement = connection.prepareStatement(sql_cmd);)
		{
			pstatement.setInt(1, e.getEmployeeID());
			pstatement.setString(2, e.getFirstName());
			pstatement.setString(3, e.getLastName());
			pstatement.setString(4, e.getEmail());
			pstatement.setString(5, e.getUsername());
			pstatement.setString(6, e.getHashpass());
			pstatement.setString(7, ((Character) e.getNeedsReset()).toString()); 
			rowchg = pstatement.executeUpdate();
		} catch (SQLException | IOException e1) {
			e1.printStackTrace();
		}
		return rowchg;
	}

	public int updateEmployee(Employee e) {
		String sql_cmd = "UPDATE EMPLOYEES SET FIRST_NAME = ?, LAST_NAME = ?, EMAIL=?, USERNAME=?, HASHPASS=?, NEEDS_RESET=? WHERE EMPLOYEE_ID = ?";
		int rowchg = 0;
		try (Connection connection = ConnectionUtil.getConectionFromFile();
			 PreparedStatement pstatement = connection.prepareStatement(sql_cmd);)
		{
			pstatement.setString(1, e.getFirstName());
			pstatement.setString(2, e.getLastName());
			pstatement.setString(3, e.getEmail());
			pstatement.setString(4, e.getUsername());
			pstatement.setString(5, e.getHashpass());
			pstatement.setString(6, ((Character) e.getNeedsReset()).toString()); 
			pstatement.setInt(7, e.getEmployeeID());
			rowchg = pstatement.executeUpdate();
		} catch (SQLException | IOException e1) {
			e1.printStackTrace();
		}
		return rowchg;	
	}

	public int deleteEmployee(int id) {
		String sql_cmd = "DELETE FROM EMPLOYEES WHERE EMPLOYEE_ID = ?";
		int rowchg = 0;
		try (Connection connection = ConnectionUtil.getConectionFromFile();
			 PreparedStatement pstatement = connection.prepareStatement(sql_cmd);)
		{
			pstatement.setInt(1, id);
			rowchg = pstatement.executeUpdate();
		} catch (SQLException | IOException e1) {
			e1.printStackTrace();
		}
		return rowchg;
	}

}
