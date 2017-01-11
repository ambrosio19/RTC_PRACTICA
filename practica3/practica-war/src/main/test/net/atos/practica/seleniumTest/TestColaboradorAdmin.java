package net.atos.practica.seleniumTest;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class TestColaboradorAdmin {
  private WebDriver driver;
  private String baseUrl;

  @Before
  public void setUp() throws Exception {
    System.setProperty("webdriver.chrome.driver", "C:\\CEDEI\\Selenium\\chromedriver.exe");
    driver = new ChromeDriver();
    baseUrl = "http://localhost:8080";
    driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
  }

  @Test
  public void testNuevoColaborador() throws Exception {
    driver.get(baseUrl + "/practica-war/login.xhtml");
    Thread.sleep(2000);
    login("A111111", "PASS");
    irA("Colaborador");
    buscar("", "");
    Thread.sleep(2000);
    nuevo("A000026", "Ivanda2", "Delgadoo", "Alonsoo", "45708180G", "647845123", "11" + "/12" + "/16", 
    		"10" + "/12" + "/16", "20" + "/07" + "/92",	"prueba@prueba.com", "PROYECTO 1111", "INGENIERIA INFORMATICA",
    		"CONTRATADO_CEDEI", "6000", "PROMOCION 9", "JEE", "5", "TÉNICO");
    Thread.sleep(2000);
    buscar("A000026", "");
    modificar("A000026", "IvandaModif", "BENEFICIARIO_CEDEI", "COBOL");
    Thread.sleep(2000);
    logout();
  }
  
  public void login(String user, String pass){
	  WebElement element = driver.findElement(By.id("j_username"));
	  element.sendKeys(user);
	  WebElement element2 = driver.findElement(By.id("j_password"));
	  element2.sendKeys(pass);
	  WebElement button = driver.findElement(By.id("login"));
	  button.click(); 
  }
  
  public void logout(){
		driver.findElement(By.id("menu:logout")).click();
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
  
  public void buscar(String codigo, String nombre) throws InterruptedException{
	  driver.findElement(By.id("filtros:codigoFiltro")).clear();
	  driver.findElement(By.id("filtros:codigoFiltro")).sendKeys(codigo);
	  driver.findElement(By.id("filtros:nombreFiltro")).clear();
	  driver.findElement(By.id("filtros:nombreFiltro")).sendKeys(nombre);
	  
	  driver.findElement(By.id("filtros:buscar")).click();
      Thread.sleep(2000);
  }
  
  public void nuevo(String codigo, String nombre, String primerApellido, String segundoApellido, String nif,
		  String telefono, String fechaBaja, String fechaAlta, String fechaNacimiento, String email,
		  String proyecto, String titulacion, String estatus, String salario, String promocion,
		  String capacidad, String nivelGCM, String categoriaProfesional) throws InterruptedException{
	  
	  driver.findElement(By.id("filtros:nuevo")).click();
	  Thread.sleep(2000);
	  driver.findElement(By.id("detalleForm:codigo")).clear();
	  driver.findElement(By.id("detalleForm:codigo")).sendKeys(codigo);
	  driver.findElement(By.id("detalleForm:nombre")).clear();
	  driver.findElement(By.id("detalleForm:nombre")).sendKeys(nombre);
	  driver.findElement(By.id("detalleForm:primerApellido")).clear();
	  driver.findElement(By.id("detalleForm:primerApellido")).sendKeys(primerApellido);
	  driver.findElement(By.id("detalleForm:segundoApellido")).clear();
	  driver.findElement(By.id("detalleForm:segundoApellido")).sendKeys(segundoApellido);
	  driver.findElement(By.id("detalleForm:nif")).clear();
	  driver.findElement(By.id("detalleForm:nif")).sendKeys(nif);
	  driver.findElement(By.id("detalleForm:telefono")).clear();
	  driver.findElement(By.id("detalleForm:telefono")).sendKeys(telefono);
	  driver.findElement(By.id("detalleForm:fechaBaja_input")).sendKeys(fechaBaja);
	  driver.findElement(By.id("detalleForm:fechaAlta_input")).sendKeys(fechaAlta);
	  driver.findElement(By.id("detalleForm:fechaNacimiento_input")).sendKeys(fechaNacimiento);
	  driver.findElement(By.id("detalleForm:email")).clear();
	  driver.findElement(By.id("detalleForm:email")).sendKeys(email);
	  driver.findElement(By.xpath(".//*[@id='detalleForm:proyecto_label']")).click();
	  driver.findElement(By.xpath(".//*[@id='detalleForm:proyecto_items']/li[text()='" + proyecto + "']")).click();
	  driver.findElement(By.xpath(".//*[@id='detalleForm:titulacion_label']")).click();
	  driver.findElement(By.xpath(".//*[@id='detalleForm:titulacion_items']/li[text()='" + titulacion + "']")).click();
	  driver.findElement(By.xpath(".//*[@id='detalleForm:estatus_label']")).click();
	  driver.findElement(By.xpath(".//*[@id='detalleForm:estatus_items']/li[text()='" + estatus + "']")).click();
	  driver.findElement(By.id("detalleForm:salario")).clear();
	  driver.findElement(By.id("detalleForm:salario")).sendKeys(salario);
	  driver.findElement(By.id("detalleForm:promocion_label")).click();
	  driver.findElement(By.xpath(".//*[@id='detalleForm:promocion_items']/li[text()='" + promocion + "']")).click();
	  driver.findElement(By.id("detalleForm:capacidad_label")).click();
	  driver.findElement(By.xpath(".//*[@id='detalleForm:capacidad_items']/li[text()='" + capacidad + "']")).click();
	  driver.findElement(By.id("detalleForm:nivelGCM_label")).click();
	  driver.findElement(By.xpath(".//*[@id='detalleForm:nivelGCM_items']/li[text()='" + nivelGCM + "']")).click();
	  driver.findElement(By.id("detalleForm:categoriaProfesional_label")).click();
	  driver.findElement(By.xpath(".//*[@id='detalleForm:categoriaProfesional_items']/li[text()='" + categoriaProfesional + "']")).click();
	  driver.findElement(By.id("detalleForm:guardarNuevo")).click();
  }
  
  public void modificar(String codigo, String nombre, String estatus, String capacidad) throws InterruptedException{
	  driver.findElement(By.xpath(".//*[@id='filtros:listaResultados_data']/tr[@data-rk='" + codigo + "']")).click();
	  Thread.sleep(2000);
	  
	  driver.findElement(By.id("detalleForm:nombre")).clear();
	  driver.findElement(By.id("detalleForm:nombre")).sendKeys(nombre);
	  	
	  driver.findElement(By.xpath(".//*[@id='detalleForm:capacidad_label']")).click();
	  driver.findElement(By.xpath(".//*[@id='detalleForm:capacidad_items']/li[text()='" + capacidad + "']")).click();
	    
	  driver.findElement(By.xpath(".//*[@id='detalleForm:estatus_label']")).click();
	  driver.findElement(By.xpath(".//*[@id='detalleForm:estatus_items']/li[text()='" + estatus + "']")).click();
	   
	  driver.findElement(By.id("detalleForm:modificar")).click();
  }

  @After
  public void tearDown() throws Exception {
	  driver.quit();
  }
}
