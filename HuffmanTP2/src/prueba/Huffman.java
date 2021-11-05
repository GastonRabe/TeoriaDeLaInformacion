package prueba;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Huffman {

	private ArrayList<NodoArbol> auxLongitudes = new ArrayList();
	
	public void getHuffman(NodoArbol arbol,String binario,List<Simbolo> simbolos) {
		String auxBinario="";
		if(arbol!=null) {
			if(arbol.getPadre()!=null) {
				auxBinario=arbol.getPadre().getCodigoBinario()+binario;
				arbol.setCodigoBinario(auxBinario);
			}
			this.getHuffman(arbol.getIzq(),"0",simbolos);
			if(arbol.getSimbolo().length()==1) {
			this.asignaCodigo(arbol.getSimbolo(),arbol.getCodigoBinario(),simbolos);
			this.agregaLongitud(arbol);
			}else
				if(arbol.getSimbolo().equals("enter")) {
					this.asignaCodigo(arbol.getSimbolo(),arbol.getCodigoBinario(),simbolos);
					this.agregaLongitud(arbol);
				}
			this.getHuffman(arbol.getDer(), "1",simbolos);
		}
		
	}
	
	public void asignaCodigo(String simbolo,String codHuffman,List<Simbolo> simbolos) {
		int i=0;
		while(i<simbolos.size() &&!simbolo.equals(simbolos.get(i).getSimbolo())) 
			i++;
		simbolos.get(i).setCodigoHuffman(codHuffman);
	}
	
	public void agregaLongitud(NodoArbol arbol) {
		this.auxLongitudes.add(arbol);
	}
	
	public double getEntropia(List<Simbolo> simbolos,Escritura escribir) {
		double auxEntropia=0,auxProb;
		for(int i=0;i<simbolos.size();i++) {
			auxProb=simbolos.get(i).getProbabilidad();
			auxEntropia+=auxProb*Math.log(1/auxProb)/Math.log(2);
		}
		escribir.agregaResultado("Entropia del código: "+auxEntropia);
		return auxEntropia;
		
	}
	
	public void getTasaCompresion(double n,Escritura escribir) throws IOException {
		escribir.agregaResultado("Tasa de compresión: "+n+":1");
		escribir.cierraArchivo2();

	}
	public double getLongMedia(Escritura escribir) {
		double auxLM=0;
		for(int i=0;i<this.auxLongitudes.size();i++) {
			auxLM+=this.auxLongitudes.get(i).getProb()*this.auxLongitudes.get(i).getCodigoBinario().length();
		}
		escribir.agregaResultado("Longitud media del código: "+auxLM);
		return auxLM;
	}
	
	
	
	public void getRendimientoRedundancia(double entropia,double longMedia,Escritura escribir) {
		double rendimiento,redundancia;
		rendimiento=entropia/longMedia;
		redundancia = 1-rendimiento;
		System.out.println("Rendimiento del código: "+rendimiento);
		System.out.println("Redundancia del código: "+redundancia);
		escribir.agregaResultado("Rendimiento del código: "+rendimiento);
		escribir.agregaResultado("Redundancia del código: "+redundancia);
	}
	public void compresionHuffman(NodoLista listaArbol,int arch,Escritura escribir) throws IOException {
		String auxLetra,aux;
		NodoArbol auxNodo = new NodoArbol();
		auxNodo.setCodigoBinario("");
		Scanner scan=null;
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
		escribir.cierraArchivo1();
		escribir.agregaResultado("--------------------------------------------------------------------");
		
	}
	
	public double getTamComprimido(int arch) throws FileNotFoundException {
		double cont=0;
		String aux;
		Scanner scan=null;
		if(arch==1) {
			 scan = new Scanner(new File("ArgentinoHuffman.Huf"));
		}else if(arch==2) {
			 scan = new Scanner(new File("ChinoHuffman.Huf"));
		}
		else {
			 scan = new Scanner(new File("ImagenHuffman.Huf"));
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
