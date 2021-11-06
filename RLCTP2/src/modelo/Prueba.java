package modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Prueba {
	/**
	 * RECOMENDACIÓN, SI EL ENCODING POR DEFECTO NO ES UTF-8, APLICARLO PARA PODER
	 * MOSTRAR TANTO LOS CARACTERES ESPECIALES EN ESPAÑOL COMO LOS CARACTERES EN
	 * CHINO WINDOW -> PREFERENCES -> GENERAL -> CONTENT TYPE -> PARARSE SOBRE TEXT
	 * -> DEBAJO EN DEFAULT ENCODING TIPEAR UTF-8 -> APLICAR Y CERRAR OTRA OPCION:
	 * ALT+ENTER Y DE APARECER OTRO TIPO DE ENCODING, PINCHAR EN OTHER Y SELECCIONAR
	 * UTF-8
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		comprimirArchivo_2(1);
		System.out.println("-----------------------------------------------------------------------------------");
		comprimirArchivo_2(2);
		System.out.println("-----------------------------------------------------------------------------------");
		comprimirImagen();
	}
	
	
	public static void comprimirImagen() throws IOException{
		String letraActual = "", rlc = "",aux="";
		ArrayList<Simbolo> simbolos = new ArrayList<>();
		Escritura escribir;
		int i = 0, contLetraActual = 0, pos = 0,auxFile,max=0;
		char auxChar;
		double contOrig = 0, tasaCompresion = 0;
		FileReader fr = null;
		BufferedReader br = null;
		fr = new FileReader("imagen.raw");
		br = new BufferedReader(fr);
		escribir = new Escritura(3);
		br.readLine();
		br.readLine();
			while((auxFile=br.read())!=-1) {
			if(auxFile!=13 && auxFile!=10) {
				auxChar = (char) auxFile;
				contOrig++;
				aux = String.valueOf(auxChar);
				if(letraActual.equals("")) {
					letraActual = aux;
					simbolos.add(new Simbolo(letraActual));
				}
				pos = buscaSimbolo(simbolos, aux);
				if (pos != -1)
					simbolos.get(pos).aumentaFrecuencia();
				else
					simbolos.add(new Simbolo(aux));
				if (aux.equals(letraActual))
					contLetraActual++;
				else {
						escribir.agregaLinea(letraActual + String.valueOf(contLetraActual));
						rlc += (letraActual + String.valueOf(contLetraActual));
						letraActual = aux;
						if(contLetraActual>max)
							max=contLetraActual;
						contLetraActual = 1;
					}
				}
			}
			if (contLetraActual > 0) {
					escribir.agregaLinea(letraActual + String.valueOf(contLetraActual));
					rlc += (letraActual + String.valueOf(contLetraActual));
					if(contLetraActual>max)
						max=contLetraActual;
			}
		fr.close();
		br.close();
		System.out.println("Compresión RLC:" + rlc);
		System.out.println(contOrig);
		escribir.cierraArchivo1();
		tasaCompresion = contOrig / getTamComprimido(3);
		System.out.println("Tasa de compresión " + tasaCompresion + ":1");
		escribir.agregaResultado("Tasa de compresión " + tasaCompresion + ":1");
		setProbabilidades(simbolos, contOrig);
		double entropia = getEntropia(simbolos);
		System.out.println("Entropía: " + entropia);
		double longMedia=getLongMedia(simbolos,max);
		System.out.println("Longitud Media: "+longMedia);
		escribir.agregaResultado("Entropía: " + entropia+" bits");
		escribir.agregaResultado("Longitud Media: "+longMedia+" bits");
		getRendimientoRedundancia(longMedia,entropia,escribir);
		escribir.cierraArchivo2();
	}
	
	public static void comprimirArchivo_2(int arch) throws IOException {
		String letraActual = "", rlc = "",aux="";
		ArrayList<Simbolo> simbolos = new ArrayList<>();
		Escritura escribir=null;
		int i = 0, contLetraActual = 0, pos = 0,auxFile,max=0;
		char auxChar;
		double contOrig = 0, tasaCompresion = 0;
		FileReader fr = null;
		BufferedReader br = null;
		if (arch == 1) {
			fr = new FileReader("Argentina.txt");
			br = new BufferedReader(fr);
			escribir = new Escritura(arch);
		}else if (arch==2) {
			fr = new FileReader("Chino.txt");
			br = new BufferedReader(fr);
			escribir = new Escritura(arch);
		}
		while((auxFile=br.read())!=-1) {
			if(auxFile!=13) {
				if(arch!=3) {
				auxChar = (char) auxFile;
				aux = String.valueOf(auxChar);
				if(letraActual.equals("")) {
					letraActual = aux;
					simbolos.add(new Simbolo(letraActual));
				}
				pos = buscaSimbolo(simbolos, aux);
				contOrig++;
				if (pos != -1)
					simbolos.get(pos).aumentaFrecuencia();
				else
					simbolos.add(new Simbolo(aux));

				if (aux.equals(letraActual))
					contLetraActual++;
				else {
					if(letraActual.equals(String.valueOf((char)10))){
						escribir.agregaLinea(String.valueOf((char)10) + String.valueOf(contLetraActual));
						rlc += (String.valueOf((char)10)+ String.valueOf(contLetraActual));
					}
					else {
						escribir.agregaLinea(letraActual + String.valueOf(contLetraActual));
						rlc += (letraActual + String.valueOf(contLetraActual));
					}
					if(contLetraActual>max)
						max=contLetraActual;
					letraActual = aux;
					contLetraActual = 1;
					
				}
			}
			}
		}
		if (contLetraActual > 0) {
			if(letraActual.equals(String.valueOf((char)10))) {
				escribir.agregaLinea(String.valueOf((char)10) + String.valueOf(contLetraActual));
				rlc += (String.valueOf((char)10)+ String.valueOf(contLetraActual));
			}
			else {
				escribir.agregaLinea(letraActual + String.valueOf(contLetraActual));
				rlc += (letraActual + String.valueOf(contLetraActual));
			}
			if(contLetraActual>max)
				max=contLetraActual;
		}
		for (i=0;i<simbolos.size();i++) {
			System.out.println(simbolos.get(i).toString());
		}
		br.close();
		fr.close();
		escribir.cierraArchivo1();
		System.out.println("Compresión RLC:" + rlc);
		System.out.println(contOrig);
		tasaCompresion = contOrig / getTamComprimido(arch);
		System.out.println("Tasa de compresión " + tasaCompresion + ":1");
		escribir.agregaResultado("Tasa de compresión " + tasaCompresion + ":1");
		setProbabilidades(simbolos, contOrig);
		double entropia = getEntropia(simbolos);
		double longMedia=getLongMedia(simbolos,max);
		System.out.println("Entropía: " + entropia+" bits");
		System.out.println("Longitud Media: "+longMedia+" bits");
		escribir.agregaResultado("Entropía: " + entropia+" bits");
		escribir.agregaResultado("Longitud Media: "+longMedia+" bits");
		getRendimientoRedundancia(longMedia,entropia,escribir);
		escribir.cierraArchivo2();
	}

	
	public static void getRendimientoRedundancia(double longMedia,double entropia,Escritura escribir) {
		double rendimiento, redundancia;
		rendimiento = entropia/longMedia;
		redundancia= 1-rendimiento;
		System.out.println("Rendimiento: "+rendimiento);
		System.out.println("Redundancia: "+redundancia);
		escribir.agregaResultado("Rendimiento: "+rendimiento);
		escribir.agregaResultado("Redundancia: "+redundancia);
		
		
	}
	public static double getLongMedia(ArrayList<Simbolo> simbolos,int max) {
		double rta=0;
		String binaryRepeticiones = Integer.toBinaryString(max);
		String binarySimbolos = Integer.toBinaryString(simbolos.size());
		rta = binaryRepeticiones.length() + binarySimbolos.length();
		return rta;
	}
	public static double getEntropia(ArrayList<Simbolo> simbolos) {
		double rta = 0, auxProb;
		for (int i = 0; i < simbolos.size(); i++) {
			auxProb = simbolos.get(i).getProbabilidad();
			rta += auxProb * Math.log(1 / auxProb) / Math.log(2);
		}
		return rta;
	}

	public static void setProbabilidades(ArrayList<Simbolo> simbolos, double cont) {
		for (int i = 0; i < simbolos.size(); i++) {
			double auxProb = simbolos.get(i).getFrecuencia() / cont;
			simbolos.get(i).setProbabilidad(auxProb);
		}
	}

	public static double getTamComprimido(int arch) throws FileNotFoundException {
		double contComp = 0;
		String aux;
		Scanner scan = null;
		if (arch == 1) {
			scan = new Scanner(new File("ArgentinoRLC.RLC"));
		} else if (arch == 2) {

			scan = new Scanner(new File("ChinoRLC.RLC"));
		} else {
			scan = new Scanner(new File("ImagenRLC.RLC"));
		}
		while (scan.hasNext()) {
			aux = scan.nextLine();
			contComp += aux.length();
		}
		return contComp;
	}

	public static int buscaSimbolo(ArrayList<Simbolo> simbolos, String caracter) {
		int i = 0;
		while (i < simbolos.size() && !simbolos.get(i).getSimbolo().equals(caracter)) {
			i++;
		}
		if (i >= simbolos.size())
			return -1;
		else
			return i;
	}

}
