import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.saucelabs.saucerest.SauceREST;

public class HelloTest {

	String username = System.getenv("SAUCE_USERNAME");
	String accessKey = System.getenv("SAUCE_ACCESS_KEY");
	RemoteWebDriver driver;

	SauceREST api = new SauceREST(username, accessKey);
	Boolean passed;
	
	@Test
	public void openTrainingTestPage() throws MalformedURLException
	{	
		String sauceURL = "https://SAUCE_USERNAME:SAUCE_ACCESS_KEY@ondemand.saucelabs.com:443/wd/hub"
				.replace("SAUCE_USERNAME", username)
				.replace("SAUCE_ACCESS_KEY", accessKey);
		
		URL url = new URL(sauceURL);
		
		DesiredCapabilities capabilities= new DesiredCapabilities();
		capabilities.setCapability("platform", "Windows 10");
		capabilities.setCapability("browserName", "Chrome");
		capabilities.setCapability("version", "latest");
		capabilities.setCapability("name", "Hello Test");
		
		driver = new RemoteWebDriver(url, capabilities);
		driver.get("https://saucelabs-sample-test-frameworks.github.io/training-test-page/");
		
		String title = driver.getTitle();
	
		try {
			assertEquals(title, "I am a page title - Sauce Labs");		
			passed = true;
		}
		catch (AssertionError e)
		{
			e.printStackTrace();
			passed = false;
			throw(e);
		}
	}
	
	@After
	public void tearDown()
	{

        driver.executeScript("sauce:job-result=" + passed);
//        driver.executeScript("sauce:break");


//      if (passed)
//		{
//
//			api.jobPassed(driver.getSessionId().toString());
//		}
//		else
//		{
//			api.jobFailed(driver.getSessionId().toString()); 
//		}

        driver.quit();
	}
}
