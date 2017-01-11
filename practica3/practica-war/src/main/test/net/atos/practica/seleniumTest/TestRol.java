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

public class TestRol {
	
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
		irA("Roles");
		Thread.sleep(2000);
		busca("");
		Thread.sleep(2000);
		nuevo("Rol Nuevo", "Este rol ");
		Thread.sleep(2000);
		busca("Rol Nuevo");
		Thread.sleep(2000);
		modificar(" 2", " copia");
		Thread.sleep(2000);
		borrar();
		Thread.sleep(2000);
		busca("");
		Thread.sleep(2000);
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
	
	public void busca(String rol) throws InterruptedException{
		driver.findElement(By.id("roles:rol")).sendKeys(rol);
		driver.findElement(By.id("roles:buscar")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("roles:rol")).clear();
	}

	public void nuevo(String rol, String description) throws InterruptedException{
		driver.findElement(By.id("roles:nuevo")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("modifica:nameRol")).sendKeys(rol);
		Thread.sleep(2000);
		driver.findElement(By.id("modifica:descrip")).sendKeys(description);
		Thread.sleep(2000);
		driver.findElement(By.xpath(".//div[@id='modifica:baja']/div/span[text()='Si']")).click();
		Thread.sleep(2000);
	    driver.findElement(By.id("modifica:guardarNuevo")).click();
	    
	}
	
	public void modificar(String nuevoRol, String nuevaDes) throws InterruptedException{
		driver.findElement(By.xpath(".//tbody[@id='roles:listaResultados_data']/tr[*]/td[text()='Rol Nuevo']")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("modifica:nameRol")).click();
		driver.findElement(By.id("modifica:nameRol")).sendKeys(nuevoRol);
		Thread.sleep(2000);
		driver.findElement(By.id("modifica:descrip")).click();
		driver.findElement(By.id("modifica:descrip")).sendKeys(nuevaDes);
		Thread.sleep(2000);
		driver.findElement(By.id("modifica:modify")).click();
		Thread.sleep(2000);
	}
	
	public void borrar() throws InterruptedException{
		driver.findElement(By.xpath(".//tbody[@id='roles:listaResultados_data']/tr[*]/td[text()='Rol Nuevo 2']")).click();
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
