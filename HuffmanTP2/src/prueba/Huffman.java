package prueba;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Huffman {

	private ArrayList<NodoArbol> auxLongitudes = new ArrayList();
	
	public void getHuffman(NodoArbol arbol,String binario) {
		String auxBinario="";
		if(arbol!=null) {
			if(arbol.getPadre()!=null) {
				auxBinario=arbol.getPadre().getCodigoBinario()+binario;
				arbol.setCodigoBinario(auxBinario);
			}
			this.getHuffman(arbol.getIzq(),"0");
			if(arbol.getSimbolo().length()==1) {
			System.out.println("Simbolo: "+arbol.getSimbolo()+" Código Huffman: "+arbol.getCodigoBinario());
			this.agregaLongitud(arbol);
			}
			this.getHuffman(arbol.getDer(), "1");
		}
		
	}
	
	public void agregaLongitud(NodoArbol arbol) {
		this.auxLongitudes.add(arbol);
	}
	
	public double getEntropia(List<Simbolo> simbolos) {
		double auxEntropia=0,auxProb;
		for(int i=0;i<simbolos.size();i++) {
			auxProb=simbolos.get(i).getProbabilidad();
			auxEntropia+=auxProb*Math.log(1/auxProb)/Math.log(2);
		}
		return auxEntropia;
		
	}
	
	public double getLongMedia() {
		double auxLM=0;
		for(int i=0;i<this.auxLongitudes.size();i++) {
			auxLM+=this.auxLongitudes.get(i).getProb()*this.auxLongitudes.get(i).getCodigoBinario().length();
		}
		return auxLM;
	}
	
	public void getRendimientoRedundancia(double entropia,double longMedia) {
		double rendimiento,redundancia;
		rendimiento=entropia/longMedia;
		redundancia = 1-rendimiento;
		System.out.println("Rendimiento del código: "+rendimiento);
		System.out.println("Redundancia del código: "+redundancia);
	}
	public void compresionHuffman(NodoLista listaArbol,int arch) throws IOException {
		String auxLetra,aux;
		NodoArbol auxNodo = new NodoArbol();
		auxNodo.setCodigoBinario("");
		Scanner scan=null;
		Escritura escribir = new Escritura(arch);
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
		while(scan.hasNext()) {
			aux=scan.nextLine();
			for (int i = 0; i < aux.length(); i++) {
				auxLetra=String.valueOf(aux.charAt(i));
				this.buscaLetra(listaArbol.getArbol(),auxLetra,auxNodo);
				escribir.agregaLinea(auxNodo.getCodigoBinario());
				auxNodo.setCodigoBinario("");
			
			}
		}
		escribir.cierraArchivo();
		
	}
	
	public double getTamComprimido(int arch) throws FileNotFoundException {
		double cont=0;
		String aux;
		Scanner scan=null;
		if(arch==1) {
			 scan = new Scanner(new File("ArgentinoHuffman.txt"));
		}else if(arch==2) {
			 scan = new Scanner(new File("ChinoHuffman.txt"));
		}
		else {
			 scan = new Scanner(new File("ImagenHuffman.txt"));
		}
		while(scan.hasNext()) {
			aux=scan.nextLine();
			for (int i = 0; i < aux.length(); i++) {
			cont++;
			}
		}
		return cont;
	}
	
	public void buscaLetra(NodoArbol arbol, String caracter, NodoArbol auxNodo) {
		if(arbol!=null) {
			this.buscaLetra(arbol.getIzq(), caracter, auxNodo);
			if(arbol.getSimbolo().length()==1)
				if(arbol.getSimbolo().equals(caracter))
					auxNodo.setCodigoBinario(arbol.getCodigoBinario());
			this.buscaLetra(arbol.getDer(), caracter, auxNodo);
		}
	}
}
