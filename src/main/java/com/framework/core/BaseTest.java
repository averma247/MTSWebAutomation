package com.framework.core;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import com.google.gson.Gson;
import com.mts.jsons.TestJSONData;
import com.mts.pages.WeatherHomePage;
import com.mts.reporter.ExtentManager;
import com.mts.reporter.Reporter;

import io.github.bonigarcia.wdm.WebDriverManager;



public class BaseTest	{

	public static final Properties configProp = new Properties();
	private static WebDriver driver;
	public WebDriverWait wait;
	protected static String TempratureUnit;
	WeatherHomePage weatherhomepage;

	@BeforeSuite
	public void init() throws IOException {

		configProp.load(BaseTest.class.getClassLoader().getResourceAsStream("config.properties"));

	}

	@BeforeTest
	public void openApplication() {	
		//if chrome is selected.
		setupChrome();	
		driver.manage().timeouts().implicitlyWait(Long.parseLong(configProp.getProperty("uiWait")), TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().setScriptTimeout(120, TimeUnit.SECONDS);
		driver.get(configProp.getProperty("weatherurl"));
		wait = new WebDriverWait(driver, Long.parseLong(configProp.getProperty("uiWait")));
		TempratureUnit=configProp.getProperty("TempratureUnit");
		setTempratureUnit();

	}
	@BeforeMethod
	public void beforeMethodSetup(Method m){

		Reporter.startTestCase(m.getName());

	}
	private void setupChrome() {
		System.setProperty("webdriver.chrome.silentOutput", "true");
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-web-security");
		options.addArguments("--allow-running-insecure-content");
		options.addArguments("--autoplay-policy=no-user-gesture-required");
		//options.addArguments("--user-data-dir="+configProp.getProperty("browserProfile"));
		//options.addArguments("--no-startup-window");
		options.addArguments("--disable-session-crashed-bubble");
		options.addArguments("--disable-extensions");
		options.addArguments("--disable-application-cache");
		/*try {
			Files.deleteIfExists(Paths.get(configProp.getProperty("browserProfile")).resolve("Default").resolve("Preferences"));
		} catch (IOException ioException) {

		}*/
		driver = new ChromeDriver(options);
	}


	@AfterMethod
	public void cleanup(ITestResult result) {
		if (result.getThrowable() != null)
			Reporter.reportStep("Failure cause " + result.getThrowable().getMessage(), result.getStatus());
		Reporter.reportStep("Test Complete", result.getStatus());
		driver.close();
		driver.quit();
		ExtentManager.getInstance().flush();
	}

	public static void setTempratureUnit(){
		final String tempratureUnitMenu="//*[local-name()='svg'][@name='triangle-down'][contains(@class,'LanguageSelector')]";
		final String tempratureUnit="//button[contains(@class,'UnitSelectorButton')]/span[contains(text(),'%s')]";
		waitForPageLoad();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.findElement(By.xpath(tempratureUnitMenu)).click();
		waitForPageLoad();
		String tempratureUnitXpath_ = String.format(tempratureUnit, TempratureUnit);
		driver.findElement(By.xpath(tempratureUnitXpath_)).click();


	}

	public static void waitForPageLoad(){		
		ExpectedCondition<Boolean> pageLoadCondition = new
				ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
			}
		};
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(pageLoadCondition);


	}

	public WebDriver getDriver(){
		return driver;
	}
}
