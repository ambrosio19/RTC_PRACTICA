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

public class TestAlertas {
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
		 
		driver.findElement(By.id("form:buscar")).click();
		driver.findElement(By.id("form:nuevo")).click();
		driver.findElement(By.id("modificar:nombreAlerta")).clear();
		driver.findElement(By.id("modificar:nombreAlerta")).sendKeys("Alarma8");
		driver.findElement(By.id("modificar:descp")).click();
		driver.findElement(By.id("modificar:descp")).clear();
		driver.findElement(By.id("modificar:descp")).sendKeys("aaaa");
		driver.findElement(By.id("modificar:aviso_label")).click();
		driver.findElement(By.xpath(".//*[@id='modificar:aviso_5']")).click();
		driver.findElement(By.id("modificar:fechaFin_input")).sendKeys("11" + "/01" + "/17");
		driver.findElement(By.id("modificar:guardar")).click();
		Thread.sleep(3000);
		driver.findElement(By.id("form:alertas")).clear();
	    driver.findElement(By.id("form:alertas")).sendKeys("Alarma8");
	    driver.findElement(By.id("form:buscar")).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//tbody[@id='form:listaResultados_data']/tr/td")).click();
		Thread.sleep(2000);
	    driver.findElement(By.id("modificar:borrar")).click();
	    Thread.sleep(3000);
		driver.findElement(By.id("menu:logout")).click();
	}
	  
	  public void login(String user, String pass){
		 driver.findElement(By.id("j_username")).sendKeys(user);
		 driver.findElement(By.id("j_password")).sendKeys(pass);
		 driver.findElement(By.id("login")).click();
	  }

	  public void ir() throws InterruptedException{
		  WebElement element3 = driver.findElement(By.linkText("Alertas"));
	      Actions action = new Actions(driver);
	      action.moveToElement(element3).perform();
	      Thread.sleep(3000);
	      action.moveToElement(element3);
	      action.click();
	      action.perform();
		}

}
