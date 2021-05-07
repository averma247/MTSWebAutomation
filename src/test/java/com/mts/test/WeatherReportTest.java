package com.mts.test;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.framework.core.BaseTest;
import com.google.gson.Gson;
import com.mts.jsons.TestJSONData;
import com.mts.pages.WeatherHomePage;
import com.mts.reporter.Reporter;

public class WeatherReportTest extends BaseTest {
	private static final Logger logger = LogManager.getLogger(WeatherReportTest.class);

	WeatherHomePage weatherhomepage;
	
	@BeforeClass
	public void setup(){
		logger.debug("Inside before class method: "+this.getClass().getSimpleName());
		weatherhomepage = new WeatherHomePage(getDriver(),configProp.getProperty("uiWait"));
	}

	@Test(enabled = false)
	public void verifyTempratureSearchOnGUIPhase1() throws InterruptedException{

		String cityname="Delhi";
		Integer currentTempInt=weatherhomepage.searchCityGetTemprature(cityname);	
		Assert.assertNotNull(currentTempInt, "No result found in the system.");
		Reporter.reportStep("Current Temprature of City on UI: "+cityname+ " is "+ currentTempInt +"°"+ TempratureUnit);


	}

	@Test(dataProvider = "citydata",enabled=true)
	public void verifyTempratureSearchOnGUIPhase1(TestJSONData data) throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		String[]Cities=data.getlistofCities();
		int varience= data.getVariance();
		for(String city: Cities){
			Reporter.reportStep("Checking the temprature for city: "+ city);
			Integer currentTempInt=weatherhomepage.searchCityGetTemprature(city);	
			softAssert.assertNotNull(currentTempInt, "No result found in the system.");
			Reporter.reportStep("Current Temprature of City on UI: "+city+ " is "+ currentTempInt +"&#176"+ TempratureUnit,1);
		}
		Reporter.reportStep("Varience Value: "+ varience);
		softAssert.assertAll();

	}

	
	
	@DataProvider(name="citydata")
	public Object[][] jsonDataProvider() throws Exception{		

		Gson gson = new Gson();
		Reader reader = Files.newBufferedReader(Paths.get(BaseTest.configProp.getProperty("testdatafilePath")));
		TestJSONData data= gson.fromJson(reader, TestJSONData.class);

		Object[][] obj = new Object[1][1];
		obj[0][0]=data;
		reader.close();
		return obj;
	}

}
