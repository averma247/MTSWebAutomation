package weatherreporttest;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import jsons.TestJSONData;
import reporter.Reporter;

public class WeatherReportTest extends TestBase {


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


}
