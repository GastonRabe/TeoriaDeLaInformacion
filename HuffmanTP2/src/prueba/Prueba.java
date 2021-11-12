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
		double cont = 0,entropia, longMedia, N;//,tamOrig=0;
		double contEnters=0,contRetorno=0;
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
			if(auxFile!=13) {// SE OMITE EL DENOMINADO RETORNO DE CARRO YA QUE NO ES NECESARIO
				if(arch!=3) {
			cont++;
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
					if(auxFile!=10) {  // PARA LA IMAGEN SE OMITE LOS ENTER O \N
						cont++;
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
					else contEnters++; // MAS ADELANTE SE EXPLICA SU USO
				}
				
			
			
			
		 }
			else
				contRetorno++; // MAS ADELANTE SE EXPLICA SU USO
		}

		for (int i = 0; i < caracteres.size(); i++) {
			if (caracteres.get(i).equals(String.valueOf((char) 10)) && arch!=3) {
				simbolos.add(new Simbolo("enter", cont, frecuencia.get(i).getFrecuencia()));
			}
			else
				simbolos.add(new Simbolo(caracteres.get(i), cont, frecuencia.get(i).getFrecuencia()));
		}
	
		Collections.sort(simbolos);
		listaArbol = l.generaLista(simbolos);
		h.getHuffman(listaArbol.getArbol(), "", simbolos);
		for (int i = 0; i < simbolos.size(); i++) {
			System.out.println(simbolos.get(i).toString());
			escribir.agregaResultado(simbolos.get(i).toString()); // AQUI SE ESCRIBEN EN LOS ARCHIVOS DE RESULTADOS LA CODIFICACION EN HUFFMAN ADEMÁS DE OTROS DATOS
		} 
		h.auxCompresionHuffman(listaArbol, arch, escribir);
		h.compresionHuffman(arch);

		entropia = h.getEntropia(simbolos, escribir);
		longMedia = h.getLongMedia(escribir);
		br.close();
		fr.close();

		System.out.println("Entropia del código: " + entropia);
		System.out.println("Longitud media del código: " + longMedia);
		h.getRendimientoRedundancia(entropia, longMedia, escribir);
		if(arch!=3)
			h.getTasaCompresion(h.getTamArchivo(arch),h.getTamComprimido(arch),escribir); 
		else
		h.getTasaCompresion((h.getTamArchivo(arch)-contRetorno-contEnters-6),(h.getTamComprimido(arch)) ,escribir);
		// PARA LAS IMAGENES HAY QUE RESTAR LOS ENTERS O \N QUE VAN ACOMPANADOS DE LOS RETORNO DE CARRO, LOS 6 QUE TAMBIEN SE RESTAN CORRESPONDEN A LOS PRIMERO 6 BYTES QUE NO SE UTILIZAN DE LA IMAGEN, OBTENIENDO ASI EL TAMANO REAL UTILIZADO DE ELLA
	}


	private static int busqueda(ArrayList caracteres, String carac) {
		int i = 0;
		while (i < caracteres.size() && !caracteres.get(i).equals(carac))
			i++;
		return i;
	}

}
