package com.qa.selenium.test;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.qa.selenium.core.SeleniumBase;

public class CommonTest extends SeleniumBase{

	protected WebDriver driver;

	public CommonTest() throws Exception {
		super();
		this.driver=initializeDriver();
		System.err.println(" printing from commontest");
		// TODO Auto-generated constructor stub
	}

	@BeforeClass
	public void openApplication(){
		try {
			System.out.println("Before all");
			loginToSystem("", "");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	@AfterClass
	public void closeApplication() {
		try {
			System.out.println("Executing closeApplication section and it logs out the application and kills web browser");
			endSession();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
