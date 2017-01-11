package net.atos.practica.seleniumTest;


import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class TestVisualizacionDeResultados {

	private static WebDriver driver;
	private String baseUrl; 

	@BeforeClass
	public static void inicializarDriver() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\CEDEI\\Selenium\\chromedriver.exe");
        driver = new ChromeDriver();
	} 
	  
	@Before
	public void setUp() throws Exception {
		baseUrl ="http://localhost:8080/practica-war/login.xhtml";
	    driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
	    driver.get(baseUrl);
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
	}
	@Test
	public void test() throws InterruptedException {
		login("A111111", "PASS");
		ir();
		Thread.sleep(2000);
		driver.findElement(By.id("capacidades")).click();
		Thread.sleep(5000);
		driver.findElement(By.id("promociones")).click();
		Thread.sleep(5000);
		driver.findElement(By.id("proyectos")).click();
		Thread.sleep(5000);
		logout();
	}
	
	public void login(String user, String pass){
		driver.findElement(By.id("j_username")).sendKeys(user);
		driver.findElement(By.id("j_password")).sendKeys(pass);
		driver.findElement(By.id("login")).click();
	}
	
	 public void ir() throws InterruptedException{
		  WebElement element3 = driver.findElement(By.linkText("Visualización de Resultados"));
	      Actions action = new Actions(driver);
	      action.moveToElement(element3).perform();
	      Thread.sleep(5000);
	      action.moveToElement(element3);
	      action.click();
	      action.perform();
		}
	
	public void logout(){
		driver.findElement(By.id("menu:logout")).click();
	}

}
