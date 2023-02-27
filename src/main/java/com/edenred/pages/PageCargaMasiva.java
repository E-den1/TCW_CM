package com.edenred.pages;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * 
 * @author amarroquin 
 * clase dónde se declaran los elementos html de la página
 *         para el flujo de carga masiva
 *         Aqui se interactua más con los elementos  html y se mandan a traer los métodos ya definidos en la clase Base
 */
public class PageCargaMasiva extends Base {

	// método constructor de la clase padre
	public PageCargaMasiva(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	// Elementos localizadores con los cuales se manipula la pag

	By gestionDatos = By.linkText("Gestión de datos");
	By vehiculo = By.linkText("Vehículo");
	By btnCargaMasiva = By.id("ctl00_contenido_listarVehiculo_btnCargaMasiva");
	By seleccionarArchivo = By.id("ctl00_contenido_CargaVehiculo_fileupload1");
	By envioArchivo = By.id("file-upload-button");
	By herramientas = By.linkText("Herramientas");
	By segCargasMa = By.linkText("Seguimiento de Cargas Masivas");
	By selectCargaMas = By.id("ctl00_contenido_ctlResumen_ddlTipoCarga");
	By altaActVehiculos = By.xpath("//*[@id=\"ctl00_contenido_ctlResumen_ddlTipoCarga\"]/option[8]");
	By tarjetasyCreditos = By.linkText("Tarjetas y Créditos");
	By gestionarPedidos = By.linkText("Gestionar Pedidos de Tarjetas");
	By btnConsultar = By.id("ctl00_contenido_btnConsultar");
	By notificacionCargaOk = By.className("icon-ok-sign");// fue recibido correctamente
	By notificacionCarga = By.className("icon-exclamation-sign");
	By cerrarSesion = By.id("ctl00_LoginStatusImage");
	String carpeta1 = "Carga";
	String carpeta2 = "Consulta";

	/*
	 * Método principal donde se realiza paso a paso la carga del archivo, el método tiene 3 parametros
	 * i: para ir contabilizando el numero de screenshot
	 * usuario: para verrificar con que usuario se ingresa desde el inicio de sesion y ver que archivo excel de carga tomar
	 * driver.inicializado desde el test para utilizar los implicitlywait y no usar Thread.sleep
	 */
	public void flujoCargaMasiva(ContadorCaptura i, Usuario usuario, WebDriver driver, int conteo)
			throws InterruptedException, IOException {
		
		if (isDisplayed(gestionDatos)) {
			click(gestionDatos);
			click(vehiculo);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
			click(btnCargaMasiva);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
			clickCarga(envioArchivo, seleccionarArchivo, usuario, conteo);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		} else {
			flujoCargaMasiva(i, usuario, driver, conteo); //recursividad llamando asi mismo el método por si no se encentra de primer instancia los elementos
			System.out.println("no se encontró algun elemento");
		}

		while (true) { // se implementa el while para hacer la espera de la notificacion de carga y mande la alerta verde de carga exitosa
			if (!exist(notificacionCarga)) {
				break;
			} else {
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
				System.out.println("entra al iterador while");

			}
		}

		if (isDisplayed(notificacionCargaOk)) {
			System.out.println("Entra al if de la notificacion de carga");
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
			capturaPantalla(i, carpeta1); //captura la pantalla de carga masiva exitosa
			click(herramientas);
			click(segCargasMa);
			click(selectCargaMas);
			click(altaActVehiculos);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(6));
			capturaPantalla(i, carpeta1);

		} else {
			System.out.println("No se cargó correctamente el archivo, falló");
		}

		try {
			click(cerrarSesion); //cierra sesion y manda a login de tcw despues de haber realizado la carga de archivo correctamente

		} catch (Exception e) {
			cerrarVentana(); // sino solo cierra la ventana, esto sucede para BP generalmente ya que a veces al intentar cerrar sesion genera un error 404
		}

		// }
	}

	//método que se genera para consultar el estatus del pedido mucho despues de haber realizado la carga masiva utilizado en el TestPageConsultaEstatusPedido
	public void consultaPedido(ContadorCaptura i, WebDriver driver) throws InterruptedException, IOException {
		click(tarjetasyCreditos);
		click(gestionarPedidos);
		click(btnConsultar);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		capturaPantalla(i, carpeta2);
		click(cerrarSesion);
	}

}
