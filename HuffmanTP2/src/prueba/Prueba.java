package prueba;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
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
		//comprimirArchivo(1);
		comprimirArchivo_2(1);
		System.out.println("----------------------------------------------------------------------------------------------------------------------------");
		comprimirArchivo_2(2);
		System.out.println("----------------------------------------------------------------------------------------------------------------------------");
		comprimirArchivo_2(3);
	}

	public static void comprimirArchivo_2(int arch) throws IOException {
		FileReader fr = null;
		BufferedReader br = null;
		ArrayList<String> caracteres = new ArrayList();
		ArrayList<Frecuencia> frecuencia = new ArrayList();
		List<Simbolo> simbolos = new ArrayList<Simbolo>();
		int pos, auxFile;
		char auxChar;
		String aux;
		double contOrig = 0,entropia, longMedia, N;
		ListaSimple l = new ListaSimple();
		NodoLista listaOrig, listaArbol;
		Huffman h = new Huffman();
		Escritura escribir = null;
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
			contOrig++;
			auxChar = (char) auxFile;
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
				else {
					if(auxFile!=10) {
						contOrig++;
						auxChar = (char) auxFile;
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
				}
				
			
			
			
		 }
		}

		for (int i = 0; i < caracteres.size(); i++) {
			if (caracteres.get(i).equals(String.valueOf((char) 10)) && arch!=3) {
				simbolos.add(new Simbolo("enter", contOrig, frecuencia.get(i).getFrecuencia()));
			}
			else
				simbolos.add(new Simbolo(caracteres.get(i), contOrig, frecuencia.get(i).getFrecuencia()));
		}
	
		Collections.sort(simbolos);
		listaArbol = l.generaLista(simbolos);
		h.getHuffman(listaArbol.getArbol(), "", simbolos);
		for (int i = 0; i < simbolos.size(); i++) {
			System.out.println(simbolos.get(i).toString());
			escribir.agregaResultado(simbolos.get(i).toString());
		}
		h.compresionHuffman(listaArbol, arch, escribir);

		entropia = h.getEntropia(simbolos, escribir);
		longMedia = h.getLongMedia(escribir);
		br.close();
		fr.close();

		System.out.println("Entropia del código: " + entropia);
		System.out.println("Longitud media del código: " + longMedia);
		h.getRendimientoRedundancia(entropia, longMedia, escribir);
		N = contOrig / h.getTamComprimido(arch);
		h.getTasaCompresion(N, escribir);
		System.out.println("Tasa de compresión: " + N + ":1");
	}

	public static void comprimirArchivo(int arch) throws IOException {
		ArrayList<String> caracteres = new ArrayList();
		ArrayList<Frecuencia> frecuencia = new ArrayList();
		List<Simbolo> simbolos = new ArrayList<Simbolo>();
		ListaSimple l = new ListaSimple();
		NodoLista listaOrig, listaArbol;
		Huffman h = new Huffman();
		Escritura escribir=null;
		int pos;
		String aux;
		double contOrig = 0, entropia, longMedia, N;
		Scanner scan = null;
		if (arch == 1) {
			scan = new Scanner(new File("Argentina.txt"));
			escribir = new Escritura(arch);
		} else if (arch == 2) {
			scan = new Scanner(new File("Chino.txt"));
			escribir = new Escritura(arch);
		} else if (arch == 3) {
			scan = new Scanner(new File("imagen.raw"));
			scan.nextLine();
			scan.nextLine();
			escribir = new Escritura(arch);
		} 
		while (scan.hasNext()) {
			aux = scan.nextLine();
			for (int i = 0; i < aux.length(); i++) {
				contOrig++;
				if (caracteres.contains(String.valueOf(aux.charAt(i)))) {
					pos = busqueda(caracteres, String.valueOf(aux.charAt(i)));
					frecuencia.get(pos).aumentaFrec();
				} else {
					caracteres.add(String.valueOf(aux.charAt(i)));
					frecuencia.add(new Frecuencia());
				}

			}
			if (scan.hasNext() && arch != 3) { // ya que el último renglón no va a tener un \n y arch!=3 porque es el de
												// la imagen, y se trabaja igual que en la práctica
				// por lo tanto no hay que tener en cuenta a los \n
				contOrig++;
				aux = String.valueOf((char) 10);
				if (caracteres.contains(aux)) {
					pos = busqueda(caracteres, aux);
					frecuencia.get(pos).aumentaFrec();
				} else {
					caracteres.add(String.valueOf((char) 10));
					frecuencia.add(new Frecuencia());
				}
			}

		}
		for (int i = 0; i < caracteres.size(); i++) {
			if (caracteres.get(i).equals(String.valueOf((char) 10)))
				simbolos.add(new Simbolo("enter", contOrig, frecuencia.get(i).getFrecuencia()));
			else
				simbolos.add(new Simbolo(caracteres.get(i), contOrig, frecuencia.get(i).getFrecuencia()));
		}
		System.out.println();
		Collections.sort(simbolos);
		listaArbol = l.generaLista(simbolos);
		h.getHuffman(listaArbol.getArbol(), "", simbolos);
		for (int i = 0; i < simbolos.size(); i++) {
			System.out.println(simbolos.get(i).toString());
			escribir.agregaResultado(simbolos.get(i).toString());
		}
		h.compresionHuffman(listaArbol, arch, escribir);

		entropia = h.getEntropia(simbolos, escribir);
		longMedia = h.getLongMedia(escribir);
		scan.close();

		System.out.println("Entropia del código: " + entropia);
		System.out.println("Longitud media del código: " + longMedia);
		h.getRendimientoRedundancia(entropia, longMedia, escribir);
		N = contOrig / h.getTamComprimido(arch);
		h.getTasaCompresion(N, escribir);
		System.out.println("Tasa de compresión: " + N + ":1");
	}

	private static int busqueda(ArrayList caracteres, String carac) {
		int i = 0;
		while (i < caracteres.size() && !caracteres.get(i).equals(carac))
			i++;
		return i;
	}

}
