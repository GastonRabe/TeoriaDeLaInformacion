package modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
		comprimirArchivo_2(3);
	
	}
	
	public static void comprimirArchivo_2(int arch) throws IOException{
		ArrayList<String> caracteres = new ArrayList();
		ArrayList<Frecuencia> frecuencia = new ArrayList();
		List<SimboloShannon> simbolos = new ArrayList<SimboloShannon>();
		Escritura escribir;
		OpShannon o = new OpShannon(); // aca se invocan los metodos para obtener shannon
		int pos,auxFile;
		char auxChar;
		double cont = 0, entropia, longMedia, N,tamOrig=0;
		double contRetorno=0,contEnters=0;
		String aux;
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
		}else {
			fr = new FileReader("imagen.raw");
			br = new BufferedReader(fr);
			escribir = new Escritura(arch);
			br.readLine();
			br.readLine();
		}
		while ((auxFile = br.read()) != -1) {
			if(auxFile!=13) {
				if(arch!=3) {
			cont++;
			auxChar = (char) auxFile;
			aux = String.valueOf(auxChar);
			String binary = Integer.toBinaryString(auxFile);
			tamOrig+=binary.length();	
			if (caracteres.contains(aux)) {
				pos = busqueda(caracteres, aux);
				frecuencia.get(pos).aumentaFrec();
			}

			else {
				caracteres.add(aux);
				frecuencia.add(new Frecuencia());
			}
		 }
			else {
				if(auxFile!=10) {
					cont++;
					auxChar = (char) auxFile;
					String binary = Integer.toBinaryString(auxFile);
					tamOrig+=binary.length();	
					aux = String.valueOf(auxChar);
					if (caracteres.contains(aux)) {
						pos = busqueda(caracteres, aux);
						frecuencia.get(pos).aumentaFrec();
					}

					else {
						caracteres.add(aux);
						frecuencia.add(new Frecuencia());
					}
					
				}
				else 
					contEnters++;
			}
		}
			else
				contRetorno++;
		}

		for (int i = 0; i < caracteres.size(); i++) {
			if (caracteres.get(i).equals(String.valueOf((char) 10)) && arch!=3) {
				simbolos.add(new SimboloShannon("enter", cont, frecuencia.get(i).getFrecuencia()));
			}
			else
				simbolos.add(new SimboloShannon(caracteres.get(i), cont, frecuencia.get(i).getFrecuencia()));
		}
		Collections.sort(simbolos);
		o.iniciaShannon(0, simbolos.size()-1, simbolos);
		o.muestraShannon(simbolos, escribir);
		o.compresionShannonFano(simbolos, arch, escribir);
		entropia = o.getEntropia(simbolos, escribir);
		longMedia = o.getLongMedia(simbolos, escribir);
		System.out.println("Entropia del código: " + entropia);
		System.out.println("Longitud media del código: " + longMedia);
		o.getRendimientoRedundancia(entropia, longMedia, escribir);
		if(arch!=3)
			o.getTasaCompresion(o.getTamArchivo(arch),(o.getTamComprimido(arch)/8),escribir);
		else 
			o.getTasaCompresion((o.getTamArchivo(arch)-contEnters-contRetorno-6),(o.getTamComprimido(arch)/8) , escribir);
	}

	
	private static int busqueda(ArrayList caracteres, String carac) {
		int i = 0;
		while (i < caracteres.size() && !caracteres.get(i).equals(carac))
			i++;
		return i;
	}
}
