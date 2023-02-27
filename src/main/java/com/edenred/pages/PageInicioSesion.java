package com.edenred.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * 
 * @author amarroquin 
 * clase donde se delcaran los elementos html del inicio de
 *         sesion, esta clase hereda de la clase padre Base que es donde se declaran todos los métodos para manipular los elementos html
 *
 */
public class PageInicioSesion extends Base {

	// método construtor heredado de la clase padre
	public PageInicioSesion(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	// elementos localizadores con los cuales se accede a la página de tcw
	By candado = By.className("SecureImg");
	By usuarioTcw = By.id("UserName");
	By botonContinuar = By.id("ButtonLogin");
	By passwodTcw = By.id("TallyHawk");
	By btnInicio = By.id("ButtonLogin");
	By tarjeta = By.id("imgPrd0");
	By alerta = By.xpath("//*[@id=\"FrmLogon\"]/div[3]/div/div/table/tbody/tr[3]/td[2]/div/div");
	By cookies = By.id("onetrust-accept-btn-handler");

	// método principal para iniciar sesión con los 5 usuarios dependiendo el que se
	// inserte en usuario de tcw
	public boolean iniciarSesion(Usuario user, WebDriver driver) throws InterruptedException {
		boolean flag = false; //bandera para usuario conapesca
		String usuario = null;
		if (isDisplayed(candado)) {
			driver.findElement(cookies).click();
			usuario = getTextWeb(usuarioTcw);
			user.setUsuario(usuario);
			Thread.sleep(2000);
			if (user.getUsuario().trim().equals("daniel.ortega@edenred.com")) {
				flag = true;
			}
			System.out.println(usuario + "usuario antes del if");

			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
			if (!usuario.equals("azucarmexicano@mailinator.com")
					&& !usuario.equals("whitelabelqa_g500fisico@mailinator.com")
					&& !usuario.equals("whitelabelqa_servifacilfisico@mailinator.com")
					&& !usuario.equals("lala2@mailinator.com") && !usuario.equals("daniel.ortega@edenred.com")) {
				System.out.println("entra a vacio" + usuario);
				iniciarSesion(user, driver);

			} else {
				System.out.println(usuario);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
				click(botonContinuar);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
				type("Abcd1234", passwodTcw); // se introduce en duro la contraseña ya que es la misma para los usuarios implementados
				click(btnInicio);
				click(tarjeta);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
				Thread.sleep(2000);

			}

		} else {
			System.out.println("no se encontró algun elemento para iniciar la sesión");
		}
		return flag; // retorna la bandera para saber si se inicio con usuario de conapesca
	}
}