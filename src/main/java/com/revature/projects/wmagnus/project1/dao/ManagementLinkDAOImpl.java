package com.revature.projects.wmagnus.project1.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.projects.wmagnus.project1.models.ManagementLink;
import com.revature.projects.wmagnus.project1.util.ConnectionUtil;

public class ManagementLinkDAOImpl implements ManagementLinkDAO {

	@Override
	public List<ManagementLink> getAllManagementLinks() {
		List<ManagementLink> list = new ArrayList<ManagementLink>();
		
		String sql_cmd = "SELECT * FROM MANAGEMENT_HIERARCHY";
		
		try (Connection connection = ConnectionUtil.getConectionFromFile();
			 Statement statement = connection.createStatement();
			 ResultSet results = statement.executeQuery(sql_cmd);)
		{
			while(results.next())
			{
				int mh_id = results.getInt(1);
				int sub = results.getInt(2);
				int sup = results.getInt(3);
				list.add(new ManagementLink(mh_id, sub, sup));
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public ManagementLink getManagementLinkById(int id) {
		String sql_cmd = "SELECT * FROM MANAGEMENT_HIERARCHY WHERE MH_ID = ?";
		ManagementLink m = null;
		
		try (Connection connection = ConnectionUtil.getConectionFromFile();
			 PreparedStatement pstatement = connection.prepareStatement(sql_cmd);)
		{
			pstatement.setInt(1, id);
			ResultSet results = pstatement.executeQuery();
			
			while(results.next())
			{
				int mh_id = results.getInt(1);
				int sub = results.getInt(2);
				int sup = results.getInt(3);
				m = new ManagementLink(mh_id, sub, sup);
			}
			results.close();
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		return m;
	}

	@Override
	public List<ManagementLink> getManagementLinksBySuperior(int id) {
		String sql_cmd = "SELECT * FROM MANAGEMENT_HIERARCHY WHERE SUPERIOR = ?";
		List<ManagementLink> list = new ArrayList<ManagementLink>();
		
		try (Connection connection = ConnectionUtil.getConectionFromFile();
			 PreparedStatement pstatement = connection.prepareStatement(sql_cmd);)
		{
			pstatement.setInt(1, id);
			ResultSet results = pstatement.executeQuery();
			
			while(results.next())
			{
				int mh_id = results.getInt(1);
				int sub = results.getInt(2);
				int sup = results.getInt(3);
				list.add(new ManagementLink(mh_id, sub, sup));
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<ManagementLink> getManagementLinksBySubordinate(int id) {
		String sql_cmd = "SELECT * FROM MANAGEMENT_HIERARCHY WHERE SUBORDINATE = ?";
		List<ManagementLink> list = new ArrayList<ManagementLink>();
		
		try (Connection connection = ConnectionUtil.getConectionFromFile();
			 PreparedStatement pstatement = connection.prepareStatement(sql_cmd);)
		{
			pstatement.setInt(1, id);
			ResultSet results = pstatement.executeQuery();
			
			while(results.next())
			{
				int mh_id = results.getInt(1);
				int sub = results.getInt(2);
				int sup = results.getInt(3);
				list.add(new ManagementLink(mh_id, sub, sup));
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int createManagementLink(ManagementLink m) {
		String sql_cmd = "INSERT INTO MANAGEMENT_HIERARCHY (MH_ID, SUBORDINATE, SUPERIOR) VALUES(?,?,?)";
		int rowchg = 0;
		try (Connection connection = ConnectionUtil.getConectionFromFile();
			 PreparedStatement pstatement = connection.prepareStatement(sql_cmd);)
		{
			pstatement.setInt(1, m.getManagementLinkID());
			pstatement.setInt(2, m.getSubordinateID());
			pstatement.setInt(3, m.getSuperiorID());
			
			rowchg = pstatement.executeUpdate();
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		
		return rowchg;
	}

	@Override
	public int updateManagementLink(ManagementLink m) {
		String sql_cmd = "UPDATE MANAGEMENT_HIERARCHY SET SUBORDINATE = ?, SUPERIOR = ? WHERE MH_ID = ?";
		int rowchg = 0;
		try (Connection connection = ConnectionUtil.getConectionFromFile();
			 PreparedStatement pstatement = connection.prepareStatement(sql_cmd);)
		{
			pstatement.setInt(1, m.getSubordinateID());
			pstatement.setInt(2, m.getSuperiorID());
			pstatement.setInt(3, m.getManagementLinkID());

			rowchg = pstatement.executeUpdate();
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		
		return rowchg;
	}

	@Override
	public int deleteManagementLink(int id) {
		String sql_cmd = "DELETE FROM MANAGEMENT_HIERARCHY WHERE MH_ID = ?";
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
