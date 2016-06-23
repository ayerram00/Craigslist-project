package com.sqa.ay;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.firefox.*;
import org.testng.annotations.*;

public class BasicTest {
	private static String baseURL = "http://sf.craigslist.com";
	private static WebDriver driver;

	public static String getBaseURL() {
		return baseURL;
	}

	public static WebDriver getDriver() {
		return driver;
	}

	@BeforeClass
	public static void setupChrome() {
		System.setProperty("webdriver.chrome.driver", "drivers/chromedriver");
		driver = new ChromeDriver();
		driver.manage();
		driver.get(baseURL);
	}

	@BeforeClass(enabled = false)
	public static void setupFirefox() {
		driver = new FirefoxDriver();
		driver.manage();
		driver.get(baseURL);
	}

	@AfterClass()
	public static void teardown() {
		driver.quit();
	}
	// @Test(dataProvider = "dp")
	// public void basicTest() {
	// }
	//
	// @DataProvider
	// public Object[][] dp() {
	// return new Object[][] {
	// new Object[] { 1, "a" },
	// new Object[] { 2, "b" },
	// };
	// }

	public BasicTest(String string) {
		BasicTest.baseURL = baseURL;
	}

}
