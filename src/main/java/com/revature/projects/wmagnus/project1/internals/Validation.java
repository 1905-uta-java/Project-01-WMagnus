package com.revature.projects.wmagnus.project1.internals;

import java.util.regex.Pattern;

import com.revature.projects.wmagnus.project1.models.Employee;
import com.revature.projects.wmagnus.project1.models.Request;

public class Validation {
	// why is this split up? because the max lengths for values handled in Request, Employee, etc, are intrinsic to the database, and thus to 
	// the containers as well.
	public static final int MAX_PASSWORD_LENGTH = 32;
	public static final int MIN_PASSWORD_LENGTH = 6;
	public static final int MIN_USERNAME_LENGTH = 6;
	public static final int MIN_NAME_LENGTH     = 2;
	public static final int MIN_SUBJECT_LENGTH  = 1;
	public static final int MIN_EXPLANATION_LENGTH = 0;
	public static final int MIN_EMAIL_SEGMENT_LENGTH = 1;
	
	private static final String SUBJCHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVVWXYZ0123456789!?&*+-,./=@ ";
	private static final String EXPLAINCHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVVWXYZ0123456789!?&*+-,./=@ \t\r\n";
	private static final String EMAILCHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVVWXYZ0123456789.";
	
	public static boolean validEmail(String email)
	{
		if (email.length() < MIN_EMAIL_SEGMENT_LENGTH*3 + 2 || email.length() > Employee.MAX_EMAIL_LENGTH) return false;
		boolean atInvoked = false;
		int lastSegmentation = 0;
		boolean sitePeriod = false;
		for (int iter = 0; iter < email.length(); iter++)
		{
			if(!atInvoked)
			{
				if(!EMAILCHARS.contains(((Character)email.charAt(iter)).toString()) && email.charAt(iter) != '@') 
				{
					return false;
				}
				if(iter - lastSegmentation-1 < MIN_EMAIL_SEGMENT_LENGTH && (email.charAt(iter) == '@' || email.charAt(iter) == '.')) return false; 
				if(email.charAt(iter) == '@') 
				{
					atInvoked = true;
					lastSegmentation = iter;
				}
				if(email.charAt(iter) == '.') lastSegmentation = iter;
			}
			else
			{
				if(!EMAILCHARS.contains(((Character)email.charAt(iter)).toString())) return false;
				if(iter - lastSegmentation-1 < MIN_EMAIL_SEGMENT_LENGTH && (email.charAt(iter) == '@' || email.charAt(iter) == '.')) return false; 
				if(email.charAt(iter) == '.') 
				{
					lastSegmentation = iter;
					sitePeriod = true;
				}
			}
		}
		if(atInvoked && sitePeriod && (email.length()-1 != lastSegmentation)) return true;
		return false;
	}
	
	public static boolean validPassword(String pw)
	{
		if (pw.length() < MIN_PASSWORD_LENGTH || pw.length() > MAX_PASSWORD_LENGTH) return false;
		char[] charsPW = pw.toLowerCase().toCharArray();
		
		for(int iter = 0; iter< charsPW.length; iter++)
		{
			if ((charsPW[iter] < 'a' || charsPW[iter] > 'z') && (charsPW[iter] < '0' || charsPW[iter] > '9') && 
					charsPW[iter] != '!' && charsPW[iter] != '.' && charsPW[iter] != '?' && charsPW[iter] != '_') return false;
		}
		return true;
	}

	public static boolean validUsername(String uid)
	{
		if (uid.length() < MIN_USERNAME_LENGTH || uid.length() > Employee.MAX_USERNAME_LENGTH) return false;
		
		char[] charsUID = uid.toLowerCase().toCharArray();
		
		for(int iter = 0; iter< charsUID.length; iter++)
		{
			if ((charsUID[iter] < 'a' || charsUID[iter] > 'z') && (charsUID[iter] < '0' || charsUID[iter] > '9') &&
				charsUID[iter] != '_') return false;
		}
		return true;
	}
	
	public static boolean validName(String name)
	{
		if (name.length() < MIN_NAME_LENGTH || name.length() > Employee.MAX_NAME_LENGTH) return false;
		
		return Pattern.matches("[a-zA-Z]{"+MIN_NAME_LENGTH+","+Employee.MAX_NAME_LENGTH+"}", name);
	}
	
	public static boolean validResetState(char state)
	{
		if(state == 'n' || state == 'y') return true;
		return false;
	}
	
	public static boolean validApprovalState(char state)
	{
		if(state == 'P' || state == 'N' || state == 'Y') return true;
		return false;
	}
	

	public static boolean validSubject(String subject)
	{
		if (subject.length() < MIN_SUBJECT_LENGTH || subject.length() > Request.SUBJECT_MAX_LENGTH) return false;
//		return Pattern.matches("[[\p{Print}]&&^[\"\';{}()\n\r]]", subject);

		//since eclipse does not like regex
		for(int iter = 0; iter< subject.length(); iter++)
		{
			if (!SUBJCHARS.contains(""+subject.charAt(iter))) return false;
		}
		return true;
	}
	
	public static boolean validExplanation(String explanation)
	{
		if(explanation.length() < MIN_EXPLANATION_LENGTH || explanation.length() > Request.EXPLANATION_MAX_LENGTH) return false;
		
		//since eclipse still doesn't like regex
		for(int iter = 0; iter < explanation.length(); iter++)
		{
			if(!EXPLAINCHARS.contains(""+explanation.charAt(iter))) return false;
		}
		return true;
	}
	
	public static boolean validAmount(double amount)
	{
		if (amount >= Request.DEFAULT_MIN_AMOUNT) return true;
		return false;
	}
}
