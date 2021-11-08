package prueba;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Huffman {

	private ArrayList<NodoArbol> auxLongitudes = new ArrayList();
	private ArrayList<NodoArbol> auxCompresion = new ArrayList();

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
				this.auxColecciones(arbol);
			} else if (arbol.getSimbolo().equals("enter")) {
				this.asignaCodigo(arbol.getSimbolo(), arbol.getCodigoBinario(), simbolos);
				this.auxColecciones(arbol);
			}
			this.getHuffman(arbol.getDer(), "1", simbolos);
		}

	}

	public void asignaCodigo(String simbolo, String codHuffman, List<Simbolo> simbolos) {
		int i = 0;
		while (i < simbolos.size() && !simbolo.equals(simbolos.get(i).getSimbolo()))
			i++;
		simbolos.get(i).setCodigoHuffman(codHuffman);
	}

	public void auxColecciones(NodoArbol arbol) {
		NodoArbol aux = new NodoArbol();
		this.auxLongitudes.add(arbol);
		aux.setCodigoBinario(arbol.getCodigoBinario());
		aux.setSimbolo(arbol.getSimbolo());
		this.auxCompresion.add(aux);
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

	public void compresionHuffman(NodoLista listaArbol, int arch, Escritura escribir) throws IOException {
		String auxLetra;
		NodoArbol auxNodo = new NodoArbol();
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
			if (auxFile != 13) {
				if (arch != 3) {
					if (auxFile != 10) {
						char auxChar = (char) auxFile;
						auxLetra = String.valueOf(auxChar);
					} else 
						auxLetra = "enter";
					int i=0;
					while(i<this.auxCompresion.size() && !auxLetra.equals(this.auxCompresion.get(i).getSimbolo())){
						i++;
					}
					escribir.agregaLinea(this.auxCompresion.get(i).getCodigoBinario());
					auxNodo.setCodigoBinario("");
				} else {
					if (auxFile != 10) {
						char auxChar = (char) auxFile;
						auxLetra = String.valueOf(auxChar);
						int i=0;
						while(i<this.auxCompresion.size() && !auxLetra.equals(this.auxCompresion.get(i).getSimbolo())){
							i++;
						}
						escribir.agregaLinea(this.auxCompresion.get(i).getCodigoBinario());
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
		while ((auxFile = br.read()) != -1)
			cont++;

		fr.close();
		br.close();
		return cont;
	}

	public void buscaLetra(NodoArbol arbol, String caracter, NodoArbol auxNodo) {
		if (arbol != null) {
			this.buscaLetra(arbol.getIzq(), caracter, auxNodo);
			//if (arbol.getSimbolo().length() == 1) {
				if (arbol.getSimbolo().equals(caracter))
					auxNodo.setCodigoBinario(arbol.getCodigoBinario());
			//} 
				else if (arbol.getSimbolo().equals("enter")) 
				auxNodo.setCodigoBinario(arbol.getCodigoBinario());
			this.buscaLetra(arbol.getDer(), caracter, auxNodo);
		}
	}
}
