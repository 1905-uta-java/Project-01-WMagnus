package com.revature.projects.wmagnus.project1;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.revature.projects.wmagnus.project1.internals.Validation;



public class ValidationTests {

	public ValidationTests()
	{
		super();
	}
	
	@Test
	public void ValidationTestsSanity()
	{
		assertTrue(true);
	}
	
	//Validation.validEmail tests
	@Test
	public void validEmailSqlInject()
	{
		assertFalse(Validation.validEmail("wm@efoe.com\'); SELECT"));
	}
	
	@Test
	public void validEmailBadCharsUser()
	{
		assertFalse(Validation.validEmail("in#al!d@email.com"));
	}
	
	@Test
	public void validEmailBadCharsDomain()
	{
		assertFalse(Validation.validEmail("invalid@em*al.c$m"));
	}
	
	@Test
	public void validEmailNoUser()
	{
		assertFalse(Validation.validEmail("@google.com"));
	}
	
	@Test
	public void validEmailNoDomain()
	{
		assertFalse(Validation.validEmail("dude@"));
	}
	
	@Test
	public void validEmailImpossiblyShort()
	{
		assertFalse(Validation.validEmail("@.c"));
	}
	
	@Test
	public void validEmailTooLong()
	{
		assertFalse(Validation.validEmail("deadbeef00@way.too.long.domain.co"));
	}
	
	@Test
	public void validEmailTrailingPeriod()
	{
		assertFalse(Validation.validEmail("invalid@email.com."));
	}
	
	@Test
	public void validEmailStartingPeriod()
	{
		assertFalse(Validation.validEmail(".invalid@email.com"));
	}
	
	@Test
	public void validEmailNoAt()
	{
		assertFalse(Validation.validEmail("dudegoogle.com"));
	}
	
	@Test
	public void validEmailShortSimple()
	{
		assertTrue(Validation.validEmail("wsm@efoe.com"));
	}
	
	@Test
	public void validEmailComplex()
	{
		assertTrue(Validation.validEmail("nicole.paiva@nevada.unr.edu"));
	}
	//Validation.validPassword tests
	@Test
	public void validPasswordTooShort()
	{
		assertFalse(Validation.validPassword("aaaaa"));
	}
	
	@Test
	public void validPasswordTooLong()
	{
		assertFalse(Validation.validPassword("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
	}
	
	@Test
	public void validPasswordBadCharacters()
	{
		assertFalse(Validation.validPassword("abc123#$%^\"!"));
	}
	
	@Test
	public void validPasswordValidSimple()
	{
		assertTrue(Validation.validPassword("abcdef0123"));
	}
	
	@Test
	public void validPasswordValidComplex()
	{
		assertTrue(Validation.validPassword("abc!_123.?"));
	}
	
	//Validation.validUsername tests
	@Test
	public void validUsernameTooShort()
	{
		assertFalse(Validation.validUsername("aaaaa"));
	}
	
	@Test
	public void validUsernameTooLong()
	{
		assertFalse(Validation.validUsername("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
	}
	
	@Test
	public void validUsernameBadCharacters()
	{
		assertFalse(Validation.validUsername("sql_injection!\'\"\';"));
	}
	
	@Test
	public void validUsernameBadCharactersPasswordValidChars()
	{
		assertFalse(Validation.validUsername("invalid_username?"));
	}
	
	@Test
	public void validUsernameValidSimple()
	{
		assertTrue(Validation.validUsername("validUsername"));
	}
	
	@Test
	public void validUsernameValidComplex()
	{
		assertTrue(Validation.validUsername("Valid_Username01"));
	}
	
	//Validation.validName tests
	
	@Test
	public void validNameTooShort()
	{
		assertFalse(Validation.validName("a"));
	}
	
	@Test
	public void validNameTooLong()
	{
		assertFalse(Validation.validName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
	}
	
	@Test
	public void validNameBadChars()
	{
		assertFalse(Validation.validName("Papa-Alfa$@!$#"));
	}
	
	@Test
	public void validNameValidShort()
	{
		assertTrue(Validation.validName("Al"));
	}
	
	@Test
	public void validNameValidLong()
	{
		assertTrue(Validation.validName("Somekindalongname"));
	}
	
	//Validation.validResetState tests
	@Test
	public void validResetStateInvalidResetState()
	{
		assertFalse(Validation.validResetState('v'));
	}
	
	@Test
	public void validResetStateBadCapitals()
	{
		assertFalse(Validation.validResetState('Y'));
	}
	
	@Test
	public void validResetStateYes()
	{
		assertTrue(Validation.validResetState('y'));
	}
	
	@Test
	public void validResetStateNo()
	{
		assertTrue(Validation.validResetState('n'));
	}
	
	//Validation.validSubject tests
	@Test
	public void validSubjectTooShort()
	{
		assertFalse(Validation.validSubject(""));
	}
	
	@Test
	public void validSubjectTooLong()
	{
		assertFalse(Validation.validSubject("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
	}
	
	@Test
	public void validSubjectBadChars()
	{
		assertFalse(Validation.validSubject("invalid salad\');"));
	}
	
	@Test
	public void validSubjectOneWord()
	{
		assertTrue(Validation.validSubject("Subject"));
	}
	
	@Test
	public void validSubjectMultiWord()
	{
		assertTrue(Validation.validSubject("A valid subject"));
	}
	
	@Test
	public void validSubjectPunctuated()
	{
		assertTrue(Validation.validSubject("Is this a valid subject?"));
	}
	
	//Validation.validExplanation tests
	@Test
	public void validExplanationTooLong()
	{
		assertFalse(Validation.validExplanation("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec ullamcorper mi augue, a fringilla mauris dapibus et. Donec ultrices ligula in nunc semper gravida. Vestibulum quis sem sem. Aenean eget dolor velit. Suspendisse tristique, erat vel sollicitudin rhoncus, risus libero pulvinar augue, eu pharetra nisi metus eu felis. Curabitur scelerisque cursus erat pretium suscipit. Proin blandit lorem quis commodo viverra.\r\n" + 
				"\r\n" + 
				"Aliquam interdum turpis sed nulla aliquam ultrices. Integer condimentum pharetra enim quis porttitor. Quisque quis vulputate leo. Fusce vel posuere purus. Quisque ante erat, tempus aliquam ligula semper, ultricies interdum lectus. Pellentesque eros sem, pulvinar sit amet mauris ac, blandit sodales turpis. Curabitur eu magna venenatis, efficitur dolor sed, maximus velit. Pellentesque in convallis arcu, sit amet fringilla enim. Sed sed ipsum nec arcu maximus tincidunt.\r\n" + 
				"\r\n" + 
				"Sed eget leo auctor nibh scelerisque interdum. Nullam neque erat, hendrerit eget lectus ac, lacinia pharetra nisi. Aenean id posuere."));
	}
	
	@Test
	public void validExplanationSqlInject()
	{
		assertFalse(Validation.validExplanation("valid explanation\');"));
	}
	
	@Test
	public void validExplanationComplexSqlInject()
	{
		assertFalse(Validation.validExplanation("valid explanation \\\'\\)\\;"));
	}
	
	@Test
	public void validExplanationVeryLong()
	{
		assertTrue(Validation.validExplanation("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec ullamcorper mi augue, a fringilla mauris dapibus et. Donec ultrices ligula in nunc semper gravida. Vestibulum quis sem sem. Aenean eget dolor velit. Suspendisse tristique, erat vel sollicitudin rhoncus, risus libero pulvinar augue, eu pharetra nisi metus eu felis. Curabitur scelerisque cursus erat pretium suscipit. Proin blandit lorem quis commodo viverra.\r\n" + 
				"\r\n" + 
				"Aliquam interdum turpis sed nulla aliquam ultrices. Integer condimentum pharetra enim quis porttitor. Quisque quis vulputate leo. Fusce vel posuere purus. Quisque ante erat, tempus aliquam ligula semper, ultricies interdum lectus. Pellentesque eros sem, pulvinar sit amet mauris ac, blandit sodales turpis. Curabitur eu magna venenatis, efficitur dolor sed, maximus velit. Pellentesque in convallis arcu, sit amet fringilla enim. Sed sed ipsum nec arcu maximus tincidunt.\r\n" + 
				"\r\n" + 
				"Sed eget leo auctor nibh scelerisque interdum. Nullam neque erat, hendrerit eget lectus ac, lacinia pharetra nisi."));
	}
	
	@Test
	public void validExplanationShort()
	{
		assertTrue(Validation.validExplanation("This is an explanation."));
	}
	
	@Test
	public void validExplanationNone()
	{
		assertTrue(Validation.validExplanation(""));
	}
	
	//Validation.validAmount tests
	@Test
	public void validAmountNegative()
	{
		assertFalse(Validation.validAmount(-4.5));
	}
	
	@Test
	public void validAmountZero()
	{
		assertFalse(Validation.validAmount(0.0));
	}
	
	@Test
	public void validAmountPositive()
	{
		assertTrue(Validation.validAmount(1.0));
	}
	
}
