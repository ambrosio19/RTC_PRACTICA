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
import org.openqa.selenium.interactions.Actions;

public class TestCapacidad {
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
		irA("Capacidad");
		busca("JEE");
		nueva("Prueba");
		Thread.sleep(2000);
		modifica(" cap");
		Thread.sleep(2000);
		borrar("Prueba cap");
		busca("");
		logout();
		
	}
	public void login(String user, String pass){
		 driver.findElement(By.id("j_username")).sendKeys(user);
		 driver.findElement(By.id("j_password")).sendKeys(pass);
		 driver.findElement(By.id("login")).click();
	  }
	
	public void irA(String nombre) throws InterruptedException{
		WebElement element3 = driver.findElement(By.linkText("Mantenimiento"));
        Actions action = new Actions(driver);
        action.moveToElement(element3).perform();
        Thread.sleep(2000);
        WebElement subElement = driver.findElement(By.linkText(nombre));
        action.moveToElement(subElement);
        action.click();
        action.perform();
	}
	
	public void busca(String capacidad) throws InterruptedException{
        driver.findElement(By.id("form:busca")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("form:capacidad")).sendKeys(capacidad);
        driver.findElement(By.id("form:busca")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("form:capacidad")).clear();

	}
	
	public void nueva(String nombre) throws InterruptedException{
		driver.findElement(By.id("form:nuevo")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("modifica:nameCapac")).sendKeys(nombre);
		Thread.sleep(2000);
		driver.findElement(By.id("modifica:guardar")).click();
		Thread.sleep(2000);
	}
	
	public void modifica(String nombre) throws InterruptedException{
		driver.findElement(By.xpath(".//*[@id='form:listaResultados_data']/tr[*]/td[text()='Prueba']")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("modifica:nameCapac")).click();
		driver.findElement(By.id("modifica:nameCapac")).sendKeys(nombre);
		Thread.sleep(2000);
		driver.findElement(By.id("modifica:modify")).click();
		Thread.sleep(2000);
	}
	
	public void borrar(String nombreCap) throws InterruptedException{
		busca(nombreCap);
		driver.findElement(By.xpath(".//*[@id='form:listaResultados_data']/tr[*]/td[text()='Prueba cap']")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("modifica:borrar")).click();
		Thread.sleep(2000);
	}
	
	public void logout(){
		driver.findElement(By.id("menu:logout")).click();
	}

	@After
	  public void tearDown() throws Exception {
	    driver.quit();
	  }
}
