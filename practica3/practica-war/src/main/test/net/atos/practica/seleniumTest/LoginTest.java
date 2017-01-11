package net.atos.practica.seleniumTest;



import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class LoginTest {
 //Comentario
	  private static WebDriver driver;
	  private String baseUrl ="http://localhost:8080/practica-war/login.xhtml";

	  @BeforeClass
      public static void inicializarDriver() throws InterruptedException {
          System.setProperty("webdriver.chrome.driver", "C:\\CEDEI\\Selenium\\chromedriver.exe");
          driver = new ChromeDriver();
      } 
	  
	  @Before
	  public void setUp() throws Exception {
	    driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
	    driver.get(baseUrl);
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

 
	  }

	  @Test
	  public void pruebaLogin() throws InterruptedException{
		  login("A111111", "sss");
		  login("A111111", "PASS");
		  Thread.sleep(2000);
		  logout();
	  }
	  
	  public void login(String user, String pass) throws InterruptedException{
		  Thread.sleep(3000);
		  WebElement element = driver.findElement(By.id("j_username"));
		  element.clear();
		  element.sendKeys(user);
		  WebElement element2 = driver.findElement(By.id("j_password"));
		  element2.clear();
		  element2.sendKeys(pass);
		  WebElement button = driver.findElement(By.id("login"));
		  button.click();
	  }
	  public void logout () throws InterruptedException{
		  Thread.sleep(3000);
		  WebElement buttonLogOut = driver.findElement(By.id("menu:logout"));
		  buttonLogOut.click();
	  }

	  @After
	  public void tearDown() throws Exception {
	    driver.quit();
	  }
	
}
