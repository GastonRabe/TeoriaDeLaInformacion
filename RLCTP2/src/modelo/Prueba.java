package modelo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Prueba {
	/**RECOMENDACIÓN, SI EL ENCODING POR DEFECTO NO ES UTF-8, APLICARLO PARA PODER MOSTRAR TANTO LOS CARACTERES ESPECIALES EN ESPAÑOL COMO LOS CARACTERES EN CHINO
	 * WINDOW -> PREFERENCES -> GENERAL -> CONTENT TYPE -> PARARSE SOBRE TEXT -> DEBAJO EN DEFAULT ENCODING TIPEAR UTF-8 -> APLICAR Y CERRAR
	 * OTRA OPCION: ALT+ENTER Y DE APARECER OTRO TIPO DE ENCODING, PINCHAR EN OTHER Y SELECCIONAR UTF-8
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, TipoInvalidoException {
		comprimirArchivo(1);
		System.out.println("-----------------------------------------------------------------------------------");
		comprimirArchivo(2);
		System.out.println("-----------------------------------------------------------------------------------");
		comprimirArchivo(3);
	}
	public static void comprimirArchivo(int arch) throws IOException,TipoInvalidoException {
		String letraActual="",rlc="",caracter,aux;
		Scanner scan;
		Escritura escribir = new Escritura(arch);
		int i=0,cont=0;
		double contOrig=0,tasaCompresion=0;
		if(arch==1) {
			 scan = new Scanner(new File("Argentina.txt"));
		}
		else if(arch==2) {
			 scan = new Scanner(new File("Chino.txt"));
		}
		else if (arch==3){
			 scan = new Scanner(new File("imagen.raw"));
			scan.nextLine();scan.nextLine();
		}
		else throw new TipoInvalidoException("No existe el archivo deseado");
		aux = String.valueOf(scan.nextLine());
		contOrig+=aux.length();
		letraActual=String.valueOf(aux.charAt(0));
		cont=1;
		for(i=1;i<aux.length();i++) {
			caracter=String.valueOf(aux.charAt(i));
			if(caracter.equals(letraActual)) 
				cont++;
			else {
				escribir.agregaLinea(letraActual+String.valueOf(cont));
				rlc+=(letraActual+String.valueOf(cont));
				letraActual=caracter;
				cont=1;
			}
		}
		while (scan.hasNext()) {
			aux = scan.nextLine();
			contOrig+=aux.length();
			for (i = 0; i < aux.length(); i++) {
				caracter=String.valueOf(aux.charAt(i));
				if(caracter.equals(letraActual)) 
					cont++;	
				else {
					escribir.agregaLinea(letraActual+String.valueOf(cont));
					rlc+=(letraActual+String.valueOf(cont));
					letraActual=caracter;
					cont=1;
				}

			}

		}
		if(cont>0) {
			escribir.agregaLinea(letraActual+String.valueOf(cont));
			rlc+=(letraActual+String.valueOf(cont));
		}
		scan.close();
		escribir.cierraArchivo();
		//System.out.println("Compresión RLC:"+rlc);
		tasaCompresion=contOrig/getTamComprimido(arch);
		System.out.println("Tasa de compresion "+tasaCompresion+":1");
	}
		
		
		
	public static double getTamComprimido(int arch) throws FileNotFoundException {
		double contComp=0;
		String aux;
		Scanner scan = null;
		if(arch==1) {
			 scan = new Scanner(new File("ArgentinoRLC.txt"));
		}
		else if(arch==2) {
	
			 scan = new Scanner(new File("ChinoRLC.txt"));
		}
		else {
			 scan = new Scanner(new File("ImagenRLC.txt"));
		}
		while(scan.hasNext()) {
			aux=scan.nextLine();
			contComp+=aux.length();
		}
		return contComp;
	}

}
