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

public class TestCrudColaborador {
	// Comentario
	private static Logger log = org.apache.log4j.Logger
			.getLogger(TestCrudColaborador.class);
	private static WebDriver driver;
	private String baseUrl = "http://localhost:8080/practica-war/login.xhtml";
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	private String usuario = "A111111";
	private String pass = "PASS";
	private String newPass = "PASSS";
	private String nombre = "DAVID";
	private String apellido1 = "PEREZ";
	private String apellido2 = "SUAREZ";
	private String nif = "78379837T";
	private Date fechaNac = new Date(1980, 02, 11);
	private String telefono = "699699699";
	private String email = "pepito@hotmail.com";

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
		// modPass(pass, newPass);
		// modPass(newPass, pass);
		modDatos(nombre, apellido1, apellido2, nif, fechaNac, telefono, email);
		modDatos("ADMIN", "ADMIN", "ADMIN", "43344942Q",
				new Date(1990, 10, 05), "922356598", "yo@gmail.com");
		logOut();
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

	// Comprueba boton cancelar modifica pass
	public void modPass(String passOld, String passNueva)
			throws InterruptedException {
		try {
			Thread.sleep(2000);
			// Pulsa boton modicar Contraseña
			WebElement botonModPass = driver.findElement(By
					.id("principal:modiPass"));
			botonModPass.click();
			Thread.sleep(3000);
			// Boton cancelar de modificar pass
			WebElement botonCancelPass = driver.findElement(By
					.id("modificarPass:cancelaPass"));
			botonCancelPass.click();
			Thread.sleep(2000);
			// Pulsa Modificar contraseña otra vez
			botonModPass.click();
			WebElement passActual = driver.findElement(By
					.id("modificarPass:oldPass"));
			passActual.sendKeys(passOld);
			WebElement passNueva1 = driver.findElement(By
					.id("modificarPass:newPass1"));
			passNueva1.sendKeys(passNueva);
			WebElement passNueva2 = driver.findElement(By
					.id("modificarPass:newPass2"));
			passNueva2.sendKeys(passNueva);
			WebElement botonGuardarPass = driver.findElement(By
					.id("modificarPass:modificaPass"));
			Thread.sleep(2000);
			botonGuardarPass.click();

			Thread.sleep(2000);
			log.info("FINALIZADO MODIFICAR PASS: -------> USUARIO: " + usuario
					+ " ; PASS: ");

		} catch (Exception e) {
			log.info("ERROR: MODIFICAR PASS: -------> USUARIO: " + usuario
					+ " ; PASS: ");
		}
	}

	public void modDatos(String nombre, String apellido1, String apellido2,
			String nif, Date fechaNac, String telefono, String email)
			throws InterruptedException {
		try{
			Thread.sleep(2000);
			// Pulsa boton modicar Datos
			WebElement botonModDatos = driver.findElement(By
					.id("principal:actualizarDatos"));
			botonModDatos.click();
			Thread.sleep(3000);
			// Prueba Boton cancelar de modificar Datos
			WebElement botonCancelDatos = driver.findElement(By
					.id("modificar:cancelaModifica"));
			// Vuelve a pulsar modificar los datos tras probar cancelar
			botonCancelDatos.click();
			Thread.sleep(3000);
			// Vuelve a lan
			botonModDatos.click();
			Thread.sleep(3000);
			WebElement nombreWe = driver.findElement(By.id("modificar:nombre"));
			WebElement apellido1Wb = driver.findElement(By
					.id("modificar:primerApellido"));

			WebElement apellido2Wb = driver.findElement(By
					.id("modificar:segundoApellido"));

			WebElement nifWb = driver.findElement(By.id("modificar:nif"));

			WebElement fechaNacWb = driver.findElement(By
					.id("modificar:fechaNacimiento_input"));
			WebElement telefonoWb = driver.findElement(By
					.id("modificar:telefono"));
			WebElement emailWb = driver.findElement(By.id("modificar:email"));
			WebElement botonGuardarWb = driver.findElement(By
					.id("modificar:modificaDatos"));

			nombreWe.clear();
			nombreWe.sendKeys(nombre);
			apellido1Wb.clear();
			apellido1Wb.sendKeys(apellido1);
			apellido2Wb.clear();
			apellido2Wb.sendKeys(apellido2);
			nifWb.clear();
			nifWb.sendKeys(nif);
			Thread.sleep(1000);
			fechaNacWb.clear();
			fechaNacWb.sendKeys(fechaNac.getDate() + "/" + fechaNac.getMonth()
					+ "/" + fechaNac.getYear());

			Thread.sleep(1000);
			fechaNacWb.isDisplayed();
			telefonoWb.clear();
			telefonoWb.sendKeys(telefono);
			emailWb.clear();
			emailWb.sendKeys(email);

			Thread.sleep(2000);
			// vuelve a modificar datos
			botonGuardarWb.click();
			Thread.sleep(3000);
			log.info("FINALIZADO MODIFICAR DATOS: -------> USUARIO: " + usuario
					+ " ; PASS: ");
		} catch (Exception e) {
			log.info("ERROR: MODIFICAR DATOS: -------> USUARIO: " + usuario
					+ " ; PASS: ");
		}
		

	}

	public void logOut() throws InterruptedException {
		Thread.sleep(2000);
		WebElement buttonLogOut = driver.findElement(By.id("menu:logout"));
		buttonLogOut.click();
	}

	@After
	public void tearDown() throws Exception {
		// driver.quit();
	}

}
