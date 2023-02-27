package com.edenred.pages;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.WebDriver;
/**
 * 
 * @author amarroquin
 *Clase que sirve para leer la primer linea del excel y replicarlo hacia abajo "N" cantidad que se indique en la estructura for
 */

//Método que se hereda de la clase padre automáticamente
public class ExcelUpdateService extends Base {
	public ExcelUpdateService(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
//El método recibe como parámetro el archivo que se genera de la clase base en el método clickCarga()
	public static void updateExcelFile(File archivoExcel, int conteo) {
		
		
		int numFilas = Constantes.arreglo[conteo]; //Número de filas que se desea replicar en el archivo de carga excel.
		List<String> listaFila1 = new ArrayList<>(); //se crea una lista para leer la información de la primer fila y guardarla temporalmente en el arrayList
		System.out.println("Entra al método de actualizacion de archivo excel");
		//File excelPath = new File("./src/main/resources/excel/CargaMasivaG500.xls");
		File excelPath = archivoExcel; // se crea para no definir en especifico un archivo excel
		try {
			FileInputStream fileInputStream = new FileInputStream(excelPath);

			Workbook workbook = WorkbookFactory.create(fileInputStream); //se crea el archivo excel
			Sheet sheet = workbook.getSheetAt(3); // se localiza el número de hoja que se va autilizar en este caso es la de "vehiculos" que es la 3 del libro

			int lastRowCount = sheet.getLastRowNum(); //se localiza la ultimafila escrita
			System.out.println("numero de ultima fila" + lastRowCount);
			Row rwInfo = sheet.getRow(7); // se lee la información de la fila 7 que es la unica que contiene datos

			// fila 7 que contiene información inicial en el archivo
			// se recorre el for para ir leyendo celda por celda hacia la derecha, hasta la
			// ultima columna se lee por tipo de dato boleano, numerico,string, etc
			for (int i = 0; i < rwInfo.getLastCellNum(); i++) {
				Cell cell = rwInfo.getCell(i);
				if (cell != null) {
					switch (cell.getCellType()) {
					case BOOLEAN:
						listaFila1.add(rwInfo.getCell(i).getBooleanCellValue() + "");
						break;
					case NUMERIC:
						listaFila1.add((int)rwInfo.getCell(i).getNumericCellValue() + "");
						break;
					case STRING:
						listaFila1.add(rwInfo.getCell(i).getStringCellValue());
						break;
					case BLANK:
						listaFila1.add("");
						break;
					case FORMULA:
                        listaFila1.add(rwInfo.getCell(i).getStringCellValue());
                        break;
					default:
						break;
					}
				} else {

					listaFila1.add("");

				}
				System.out.println("el contenido de la lista es: " + listaFila1.get(i));
			}
			int fila = 7;// comienza escribir en la fila 7 
			for (int j = 1; j <= numFilas; j++) {
				System.out.println("fila:  "+fila);
				Row dataRow = sheet.createRow(fila); //crea una nueva fila
				String placa = placaRandom(); //crea la placarandom método que se encuentra en la clase Base
				for (int k = 0; k < listaFila1.size(); k++) {
					Cell cell1 = dataRow.createCell(k);		//crea celda por celda		
					if(k==3 || k== 7 ) {
						cell1.setCellValue(placa);
					}else {
						cell1.setCellValue(listaFila1.get(k)); //aqui es donde por medio del for se van llenando las celdas del excel
					}
					
					//cell1.setCellValue(listaFila1.get(k));
					System.out.println("celda es igual a "+cell1.getStringCellValue() +k);
				}

				fila++;

			}
			//de aqui en adelante se cierra el archivo excel
			FileOutputStream fileOutputStream = new FileOutputStream(excelPath);
			workbook.write(fileOutputStream);
			workbook.close();
			System.out.println("Archivo excel actualizado correctamente");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
