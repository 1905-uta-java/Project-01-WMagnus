package com.revature.projects.wmagnus.project1.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.projects.wmagnus.project1.models.Request;
import com.revature.projects.wmagnus.project1.util.ConnectionUtil;

public class RequestDAOImpl implements RequestDAO {

	@Override
	public List<Request> getAllRequests() {
		List<Request> list = new ArrayList<Request>();
		String sql_cmd = "SELECT * FROM REQUESTS";
		
		try (Connection connection = ConnectionUtil.getConectionFromFile();
				 Statement statement = connection.createStatement();
				 ResultSet results = statement.executeQuery(sql_cmd);)
			{
				while(results.next())
				{
					int reqid = results.getInt(1);
					int postemp = results.getInt(2);
					int resmgr = results.getInt(3);
					char approval  = results.getString(4).charAt(0);
					String subj = results.getString(5);
					String expl = results.getString(6);
					double amt = results.getDouble(7);
					list.add(new Request(reqid,postemp,resmgr,approval,subj,expl,amt));
				}
			} catch (SQLException | IOException e) {
				e.printStackTrace();
			}

		return list;
	}

	@Override
	public Request getRequestById(int id) {
		String sql_cmd = "SELECT * FROM REQUESTS WHERE REQ_ID = ?";
		Request r = null;
		
		try (Connection connection = ConnectionUtil.getConectionFromFile();
			 PreparedStatement pstatement = connection.prepareStatement(sql_cmd);)
		{
			pstatement.setInt(1, id);
			ResultSet results = pstatement.executeQuery();
			
			while(results.next())
			{
				int postemp = results.getInt(2);
				int resmgr = results.getInt(3);
				char approval = results.getString(4).charAt(0);
				String subj = results.getString(5);
				String expl = results.getString(6);
				double amt = results.getDouble(7);
				r = new Request(id,postemp,resmgr,approval,subj,expl,amt);
			}
			results.close();
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		return r;
	}

	@Override
	public List<Request> getRequestsByPostEmployee(int id) {
		String sql_cmd = "SELECT * FROM REQUESTS WHERE POST_EMPLOYEE = ?";
		List<Request> list = new ArrayList<Request>();
		
		try (Connection connection = ConnectionUtil.getConectionFromFile();
			 PreparedStatement pstatement = connection.prepareStatement(sql_cmd);)
		{
			pstatement.setInt(1, id);
			ResultSet results = pstatement.executeQuery();

			while(results.next())
			{
				int reqid = results.getInt(1);
				int resmgr = results.getInt(3);
				char approval = results.getString(4).charAt(0);
				String subj = results.getString(5);
				String expl = results.getString(6);
				double amt = results.getDouble(7);
				list.add(new Request(reqid,id,resmgr,approval,subj,expl,amt));
			}
			results.close();
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Request> getRequestsByApproval(char approvalState) {
		String sql_cmd = "SELECT * FROM REQUESTS WHERE APPROVAL_STATE = ?";
		List<Request> list = new ArrayList<Request>();
		
		try (Connection connection = ConnectionUtil.getConectionFromFile();
			 PreparedStatement pstatement = connection.prepareStatement(sql_cmd);)
		{
			pstatement.setString(1, ((Character)approvalState).toString());
			ResultSet results = pstatement.executeQuery();

			while(results.next())
			{
				int reqid = results.getInt(1);
				int postemp = results.getInt(2);
				int resmgr = results.getInt(3);
				String subj = results.getString(5);
				String expl = results.getString(6);
				double amt = results.getDouble(7);
				list.add(new Request(reqid,postemp,resmgr,approvalState,subj,expl,amt));
			}
			results.close();
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int createRequest(Request r) {
		
		
		String sql_cmd = "INSERT INTO REQUESTS (REQ_ID, POST_EMPLOYEE, RESOLVING_MGR, APPROVAL_STATE, SUBJECT, EXPLANATION, AMOUNT) VALUES (?,?,?,?,?,?,?)";
		int rowchg = 0;
		
		try (Connection connection = ConnectionUtil.getConectionFromFile();
			 PreparedStatement pstatement = connection.prepareStatement(sql_cmd);)
		{
			pstatement.setInt(1, r.getRequestID());
			pstatement.setInt(2, r.getPostingID());
			if(r.getResolvingManagerID() != 0)
			{
				pstatement.setInt(3, r.getResolvingManagerID());
			}
			else
			{
				pstatement.setNull(3, java.sql.Types.INTEGER);
			}
			pstatement.setString(4, ((Character)r.getApprovalState()).toString());
			pstatement.setString(5, r.getSubject());
			pstatement.setString(6, r.getExplanation());
			pstatement.setDouble(7, r.getAmount());
			
			rowchg = pstatement.executeUpdate();
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		return rowchg;
	}

	@Override
	public int updateRequest(Request r) {
		String sql_cmd = "UPDATE REQUESTS SET RESOLVING_MGR = ?, APPROVAL_STATE = ?, SUBJECT = ?, EXPLANATION = ?, AMOUNT = ? WHERE REQ_ID = ?";
		int rowchg = 0;
		
		try (Connection connection = ConnectionUtil.getConectionFromFile();
			 PreparedStatement pstatement = connection.prepareStatement(sql_cmd);)
		{
			if(r.getResolvingManagerID() != 0)
			{
				pstatement.setInt(1, r.getResolvingManagerID());
			}
			else
			{
				pstatement.setNull(1, java.sql.Types.INTEGER);
			}			
			pstatement.setString(2, ((Character)r.getApprovalState()).toString());
			pstatement.setString(3, r.getSubject());
			pstatement.setString(4, r.getExplanation());
			pstatement.setDouble(5, r.getAmount());
			pstatement.setInt(6, r.getRequestID());
			
			rowchg = pstatement.executeUpdate();
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		return rowchg;
	}

	@Override
	public int deleteRequest(int id) {
		String sql_cmd = "DELETE FROM REQUESTS WHERE REQ_ID = ?";
		int rowchg = 0;
		
		try (Connection connection = ConnectionUtil.getConectionFromFile();
			 PreparedStatement pstatement = connection.prepareStatement(sql_cmd);)
		{
			pstatement.setInt(1, id);
			rowchg = pstatement.executeUpdate();
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		
		return rowchg;
	}

}
