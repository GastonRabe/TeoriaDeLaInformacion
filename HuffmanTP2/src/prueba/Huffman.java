package prueba;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**CLASE EN DONDE SE VAN A EJECUTAR LOS METODOS PRINCIPALES PARA HUFFMAN
 * @author usuario
 *
 */
public class Huffman {

	private ArrayList<NodoArbol> auxLongitudes = new ArrayList();

	/** ACA SE CODIFICA A LOS SIMBOLOS
	 * @param arbol
	 * @param binario
	 * @param simbolos
	 */
	public void getHuffman(NodoArbol arbol, String binario, List<Simbolo> simbolos) {
		String auxBinario = "";
		if (arbol != null) {
			if (arbol.getPadre() != null) {
				auxBinario = arbol.getPadre().getCodigoBinario() + binario;
				arbol.setCodigoBinario(auxBinario);
			}
			this.getHuffman(arbol.getIzq(), "0", simbolos);
			if (arbol.getSimbolo().length() == 1) {
				this.asignaCodigo(arbol.getSimbolo(), arbol.getCodigoBinario(), simbolos);
				this.addLongitud(arbol);
			} else if (arbol.getSimbolo().equals("enter")) {
				this.asignaCodigo(arbol.getSimbolo(), arbol.getCodigoBinario(), simbolos);
				this.addLongitud(arbol);
			}
			this.getHuffman(arbol.getDer(), "1", simbolos);
		}

	}

	/**METODO QUE UTILIZA UNA COLECCION AUXILIAR PARA PODER IMPRIMIR EN ARCHIVOS Y/O PANTALLA LAS CODIFICACIONES DE HUFFMAN Y DE SHANNON FANO EN EL MISMO ORDEN INDEPENDIENTEMENTE DE SU CODIFICACION
	 * @param simbolo
	 * @param codHuffman
	 * @param simbolos COLECCION DE SIMBOLOS
	 */
	public void asignaCodigo(String simbolo, String codHuffman, List<Simbolo> simbolos) {
		int i = 0;
		while (i < simbolos.size() && !simbolo.equals(simbolos.get(i).getSimbolo()))
			i++;
		simbolos.get(i).setCodigoHuffman(codHuffman);
	}

	/**METODO EN DONDE SE ALMACENAN LAS LONGITUDES DE LOS CODIGOS EN HUFFMAN PARA FACILITAR LOS CALCULOS DE LONGITUD MEDIA
	 * @param arbol
	 */
	public void addLongitud(NodoArbol arbol) {
		this.auxLongitudes.add(arbol);
	}

	public double getEntropia(List<Simbolo> simbolos, Escritura escribir) {
		double auxEntropia = 0, auxProb;
		for (int i = 0; i < simbolos.size(); i++) {
			auxProb = simbolos.get(i).getProbabilidad();
			auxEntropia += auxProb * Math.log(1 / auxProb) / Math.log(2);
		}
		escribir.agregaResultado("Entropia del código: " + auxEntropia);
		return auxEntropia;

	}

	public void getTasaCompresion(double tamOrig, double tamComprimido, Escritura escribir) throws IOException {
		double n = tamOrig / tamComprimido;
		System.out.println("Tasa de compresión: " + n + ":1");
		escribir.agregaResultado("Tasa de compresión: " + n + ":1");
		escribir.cierraArchivo2();

	}

	public double getLongMedia(Escritura escribir) {
		double auxLM = 0;
		for (int i = 0; i < this.auxLongitudes.size(); i++) {
			auxLM += this.auxLongitudes.get(i).getProb() * this.auxLongitudes.get(i).getCodigoBinario().length();
		}
		escribir.agregaResultado("Longitud media del código: " + auxLM);
		return auxLM;
	}

	public void getRendimientoRedundancia(double entropia, double longMedia, Escritura escribir) {
		double rendimiento, redundancia;
		rendimiento = entropia / longMedia;
		redundancia = 1 - rendimiento;
		System.out.println("Rendimiento del código: " + rendimiento);
		System.out.println("Redundancia del código: " + redundancia);
		escribir.agregaResultado("Rendimiento del código: " + rendimiento);
		escribir.agregaResultado("Redundancia del código: " + redundancia);
	}

	/** ACA SE REALIZA LA COMPRESION POR HUFFMAN
	 * @param listaArbol
	 * @param arch
	 * @param escribir
	 * @throws IOException
	 */
	public void compresionHuffman(NodoLista listaArbol, int arch, Escritura escribir) throws IOException {
		String auxLetra;
		NodoArbol auxNodo = new NodoArbol(); // nodo auxiliar en el cual se ira asignando los codigos huffman para escribir en archivo
		auxNodo.setCodigoBinario("");
		FileReader fr = null;
		BufferedReader br = null;
		int auxFile;
		if (arch == 1) {
			fr = new FileReader("Argentina.txt");
			br = new BufferedReader(fr);
		} else if (arch == 2) {
			fr = new FileReader("Chino.txt");
			br = new BufferedReader(fr);
		} else {
			fr = new FileReader("imagen.raw");
			br = new BufferedReader(fr);
			br.readLine();
			br.readLine();
		}
		while ((auxFile = br.read()) != -1) {
			if (auxFile != 13) { // SE OMITE EL DENOMINADO RETORNO DE CARRO YA QUE NO ES NECESARIO
				if (arch != 3) {
					if (auxFile != 10) { // hay que asignarle la cadena "enter" ya que en la colección el simbolo esta seteado de esa forma
						char auxChar = (char) auxFile;
						auxLetra = String.valueOf(auxChar);
					} else 
						auxLetra = "enter";
					this.buscaSimbolo(listaArbol.getArbol(),auxLetra,auxNodo);
					escribir.agregaLinea(auxNodo.getCodigoBinario());
					auxNodo.setCodigoBinario("");
				} else { 
					if (auxFile != 10) {  // PARA LA IMAGEN SE OMITE LOS ENTER O \N
						char auxChar = (char) auxFile;
						auxLetra = String.valueOf(auxChar);
						this.buscaSimbolo(listaArbol.getArbol(),auxLetra,auxNodo);
						escribir.agregaLinea(auxNodo.getCodigoBinario());
						auxNodo.setCodigoBinario("");
					}
				}
			}
		}
		fr.close();
		br.close();
		escribir.cierraArchivo1();
		escribir.agregaResultado("--------------------------------------------------------------------");

	}
	/**METODO PARA BUSCAR UN SIMBOLO Y ASI RETORNAR SU CODIGO HUFFMAN PARA ESCRIBIR EN EL ARCHIVO A COMPRIMIR
	 * @param arbol
	 * @param caracter
	 * @param auxNodo
	 */
	public void buscaSimbolo(NodoArbol arbol, String caracter, NodoArbol auxNodo) {
		if (arbol != null) {
			this.buscaSimbolo(arbol.getIzq(), caracter, auxNodo);
				if (arbol.getSimbolo().equals(caracter))
					auxNodo.setCodigoBinario(arbol.getCodigoBinario());
			this.buscaSimbolo(arbol.getDer(), caracter, auxNodo);
		}
	}

	/**
	 * @param arch
	 * @return retorna el tamano del archivo original
	 * @throws IOException
	 */
	public double getTamArchivo(int arch) throws IOException {
		Path ruta;
		if (arch == 1) {
			ruta = Paths.get("Argentina.txt");

		} else if (arch == 2) {
			ruta = Paths.get("Chino.txt");
		} else {
			ruta = Paths.get("imagen.raw");
		}
		return Files.size(ruta);
	}

	public double getTamComprimido(int arch) throws IOException {
		double cont = 0;
		FileReader fr = null;
		BufferedReader br = null;
		int auxFile;
		if (arch == 1) {
			fr = new FileReader("ArgentinoHuffman.Huf");
			br = new BufferedReader(fr);
		} else if (arch == 2) {
			fr = new FileReader("ChinoHuffman.Huf");
			br = new BufferedReader(fr);
		} else {
			fr = new FileReader("ImagenHuffman.Huf");
			br = new BufferedReader(fr);

		}
		while ((auxFile = br.read()) != -1) //while !eof
			cont++;

		fr.close();
		br.close();
		return cont;
	}


}
