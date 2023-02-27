
package com.edenred.pages;

/**
 * 
 * @author amarroquin
 *  Esta clase solo sirve para hacer un contador y se reinicie cada que se ejecuta el test, que es el
 *         parametro i que lleva el método de capturaPantalla(i) para que se
 *         muestre el consecutivo de las screenshots _0,_1,_3
 */
public class ContadorCaptura {

	public int i;
	//Métodos get y set
	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}
	//Método constructor
	public ContadorCaptura() {

	}

}
