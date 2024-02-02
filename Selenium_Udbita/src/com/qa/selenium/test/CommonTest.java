package com.qa.selenium.test;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.qa.selenium.core.SeleniumBase;

public class CommonTest{

	private String envDataFile = "D:\\SeleniumBase\\Selenium_Udbita\\src\\config.properties";
	protected SeleniumBase base;
	public CommonTest() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@BeforeClass
	public void openApplication(){
		try {
			base= new SeleniumBase(envDataFile);
			System.out.println("Before all");
			base.loginToSystem("", "");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	@AfterClass
	public void closeApplication() {
		try {
			System.out.println("Executing closeApplication section and it logs out the application and kills web browser");
			base.endSession();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
