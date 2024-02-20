package com.qa.selenium.test;

import org.testng.annotations.Test;
import com.qa.selenium.test.pageobjects.TestPageObject;

public class Testcase1 extends CommonTest{

	//CommonTest cTest=new CommonTest();
	TestPageObject test= new TestPageObject(driver);
	public Testcase1() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Test
	public void clickSignInButton() {
		System.out.println("TEST CASE 1");
		test.clickSignInButton();
	}
	@Test
	public void enterUserName() {
		System.out.println("TEST CASE 2");
		test.enterUserName("username");
	}
	@Test
	public void testCase3() {
		System.out.println("TEST CASE 2");
	}
}
