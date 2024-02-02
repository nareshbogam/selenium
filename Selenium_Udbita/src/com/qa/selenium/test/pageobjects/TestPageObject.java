package com.qa.selenium.test.pageobjects;

import org.openqa.selenium.WebDriver;

import com.qa.selenium.util.SeleniumUtil;

public class TestPageObject extends SeleniumUtil{

	public TestPageObject(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	public static final String	SIGNIN_BUTTON	=	"//a[@data-action='sign in']";
	public void clickSignInButton() {
		click(SIGNIN_BUTTON, BY_XPATH);
	}

}
