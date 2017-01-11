package net.atos.practica.seleniumTest;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class TestNavegacionXMenu {
	// Comentario
	private static Logger log = org.apache.log4j.Logger
			.getLogger(TestCrudColaborador.class);
	private static WebDriver driver;
	private String baseUrl = "http://localhost:8080/practica-war/login.xhtml";

	private String usuario = "A111111";
	private String pass = "PASS";

	@BeforeClass
	public static void inicializarDriver() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver",
				"C:\\CEDEI\\Selenium\\chromedriver.exe");
		driver = new ChromeDriver();
	}

	@Before
	public void setUp() throws Exception {
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.get(baseUrl);
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

	}

	@Test
	public void test() throws InterruptedException {

		login(usuario, pass);
		navegacion();
		// modPass(pass, newPass);
		// modPass(newPass, pass);
		//modDatos(nombre, apellido1, apellido2, nif, fechaNac, telefono, email);
		//modDatos("ADMIN", "ADMIN", "ADMIN", "43344942Q",
		//		new Date(1990, 10, 05), "922356598", "yo@gmail.com");
		// logOut();
	}

	public void login(String user, String pass) throws InterruptedException {
		try {
			WebElement element = driver.findElement(By.id("j_username"));
			element.sendKeys(user);
			WebElement element2 = driver.findElement(By.id("j_password"));
			element2.sendKeys(pass);
			WebElement button = driver.findElement(By.id("login"));
			button.click();
			Thread.sleep(1000);
			log.info("FINALIZADO LOGIN: -------> USUARIO: " + usuario
					+ " ; PASS: ");

		} catch (Exception e) {
			log.info("ERROR: LOGIN INCORRECTO: -------> USUARIO: " + user
					+ " ; PASS: ");

		}
	}

	// Comprueba Navegacion por menus
	public void navegacion() throws InterruptedException {
		mantenimientoOpcion("Colaborador");
	    Thread.sleep(5000);
	    mantenimientoOpcion("Proyectos");
	    Thread.sleep(5000);
	    mantenimientoOpcion("Promoción");
	    Thread.sleep(5000);
	    mantenimientoOpcion("Titulación");
	    Thread.sleep(5000);
	    mantenimientoOpcion("Capacidad");
	    Thread.sleep(5000);
	    mantenimientoOpcion("Roles");
	    Thread.sleep(5000);
	    mantenimientoOpcion("Estatus");
	    Thread.sleep(5000);
	    mantenimientoOpcion("Cat. Profesional");
	    Thread.sleep(2000);
	    driver.findElement(By.xpath("//div[@id='menuAdmin:menuPrincipal']/ul/li[2]/a/span")).click();
	    Thread.sleep(2000);
	    driver.findElement(By.xpath("//div[@id='menuAdmin:menuPrincipal']/ul/li[3]/a/span")).click();
	    Thread.sleep(2000);
	    driver.findElement(By.xpath("//div[@id='menuAdmin:menuPrincipal']/ul/li[4]/a/span")).click();
	    Thread.sleep(2000);
	    driver.findElement(By.xpath("//div[@id='menuAdmin:menuPrincipal']/ul/li[5]/a/span")).click();
	    Thread.sleep(2000);
	    
	    logOut();
	}
	public void mantenimientoOpcion(String nombre) throws InterruptedException{
		WebElement element3 = driver.findElement(By.linkText("Mantenimiento"));
		Actions action = new Actions(driver);
		action.moveToElement(element3).perform();
		Thread.sleep(3000);
		WebElement subElement = driver.findElement(By.linkText(nombre));
		action.moveToElement(subElement);
		action.click();
		action.perform();
	}

	public void logOut() throws InterruptedException {
		Thread.sleep(3000);
		WebElement buttonLogOut = driver.findElement(By.id("menu:logout"));
		buttonLogOut.click();
	}

	@After
	public void tearDown() throws Exception {
		// driver.quit();
	}

}
