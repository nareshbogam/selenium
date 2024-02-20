package com.qa.selenium.test.pageobjects;

import org.openqa.selenium.WebDriver;

import com.qa.selenium.util.SeleniumUtil;

public class TestPageObject extends SeleniumUtil{

	public TestPageObject(WebDriver driver) throws Exception {
		super(driver);
		System.out.println("test object : "+driver);
		// TODO Auto-generated constructor stub
	}
	private static final String	SIGNIN_BUTTON	=	"//a[@data-action='sign in']";
	private static final String  USER_NAME		=	"#identifierId";
	
	public void clickSignInButton() {
		click(SIGNIN_BUTTON, BY_XPATH);
	}
	public void enterUserName(String username) {
		fillInTextBox(USER_NAME, username, false, BY_CSS, false);
	}

}
