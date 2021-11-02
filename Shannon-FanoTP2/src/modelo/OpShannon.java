package modelo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
	public double getTamComprimido(int arch) throws FileNotFoundException {
		double cont=0;
		String aux;
		Scanner scan=null;
		if(arch==1) {
			 scan = new Scanner(new File("ArgentinoShannonFano.Fan"));
		}else if(arch==2) {
			 scan = new Scanner(new File("ChinoShannonFano.Fan"));
		}
		else {
			 scan = new Scanner(new File("ImagenShannonFano.Fan"));
		}
		while(scan.hasNext()) {
			aux=scan.nextLine();
			for (int i = 0; i < aux.length(); i++) {
			cont++;
			}
		}
		return cont;
	}

	public void getTasaCompresion(double n,Escritura escribir) throws IOException {
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

	public void compresionShannonFano(List<SimboloShannon> simbolos,int arch, Escritura escribir) throws IOException, TipoInvalidoException {
		String aux, auxLetra;
		int j;
		Scanner scan;
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
		while (scan.hasNext()) {
			aux = scan.nextLine();
			for (int i = 0; i < aux.length(); i++) {
				j = 0;
				auxLetra = String.valueOf(aux.charAt(i));
				while (j < simbolos.size() && !auxLetra.equals(simbolos.get(j).getSimbolo())) {
					j++;
				}
				escribir.agregaLinea(simbolos.get(j).getCodigoShannon());

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
