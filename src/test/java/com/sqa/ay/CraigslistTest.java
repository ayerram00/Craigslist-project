
package com.sqa.ay;

import java.io.*;

import org.apache.commons.io.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.testng.*;
import org.testng.annotations.*;

public class CraigslistTest extends BasicTest {

	@DataProvider
	public static Object[][] getData() {
		return new Object[][] { { "cobol automation player", 0, 0 }, { "java selenium", 5, 50 },
				{ "java junior", 5, 50 }, { "QA engineer", 200, 300 }, { "test developer", 5, 50 },
				{ "automation", 200, 300 }, { "selenium", 5, 50 } };
	}

	public CraigslistTest() {
		super("http://sf.craigslist.com");
	}

	@Test(dataProvider = "getData")
	public void craigslistTest(String keywords, int minExpectedResults, int maxExpectedResults)
			throws InterruptedException {
		getDriver().get(getBaseURL());
		// Get page element which is located
		WebElement searchField = getDriver().findElement(By.id("query"));
		// clear content inside search field
		searchField.clear();
		// Send input text into search field
		searchField.sendKeys(keywords);
		// Click on submit button
		// No submit present so press enter key
		searchField.sendKeys(Keys.RETURN);
		// OR Submit form
		// searchField.submit();
		// Select the option to search in "jobs"
		WebElement searchArea = getDriver().findElement(By.id("catAbb"));
		Select selectCat = new Select(searchArea);
		// System.out.println("Options:" + selectCat.getOptions());
		selectCat.selectByVisibleText("jobs");
		String results = getDriver().findElement(By.cssSelector("span.button.pagenum")).getText();
		// System.out.println("Results:" + results);
		int totalResults = 0;
		if (results.equalsIgnoreCase("no results")) {
			// System.out.println("There are no results available for " +
			// keywords);
		} else if (results.split("of").length > 1) {
			totalResults = Integer.parseInt(results.split("of")[1].trim());
			// System.out.println("Total Results:" + totalResults);
		} else {
			totalResults = Integer.parseInt(results.split("to")[1].trim());
			// System.out.println("Total Results:" + totalResults);
		}

		File src = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(src, new File("screenshots/" + keywords + ".png"));
		} catch (IOException e) {

		}
		Assert.assertTrue(totalResults >= minExpectedResults && totalResults <= maxExpectedResults,
				"Number of results(" + results + ") is not within the range of expected (" + minExpectedResults + " - "
						+ maxExpectedResults + ")");
	}

	@BeforeMethod
	public void setupTest() {
		getDriver().manage().deleteAllCookies();
		getDriver().get(getBaseURL());
	}

}
