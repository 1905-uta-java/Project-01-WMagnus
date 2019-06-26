package com.revature.projects.wmagnus.project1.models;

import java.io.Serializable;

public class Request  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public static final int SUBJECT_MAX_LENGTH = 64;
	public static final int EXPLANATION_MAX_LENGTH = 1024;
	public static final double DEFAULT_MIN_AMOUNT = 0.01;
	
	private int requestID;
	private int postingID;
	private int resolvingManagerID;
	private char approvalState;
	private String subject;
	private String explanation;
	private double amount;
	
	public Request()
	{
		super();
	}
	
	public Request(int requestID, int postingID, int resolvingManagerID, char approvalState, String subject, String explanation, double amount)
	{
		this.requestID = requestID;
		this.postingID = postingID;
		this.resolvingManagerID = resolvingManagerID;
		this.approvalState = approvalState;
		if (subject.length() <= SUBJECT_MAX_LENGTH) this.subject = subject; else this.subject = null;
		if (explanation.length() <= EXPLANATION_MAX_LENGTH) this.explanation = explanation; else this.explanation = null;
		if (amount >= DEFAULT_MIN_AMOUNT) this.amount = amount; else this.amount = DEFAULT_MIN_AMOUNT;
	}
	
	public int getRequestID() {
		return requestID;
	}
	public void setRequestID(int requestID) {
		this.requestID = requestID;
	}
	public int getPostingID() {
		return postingID;
	}
	public void setPostingID(int postingID) {
		this.postingID = postingID;
	}
	public int getResolvingManagerID() {
		return resolvingManagerID;
	}
	public void setResolvingManagerID(int resolvingManagerID) {
		this.resolvingManagerID = resolvingManagerID;
	}
	public char getApprovalState() {
		return approvalState;
	}
	public void setApprovalState(char approvalState) {
		this.approvalState = approvalState;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		if (subject.length() <= SUBJECT_MAX_LENGTH) this.subject = subject;
	}
	public String getExplanation() {
		return explanation;
	}
	public void setExplanation(String explanation) {
		if (explanation.length() <= EXPLANATION_MAX_LENGTH) this.explanation = explanation;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		if (amount >= DEFAULT_MIN_AMOUNT) this.amount = amount;
	}
	

	@Override
	public String toString()
	{
		return "request={" + "rid:" + this.requestID + ",posterid:" + this.postingID + ",resmgrid:" + this.resolvingManagerID + ",amount:" + this.amount +",approval:" + ((Character)this.approvalState) + ",subj:"+ this.subject +",explanation:"+ this.explanation + "}";		
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null) return false;
		if (getClass() != o.getClass()) return false;
		Request r = (Request) o;
		if (this.requestID != r.requestID) return false;
		if (this.amount != r.amount) return false;
		if (this.approvalState != r.approvalState) return false;
		if (!this.explanation.equals(r.explanation)) return false;
		if (this.postingID != r.postingID) return false;
		if (this.resolvingManagerID != r.resolvingManagerID) return false;
		if (!this.subject.equals(r.subject)) return false;
		
		return true;
	}
}
