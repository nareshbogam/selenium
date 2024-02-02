package com.qa.selenium.test;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import com.qa.selenium.test.pageobjects.TestPageObject;

public class Testcase1 extends CommonTest{

	WebDriver driver;
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
	public void testCase2() {
		System.out.println("TEST CASE 1");
	}
	@Test
	public void testCase3() {
		System.out.println("TEST CASE 1");
	}
}
