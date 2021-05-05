package com.mts.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static reporter.Utils.*;

public class WeatherHomePage extends BasePage{

	private static final String locationSearchID = "LocationSearch_input";
	private static final String currentTempratureXpath="//span[contains(@class,'CurrentConditions--tempValue')]";
	private static final String locationsearchResultXpath="//button[@id='LocationSearch_listbox-0']";
	private static final String noResultFoundMsgXpath="//div[contains(text(),'No results found')]";

	WebDriver driver;
	WebDriver wait;

	public WeatherHomePage(WebDriver driver, String uiwait) {
		super(driver, uiwait);
		//this.driver= driver;		
	}

	/*public void searchCity(String cityname){

		ExpectedConditions.visibilityOfElementLocated(By.id(locationSearchID));
		driver.findElement(By.id(locationSearchID)).click();
		driver.findElement(By.id(locationSearchID)).sendKeys(cityname);
		driver.findElement(By.id(locationSearchID)).sendKeys(Keys.ENTER);

	}*/

	public void searchCity(String cityname){
		enterTextInTextBox(By.id(locationSearchID), cityname);
		clickOnButton(By.id(locationsearchResultXpath));
	}


	public Integer searchCityGetTemprature(String cityname) throws InterruptedException{
		
		waitForPageLoad();
		enterTextInTextBox(By.id(locationSearchID), cityname);
		waitForPageLoad();
		//check for no results.
		boolean resultstatus=checkForNoResults();
		if(!resultstatus){
			clickOnButton(By.xpath(locationsearchResultXpath));
			waitForPageLoad();
			String currenTemprature=getTextAt(By.xpath(currentTempratureXpath));		
			return getCurrentTempratureAsInt(currenTemprature);		

		}
		else
			return null;

	}

	public boolean checkForNoResults() throws InterruptedException{
		return isElementPresentWithImplicit(By.xpath(noResultFoundMsgXpath));		
	}

}
