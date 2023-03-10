package com.project.test;

import org.testng.annotations.Test;

import com.edenred.pages.ContadorCaptura;
import com.edenred.pages.PageCargaMasiva;
import com.edenred.pages.PageInicioSesion;
import com.edenred.pages.Usuario;
import java.io.IOException;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;

/**
 * 
 * @author amarroquin Clase test del flujo de carga masiva
 */
public class TestPageCargaMasiva {
	// se instancian las clases para ser utilizadas en el test
	private WebDriver driver;
	PageInicioSesion inicioSesion;
	PageCargaMasiva flujoCargaMasiva;
	ContadorCaptura cc;
	Usuario usuario;

	@BeforeTest
	public void beforeTest() {
		inicioSesion = new PageInicioSesion(driver);
		driver = inicioSesion.chromeDriverConnection();
		inicioSesion.visit("https://emx-mo-websites-q-01.azurewebsites.net/MX/EdenredSSO/Account/LogOn");

		flujoCargaMasiva = new PageCargaMasiva(driver);
	}

	@Test
	public void f() throws InterruptedException, IOException {
		// se implementa el for para mandar a llamar 4 veces la carga del archivo con el
		// i<=3
		// si se desea implementar la carga 1 o 2 veces se debe de modificar el valor de
		// i<= y
		// poner el n numero de veces
		for (int i = 0; i <= 0; i++) {

			cc = new ContadorCaptura();
			cc.setI(0);
			usuario = new Usuario();
			boolean plantillaConapesca = inicioSesion.iniciarSesion(usuario, driver); // valida a que plantilla de tcw
																						// entra si
			System.out.println("plantilla conapesca test  " + plantillaConapesca); // a conapesca o alguna otra
			boolean assertion = inicioSesion.conapesca(plantillaConapesca); // valida que se muestre el elemento de la
																			// pagina
			Assert.assertTrue(assertion);
			if (plantillaConapesca == true) {
				break;
			} else {
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
				inicioSesion.capturaPantalla(cc, "Carga");
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
				flujoCargaMasiva.flujoCargaMasiva(cc, usuario, driver,i);
			}
		}

	}

	// M?todo para cerrar el driver

	@AfterMethod

	public void TearDown() {
		driver.close();
	}

}
