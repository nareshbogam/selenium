package com.qa.selenium.util;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.qa.selenium.core.SeleniumBase;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;

public class SeleniumUtil{

	protected WebDriver driver;
	
	protected static int BY_ID						= 0;
	protected static int BY_NAME					= 1;
	protected static int BY_XPATH					= 2;
	protected static int BY_CSS						= 3;
	protected static int BY_CLASS					= 4;
	
	private static final int RETRY_LIMIT			= 2;
	public static final int PAGE_RENDER_TIME 		= 2000;
	public static final int JS_LOAD_TIME 			= 200;
	
	public SeleniumUtil( WebDriver driver ) throws Exception 
	{
		this.driver 		= driver;	
		System.out.println(this.driver);
	}
	
	protected void fillInTextBox( String id, String value, boolean clearValue, int byType, boolean useSendKeys )
	{
		fillInTextBox( id, value, clearValue, byType, useSendKeys, -1 );
	}
	
	protected void fillInTextBox( String id, String value, boolean clearValue, int byType, boolean useSendKeys, int elementIndex )
	{			
		WebElement textboxElement		= null;		
		int count 						= 0;
		
		while( count < RETRY_LIMIT )
		{
			try
			{
				textboxElement		= getVisibleWebElement( id, byType, elementIndex );
								
				if( textboxElement == null )
				{
					throw new StaleElementReferenceException( "The element has still not become visible" );					
				}
				
				break;
			}
			catch( StaleElementReferenceException  ex )
			{
				System.out.println( "StaleElementReferenceException" + id ); 
				waitForPageLoad();
				count++;			
			}
		}
		
		if( textboxElement == null )
		{
			throw new NoSuchElementException( "The element has still not become visible or is not present. key: " + id );					
		}
		
		if( clearValue )
		{
			textboxElement.clear();
		}
		
		if( useSendKeys )
		{
			textboxElement.sendKeys( value );
			
		}
		else
		{
			JavascriptExecutor js = ( JavascriptExecutor )driver;
			js.executeScript( "arguments[0].value='" + value + "';", textboxElement );
		}
				
	}
	
	public void waitForPageLoad()
	{
		waitFor( PAGE_RENDER_TIME );
	}
	public void waitFor( int delay )
	{
		try 
		{
			Thread.sleep( delay );
		} 
		catch( InterruptedException e )
		{		
		}
	}

	protected WebElement getVisibleWebElement( String id, int byType, int index )
	{
		WebElement visibleElement		= null;
		List<WebElement> elementList	= getWebElementList( id, byType, index );	
		
		for( WebElement element: elementList )
		{
			if( element.isDisplayed() )
			{
				visibleElement = element;
				break;
			}				
		}
		
		return visibleElement;
	}
	
	protected List<WebElement> getWebElementList( String id, int byType, int index )
	{
		List<WebElement> elementList	= getWebElementList( id, byType );
		
		if( index != -1 )
		{
			List<WebElement> tempList = new ArrayList<WebElement>();
			tempList.add( elementList.get( index ) );
			return tempList;
		}
		else
		{
			return elementList;
		}		
	}
	
	protected List<WebElement> getWebElementList( String id, int byType )
	{
		List<WebElement> clickElementList = null;
		if( byType == BY_NAME )
		{
			clickElementList = driver.findElements( By.name( id ) );
		}
		else if( byType == BY_XPATH )
		{
			clickElementList = driver.findElements( By.xpath( id ) );
		}
		else if( byType == BY_CSS )
		{
			clickElementList = driver.findElements( By.cssSelector( id ) );
		}
		else if( byType == BY_CLASS )
		{
			clickElementList = driver.findElements( By.className( id ) );
		}
		else
		{
			clickElementList = driver.findElements( By.id( id ) );
		}
				
		return clickElementList;		
	}

	
	protected void selectInDropDown( String id, String value, int byType )
	{
		selectInDropDown( id, value, byType, -1 );
	}
	
	protected void selectInDropDown( String id, String value, int byType, int elementIndex )
	{
		int count = 0;
		
		while( count < RETRY_LIMIT )
		{
			try
			{
				WebElement selectElement = getVisibleWebElement( id, byType, elementIndex );
								
				if( selectElement == null )
				{
					throw new StaleElementReferenceException( "The element has still not become visible" );					
				}
				
				String onchangeEvent	= selectElement.getAttribute( "onchange" );
				Select select 			= new Select( selectElement );
				select.selectByVisibleText( value );				
				break;
			}
			catch( StaleElementReferenceException  ex )
			{
				System.out.println( "StaleElementReferenceException" + id ); 
				waitForPageLoad();
				count++;	
				
				if( count == RETRY_LIMIT )
				{
					throw new NoSuchElementException( "The element has still not become visible or is not present. key: " + id );
				}
			}
		}
	}
	
	protected void selectInDropDownWithValue( String id, String value, int byType )
	{
		int count = 0;
		
		while( count < RETRY_LIMIT )
		{
			try
			{
				WebElement element		= getWebElement( id, byType );				
				String onchangeEvent	= element.getAttribute( "onchange" );
				Select select 			= new Select( element );
				select.selectByValue( value );				
				break;
			}
			catch( StaleElementReferenceException  ex )
			{
				System.out.println( "StaleElementReferenceException" + id ); 
				count++;	
				
				if( count == RETRY_LIMIT )
				{
					throw new NoSuchElementException( "The element has still not become visible or is not present. key: " + id );
				}
			}
		}
	}
	
	protected WebElement getWebElement( String id, int byType, int index )
	{
		if( index == -1 )
		{
			return getWebElement( id, byType );
		}
		else
		{
			List<WebElement> elementList	= getWebElementList( id, byType );
			return elementList.get( index );
		}
	}	
		
	protected int getWebElementIndex( String id, int byType, String value )
	{
		int index 						= -1;
		int elementIndex				= 0;
		
		List<WebElement> elementList	= getWebElementList( id, byType );
		
		for( WebElement element : elementList )
		{
			if( element.getText().trim().equals( value ) )
			{
				index	= elementIndex;
				break;
			}
			
			elementIndex++;
		}
		
		return index;
	}
	
	protected WebElement getWebElement( String id, int byType )
	{
		WebElement element 	= null;
		int count = 0;
		
		while( count <= 1 )
		{
			try
			{
				if( byType == BY_XPATH )
				{
					element = driver.findElement( By.xpath( id ) );
				}
				else if( byType == BY_NAME )
				{
					element = driver.findElement( By.name( id ) );
				}
				else if( byType == BY_CSS )
				{
					element = driver.findElement( By.cssSelector( id ) );
				}
				else if( byType == BY_CLASS )
				{
					element = driver.findElement( By.className( id ) );
				}
				else
				{
					element = driver.findElement( By.id( id ) );
				}
				
				break;
			}
			catch( StaleElementReferenceException ex )
			{
				ex.printStackTrace( System.out );
				count++;
				
				if( count > 1 )
				{
					throw ex;
				}
			}	
			catch( NoSuchElementException ex )
			{
				ex.printStackTrace( System.out );
				throw ex;					
			}
		}				
		return element;		
	}
	
	protected void selectCheckBox( String key, int byType )
	{
		selectCheckBox( key, byType, -1 );
	}
	
	protected void selectCheckBox( String key, int byType, int elementIndex )
	{
		WebElement element 	= getVisibleWebElement( key, byType, elementIndex );		
		if( element == null )
		{
			throw new NoSuchElementException( "The element has still not become visible or is not present. key: " + key );	
		}
		
		String onClickEvent	= element.getAttribute( "onclick" );
		
		if( !element.isSelected() )
		{
			JavascriptExecutor js 	= ( JavascriptExecutor )driver;
			js.executeScript( "arguments[0].focus();", element );
			element.click();	
		}					
	}
	
	protected void unSelectCheckBox( String key, int byType )
	{
		unSelectCheckBox( key, byType, -1 );
	}
	
	protected void unSelectCheckBox( String key, int byType, int elementIndex )
	{
		WebElement element 	= getVisibleWebElement( key, byType, elementIndex );		
		
		if( element == null )
		{
			throw new NoSuchElementException( "The element has still not become visible or is not present. key: " + key );	
		}		
		String onClickEvent	= element.getAttribute( "onclick" );		
		if( element.isSelected() )
		{
			JavascriptExecutor js 	= ( JavascriptExecutor )driver;
			js.executeScript( "arguments[0].focus();", element );
			element.click();	
		}		
	}
	
	protected void selectCheckBox( String id )
	{
		selectCheckBox( id, BY_ID );	
	}
	
	protected void selectRadioBox( String id )
	{
		selectCheckBox( id );
	}
	
	protected void click( String id, int byType )
	{
		click( id, byType, -1 );
	}
	
	protected void click( String id, int byType, int elementIndex )
	{
		int retryCount	= 0;
		do
		{
			try
			{
				List<WebElement> clickElementList 	= getWebElementList( id, byType, elementIndex );	
				int noOfElements					= clickElementList.size();
				
				if( noOfElements == 0 )
				{
					throw new NoSuchElementException( "Couldnt find element for " + id );
				}
				for( WebElement element : clickElementList )
				{
					if( element.isDisplayed() || noOfElements == 1 )
					{
						int retryForDisplay	= 0;
						
						while( retryForDisplay < 10 )
						{
							if( element.isDisplayed() )
							{
								break;
							}
							else
							{
								retryForDisplay++;
								waitFor( JS_LOAD_TIME ); 
							}
						}						
						final String onClickEvent	= element.getAttribute( "onclick" );	
						
						if( ( element.getTagName() != null && element.getTagName().equalsIgnoreCase( "a" ) ) )
						{
							JavascriptExecutor js = ( JavascriptExecutor )driver;
							js.executeScript( "arguments[0].click();", element );
						}
						else
						{
							element.click();
						}						
					}
				}
				break;
			}
			catch( StaleElementReferenceException ex )
			{
				//This is only a fallback option to avoid break in nightly runs but this should be fixed ASAP.
				System.out.print( "StaleElementReferenceException Found. Fix the test script "  );
				ex.printStackTrace( System.out );				
				retryCount++;
				if( retryCount > 1 )
				{
					throw ex;
				}
			}
		}
		while( retryCount <= 1 );
	}	
}
