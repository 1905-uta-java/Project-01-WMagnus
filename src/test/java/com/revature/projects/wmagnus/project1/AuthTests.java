package com.revature.projects.wmagnus.project1;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import com.revature.projects.wmagnus.project1.delegate.AuthDelegate;

public class AuthTests {

	AuthDelegate ad = new AuthDelegate();
	
	public AuthTests()
	{
		super();
	}
	
	@Test
	public void checkValidityNoSuchUser()
	{
		assertFalse(ad.checkValidity("nosuchuserexists","password"));
	}
	
	@Test
	public void checkValidityGoodUserBadPass()
	{
		assertFalse(ad.checkValidity("munterling", "wrongpass"));
	}
	
	@Test
	public void checkValidityValid()
	{
		assertTrue(ad.checkValidity("munterling", "password_01"));
	}
}
