package stepdefs.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Hooks {

  private static WebDriver driver;

  @Before
  public void setup() {
    if (driver == null) {
      ChromeOptions options = new ChromeOptions();
      options.addArguments("--disable-search-engine-choice-screen");
      driver = new ChromeDriver(options);
      driver.get("https://uk.gymshark.com");
      driver.manage().addCookie(new Cookie("gs-headless-locale-production", "en-GB"));
      driver.manage().addCookie(new Cookie("OptanonAlertBoxClosed", "2024-10-03"));
      driver.navigate().refresh();
    }
  }

  @After
  public void teardown() {
    if (driver != null) {
      driver.quit();
      driver = null;
    }
  }

  public static WebDriver getDriver() {
    return driver;
  }

}
