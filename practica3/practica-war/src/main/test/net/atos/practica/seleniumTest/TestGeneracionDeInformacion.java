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

public class TestGeneracionDeInformacion {
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
			buscar();
		  }
		  
		  public void login(String user, String pass){
			 driver.findElement(By.id("j_username")).sendKeys(user);
			 driver.findElement(By.id("j_password")).sendKeys(pass);
			 driver.findElement(By.id("login")).click();
		  }

		  public void ir() throws InterruptedException{
			  WebElement element3 = driver.findElement(By.linkText("Generación de Información"));
		      Actions action = new Actions(driver);
		      action.moveToElement(element3).perform();
		      Thread.sleep(3000);
		      action.moveToElement(element3);
		      action.click();
		      action.perform();
			}
		  
		  public void buscar() throws InterruptedException{
			  driver.findElement(By.id("form:busca")).click();
			  Thread.sleep(3000);
			  driver.findElement(By.id("form:colaboradores:toggler")).click();
			  Thread.sleep(3000);
			    driver.findElement(By.xpath("(//div[@id='form:colaboradores:j_idt43']/ul/li[13]/div/div[2]/span)[2]")).click();
			    driver.findElement(By.xpath("(//div[@id='form:colaboradores:j_idt43']/ul/li[15]/div/div[2]/span)[2]")).click();
			    Thread.sleep(3000);
			    driver.findElement(By.id("form:buscarDatos_content")).click();
			    Thread.sleep(3000);
			    driver.findElement(By.xpath("//div[@id='form:promocion']/div[3]/span")).click();
			    Thread.sleep(3000);
			    driver.findElement(By.id("form:promocion_1")).click();
			    driver.findElement(By.id("form:busca")).click();
			    Thread.sleep(3000);
			    driver.findElement(By.xpath("//div[@id='form:promocion']/div[3]/span")).click();
			    driver.findElement(By.id("form:promocion_0")).click();
			    Thread.sleep(3000);
			    driver.findElement(By.xpath("//div[@id='form:gcm']/div[3]/span")).click();
			    driver.findElement(By.xpath("//div[@id='form:proyect']/div[3]/span")).click();
			    driver.findElement(By.xpath("//div[@id='form:proyect']/div[3]/span")).click();
			    driver.findElement(By.id("form:busca")).click();
			    Thread.sleep(3000);
			    driver.findElement(By.id("form:colaboradores:excel")).click();
			    driver.findElement(By.id("menu:logout")).click();
			    Thread.sleep(3000);
		  }
}
