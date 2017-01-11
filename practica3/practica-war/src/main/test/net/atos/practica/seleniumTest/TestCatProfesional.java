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

public class TestCatProfesional {

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
		irA("Cat. Profesional");
		Thread.sleep(2000);
		buscar("");
		Thread.sleep(2000);
		nueva("Ingeniero");
		buscar("Ingeniero");
		Thread.sleep(2000);
		modifica(" mecánico");
		Thread.sleep(2000);
		buscar("Ingeniero mecánico");
		borrar();
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
		Thread.sleep(3000);
		WebElement subElement = driver.findElement(By.linkText(nombre));
		action.moveToElement(subElement);
		action.click();
		action.perform();
	}
	
	public void buscar(String categoria){
		driver.findElement(By.id("filtros:filtroNombre")).clear();
		driver.findElement(By.id("filtros:filtroNombre")).click();
		driver.findElement(By.id("filtros:filtroNombre")).sendKeys(categoria);
	    driver.findElement(By.id("filtros:buscar")).click();
	}
	
	public void nueva(String categoria) throws InterruptedException{
		driver.findElement(By.id("filtros:nuevo")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("detalleForm:nombre")).sendKeys(categoria);
		Thread.sleep(2000);
		driver.findElement(By.id("detalleForm:guardarNuevo")).click();
		Thread.sleep(2000);
	}
	
	public void modifica(String categoria) throws InterruptedException{
		driver.findElement(By.xpath(".//*[@id='filtros:listaResultados_data']/tr[*]/td[text()='Ingeniero']")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("detalleForm:nombre")).click();
		driver.findElement(By.id("detalleForm:nombre")).sendKeys(categoria);
		Thread.sleep(2000);
		driver.findElement(By.id("detalleForm:modificar")).click();
		Thread.sleep(2000);
	}
	
	public void borrar() throws InterruptedException{
		driver.findElement(By.xpath(".//*[@id='filtros:listaResultados_data']/tr[*]/td[text()='Ingeniero mecánico']")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("detalleForm:eliminar")).click();
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
