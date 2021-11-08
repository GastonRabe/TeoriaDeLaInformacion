package modelo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class OpShannon {
	
	
	public void iniciaShannon(int limiteInf, int limiteSup, List<SimboloShannon> simbolos) {
		if (limiteInf != limiteSup) {
			int i, j, corte = 0;
			float inf = 0, sup = 0, dif = 0, probDif = 0;
			for (i = limiteInf; i <= limiteSup; i++) {
				sup += simbolos.get(i).getProbabilidad();
				for (j = i + 1; j <= limiteSup; j++) {
					inf += simbolos.get(j).getProbabilidad();
				}
				dif = (sup - inf) * (sup - inf);
				if (inf == sup) {
					break;
				}
				if (i == limiteInf) {
					probDif = dif;
				} else {
					if (dif >= probDif) {
						i = i - 1;
						break;
					}
					probDif = dif;
				}
				inf = 0;
			}
			corte = i;
			this.shannonFano(limiteInf, limiteSup, corte, simbolos);
			iniciaShannon(limiteInf, i, simbolos);
			iniciaShannon(i + 1, limiteSup, simbolos);
		}

	}
	
	public double getTamArchivo(int arch) throws IOException{
		Path ruta;
		if(arch==1) {
			ruta= Paths.get("Argentina.txt");
			
		}else if(arch==2) {
			 ruta= Paths.get("Chino.txt");
		}
		else {
			ruta= Paths.get("imagen.raw");
		}
		return Files.size(ruta);
	}
	
	public double getTamComprimido(int arch) throws IOException {
		double cont=0;
		FileReader fr = null;
		BufferedReader br = null;
		int auxFile;
		if (arch == 1) {
			fr = new FileReader("ArgentinoShannonFano.Fan");
			br = new BufferedReader(fr);
		}else if (arch==2) {
			fr = new FileReader("ChinoShannonFano.Fan");
			br = new BufferedReader(fr);
		}else {
			fr = new FileReader("ImagenShannonFano.Fan");
			br = new BufferedReader(fr);
		
		}
		while ((auxFile=br.read())!=-1){
			cont++;
		}
		fr.close();
		br.close();
		return cont;
	}

	public void getTasaCompresion(double tamOrig,double tamComprimido,Escritura escribir) throws IOException {
		double n = tamOrig/tamComprimido;
		System.out.println("Tasa de compresión: "+n+":1");
		escribir.agregaResultado("Tasa de compresión: "+n+":1");
		escribir.cierraArchivo2();

	}
	
	public void shannonFano(int limiteInf, int limiteSup, int corte, List<SimboloShannon> simbolos) {
		int i;
		for (i = limiteInf; i <= limiteSup; i++) {
			if (i <= corte)
				simbolos.get(i).setCodigoShannon(simbolos.get(i).getCodigoShannon() + "1");
			else
				simbolos.get(i).setCodigoShannon(simbolos.get(i).getCodigoShannon() + "0");
		}
	}

	public void muestraShannon(List<SimboloShannon> simbolos,Escritura escribir) {
		for (int i = 0; i < simbolos.size(); i++) {
			System.out.println(simbolos.get(i).toString());
			escribir.agregaResultado(simbolos.get(i).toString());			
		}
		escribir.agregaResultado("---------------------------------------------------------");
	}

	public void compresionShannonFano(List<SimboloShannon> simbolos,int arch, Escritura escribir) throws IOException {
		String aux, auxLetra;
		int j;
		FileReader fr = null;
		BufferedReader br = null;
		int auxFile;
		if (arch == 1) {
			fr = new FileReader("Argentina.txt");
			br = new BufferedReader(fr);
		}else if (arch==2) {
			fr = new FileReader("Chino.txt");
			br = new BufferedReader(fr);
		}else {
			fr = new FileReader("imagen.raw");
			br = new BufferedReader(fr);
			br.readLine();
			br.readLine();
		}
		while((auxFile=br.read())!=-1) {
			if(auxFile!=13) {
				if(arch!=3) {
				if(auxFile==10) 
					auxLetra="enter";
				else {
				char auxChar=(char) auxFile;
				auxLetra=String.valueOf(auxChar);
				}
				j=0;
				while (j < simbolos.size() && !auxLetra.equals(simbolos.get(j).getSimbolo())) {
					j++;
				}
				escribir.agregaLinea(simbolos.get(j).getCodigoShannon());
				}
				else {
					if(auxFile!=10) {
						char auxChar=(char) auxFile;
						auxLetra=String.valueOf(auxChar);
						j=0;
						while (j < simbolos.size() && !auxLetra.equals(simbolos.get(j).getSimbolo())) {
							j++;
						}
						escribir.agregaLinea(simbolos.get(j).getCodigoShannon());
					}
				}
			}
		}
		escribir.cierraArchivo1();


	}

	
	public double getEntropia(List<SimboloShannon> simbolos,Escritura escribir) {
		double auxEntropia=0,auxProb;
		for (int i=0;i<simbolos.size();i++) {
			auxProb=simbolos.get(i).getProbabilidad();
			auxEntropia+=auxProb*Math.log(1/auxProb)/Math.log(2);
		}
		escribir.agregaResultado("Entropia del código: "+auxEntropia);
		return auxEntropia;
	}
	
	public double getLongMedia(List<SimboloShannon> simbolos,Escritura escribir) {
		double auxLM=0;
		for (int i=0;i<simbolos.size();i++) {
			auxLM+=simbolos.get(i).getProbabilidad()*simbolos.get(i).getCodigoShannon().length();
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
	

}
