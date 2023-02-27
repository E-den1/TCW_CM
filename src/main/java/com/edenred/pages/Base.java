package com.edenred.pages;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

/**
 * 
 * @author amarroquin ;los mensajes logs no se pintan 
 * clase padre:
 *         clase principal donde se gestionan todas las acciones, es decir todos
 *         los m�todos heredados a las dem�s clases(clases hijas: PageCargaMasiva,PageInicioSesion, ExcelUpdateService)
 */
public class Base {
	//private static final Logger log = LoggerFactory.getLogger(Base.class);// para pintar logs (no funciona)
	private WebDriver driver;

	// M�todo constructor
	public Base(WebDriver driver) {
		this.driver = driver;
	}

	// se crea el metodo para crear una instancia del driver de chrome
	public WebDriver chromeDriverConnection() {
		System.setProperty("webdriver.chrome.driver", "./src/main/resources/chromedriver/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		return driver;

	}

	// se crea un wrapper del metodo de selenium
	public WebElement findElement(By locator) {
		return driver.findElement(locator);
	}

	// se crea un wrapper llamando al metodo de selenium (NO se utiliza)
	public List<WebElement> findElements(By locator) {

		return driver.findElements(locator);
	}

	// M�todo que se utiliza para obtener el valor que se inserta dentro del input
	// de usuario
	public String getTextWeb(By locator) {
		WebElement padre = driver.findElement(locator);
		String usuario = padre.getAttribute("value");
		return usuario;
	}

	// M�todo para obtener el texto de un input general de la p�gina tcw
	public String getText(By locator) {
		return driver.findElement(locator).getText();
	}

	// M�todo para escribir en un input de la p�gina tcw
	public void type(String inputText, By locator) {

		driver.findElement(locator).sendKeys(inputText);

	}

	// M�todo click para hacer click en cualquier bot�n o men� de la p�gina
	public void click(By locator) {
		driver.findElement(locator).click();
	}

	// M�todo de Carga del archivo excel de acuerdo al usuario ingresado en TCW
	public void clickCarga(By archivo, By locator, Usuario usuario, int conteo) throws InterruptedException {
		
		System.out.println("entra al m�todo de clickCarga");
		if (usuario.getUsuario().equals("azucarmexicano@mailinator.com")) {
			System.out.println("Entra a BP archivo de carga");
			File file = new File("./src/main/resources/excel/CargaMasivaBP.xls");
			ExcelUpdateService.updateExcelFile(file,conteo); //aqui es donde se va a la clase de actuaizacion de archivo excel para duplicar filas de "N" n�mero de pedidos de acuerdo a la primer fila
			String path = file.getAbsolutePath();
			driver.findElement(locator).sendKeys(path);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));

		} else if (usuario.getUsuario().equals("lala2@mailinator.com")) {
			System.out.println("Entra a TC archvio de carga");
			File file = new File("./src/main/resources/excel/CargaMasivaTC.xls");
			ExcelUpdateService.updateExcelFile(file, conteo);
			String path = file.getAbsolutePath();
			driver.findElement(locator).sendKeys(path);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));

		} else if (usuario.getUsuario().equals("whitelabelqa_g500fisico@mailinator.com")) {
			System.out.println("Entra a G500 archivo de carga");
			File file = new File("./src/main/resources/excel/CargaMasivaG500.xls");
			ExcelUpdateService.updateExcelFile(file, conteo);
			String path = file.getAbsolutePath();
			driver.findElement(locator).sendKeys(path);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));

		} else if (usuario.getUsuario().equals("whitelabelqa_servifacilfisico@mailinator.com")) {
			System.out.println("Entra a servifacil archivo de carga");
			File file = new File("./src/main/resources/excel/CargaMasivaServifacil.xls");
			ExcelUpdateService.updateExcelFile(file, conteo);
			String path = file.getAbsolutePath();
			driver.findElement(locator).sendKeys(path);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));

		} else {
			System.out.println("No agarra ningun template de carga");
		}

		// driver.findElement(archivo).click();
	}

	// M�todo que permite verificar si un elemento esta visible en la p�gina de TCW
	public Boolean isDisplayed(By locator) {
		try {
			return driver.findElement(locator).isDisplayed();
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}
	// M�todo que permite revisar en tcw que exista un elemento (se ocupa para la notificaci�n de carga)
	public Boolean exist(By locator) {
		try {
			WebElement existe = driver.findElement(locator);
			if (existe != null) {
				return true;
			} else {
				return false;
			}
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		}
	}

	// M�todo para obtener el url principal, es decir el url del tcw
	public void visit(String url) {
		driver.get(url);
	}

	// M�todo para limpiar el input de usuario
	public void cleanInput(By locator) {
		driver.findElement(locator).clear();
	}

	// M�todo para validar alerta de incio de sesi�n cuando se presiona el bot�n
	// siguiente antes de meter el email (NO se usa)
	public void cleanAlert(By locator) {
		WebElement element = driver.findElement(locator);
		((JavascriptExecutor) driver).executeScript("arguments[0].style.display='none'", element);
	}

	// M�todo para validar alerta de incio de sesi�n cuando se presiona el boton
	// sigueinte antes de meter el email (no se usa)
	public void showAlert(By locator) {
		WebElement element = driver.findElement(locator);
		((JavascriptExecutor) driver).executeScript("arguments[0].style.display='block'", element);
	}

	// M�todo para validar que entre al formato de p�gina de conapesca o un template general solo por si se entra con conapesca no mande error y se cierre el proceso automatizado
	public boolean conapesca(boolean isConapesca) {
		//log.info("Entra a m�todo conapesca valor ", isConapesca);
		System.out.println("entra al metodo isconapesca--------------------------------------------" + isConapesca);
		if (isConapesca) {		
			if (driver.findElement(By.id("lbCloseSesion")).isDisplayed()) {
				return true;
			} else {
				System.err.println("fall� elemento no encontrado en TCW");
				// log.error("fall� elemento no encontrado en TCW");
				return false;
			}
		} else {

			if (driver.findElement(By.id("ctl00_LoginStatusImage")).isDisplayed()) {
				return true;
			} else {
				System.out.println("fall� elemento no encontrado en TCW");
				return false;
			}

		}
	}

	// M�todo para incluir la fecha en la captura de pantalla
	public String getDate() {
		SimpleDateFormat standar;
		Calendar now = new GregorianCalendar();
		Date nowDate = now.getTime();
		standar = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

		return standar.format(nowDate);
	}

	// M�todo para realizar captura de pantalla con parametros de entrada i para contabilizar el n�mero de captura y carpeta donde se guardar� la captura
	public void capturaPantalla(ContadorCaptura i, String carpeta) throws IOException {

		System.out.println("Entra al m�todo de captura de pantalla" + getDate());
		File screnshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		FileUtils.copyFile(screnshotFile, new File("./src/main/resources/screen/" + carpeta + "/" + getDate().replace(":", "") + "_" + i.getI() + ".png"));
		i.setI(i.getI() + 1);

	}
	//M�todo para cerrar el driver de chrome, lo cu�l hace que se cierre la ventana de chrome
	public void cerrarVentana() {
		driver.close();
	}
	//M�todo para generar placas random en el archivo excel para la columna D y H
	public static String placaRandom() {
		String placaLetras = RandomStringUtils.randomAlphabetic(4);
		placaLetras= placaLetras.toUpperCase();
		String placaNum = RandomStringUtils.randomNumeric(4);		
		String placa = placaLetras+placaNum;
		System.out.println("la placa random es:  "+placa);
		return placa;
	}
}