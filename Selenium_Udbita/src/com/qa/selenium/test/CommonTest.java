package com.qa.selenium.test;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.qa.selenium.core.SeleniumBase;

public class CommonTest extends SeleniumBase{

	
	public CommonTest() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}


	@BeforeClass
	public void openApplication(){
		loginToSystem("", "");
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
