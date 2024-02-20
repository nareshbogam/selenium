package com.qa.selenium.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import io.github.bonigarcia.wdm.WebDriverManager;

public class SeleniumBase {
	public WebDriver driver 			= null;
	private static String rootDirectory = System.getProperty("user.dir");	
	protected String envDataFile		= rootDirectory+"/src/main/resources/TestEnv.properties";
	private static Properties envProps		= new Properties();	
	private static String APP_URL			= null;
	private static int IMPLICIT_WAIT		= 20;
	private static int PAGE_LOAD_TIMEOUT	= 20;
	private static String BROWSER_INSTALL_PATH		= null;
	private static String BROWSER					= null;	
	private static String userName					= null;
	private static String password					= null;
	
	public SeleniumBase() throws Exception 
	{
		initializeAllVariables();	
		
	}
	
	public void initializeAllVariables() throws Exception
	{
		System.out.println("Working Directory = " + System.getProperty("user.dir"));
		System.out.println("Working Directory = " + envDataFile);
		BufferedReader reader			= null;
		InputStream fStream				= null;
		
		try
		{
			File envFile 			= ( new File( envDataFile ) );
			fStream 				= new FileInputStream( envFile );	
			reader					= new BufferedReader( new InputStreamReader( fStream ) );
			
			envProps.load( reader );		
			APP_URL					= envProps.getProperty( EnvConstants.APP_URL );
			IMPLICIT_WAIT			= Integer.parseInt( envProps.getProperty( EnvConstants.IMPLICIT_WAIT, "20" ) );
			PAGE_LOAD_TIMEOUT		= Integer.parseInt( envProps.getProperty( EnvConstants.PAGE_LOAD_TIMEOUT, "20" ) );
			BROWSER_INSTALL_PATH	= envProps.getProperty( EnvConstants.BROWSER_INSTALL_PATH );
			BROWSER					= envProps.getProperty( EnvConstants.BROWSER );			
					
		}
		catch( Exception ex )
		{
			ex.printStackTrace( System.out );
			throw ex;
		}
		finally
		{
			try 
			{
				fStream.close();
				reader.close();				
			} 
			catch( IOException e ) 
			{				
			}
		}
	}
	
	public WebDriver initializeDriver(){
		String browserName = BROWSER.toLowerCase();
		
		switch(browserName)
		{
		case "chrome": launchChromeBrowser(); break;
		case "firefox": launchFirefoxBrowser(); break;
		case "edge": launchEdgeeBrowser(); break;
		}
		driver.manage().timeouts().implicitlyWait( Duration.ofSeconds(IMPLICIT_WAIT) );
		driver.manage().timeouts().pageLoadTimeout( Duration.ofSeconds(PAGE_LOAD_TIMEOUT)); 
		driver.manage().window().maximize();
		return driver;

	}
	
	public void launchChromeBrowser() {
		try{
			WebDriverManager.chromedriver().setup();
		}
		catch(WebDriverException we) {
			System.setProperty("webdriver.chrome.driver", rootDirectory+"/drivers/chromedriver/chromedriver.exe");
			
		}
		finally {
		driver = new ChromeDriver();
		}
	}
	
	public void launchFirefoxBrowser() {
		WebDriverManager.firefoxdriver().setup();
		
	    try{
			WebDriverManager.chromedriver().setup();
		}
		catch(WebDriverException we) {
			System.setProperty("webdriver.chrome.driver", rootDirectory+"/drivers/chromedriver/chromedriver.exe");
			
		}
		finally {
			FirefoxProfile profile = new FirefoxProfile();
			FirefoxOptions options = new FirefoxOptions();
			options.setBinary(BROWSER_INSTALL_PATH);
			options.setProfile(profile);
		    driver = new FirefoxDriver(options);
		}
		
	}

	public void launchEdgeeBrowser() {
		WebDriverManager.edgedriver().setup();
		driver = new EdgeDriver();
	}
	public void loginToSystem( String userName, String password ) 
	{	driver.get(APP_URL);
	// enter username
	// enter password
	}
	
	public void endSession()
	{		
		try
		{	driver.close();		
			driver.quit();
			driver=null;
		}
		catch( Exception ex )
		{
			ex.printStackTrace( System.out );			
		}		
	}

}
