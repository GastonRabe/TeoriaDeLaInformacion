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
	public double getTamComprimido() throws FileNotFoundException {
		double cont=0;
		String aux;
		//Scanner scan = new Scanner(new File("ImagenShannon.txt"));
		Scanner scan = new Scanner(new File("ArgentinoShannonFano.txt"));
		//Scanner scan = new Scanner(new File("ChinoShannonFano.txt"));
		//FALTAN LOS OTROS 2
		while(scan.hasNext()) {
			aux=scan.nextLine();
			for (int i = 0; i < aux.length(); i++) {
			cont++;
			}
		}
		return cont;
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

	public void muestraShannon(List<SimboloShannon> simbolos) {
		for (int i = 0; i < simbolos.size(); i++) {
			System.out.println(simbolos.get(i).toString());
		}
	}

	public void compresionShannonFano(List<SimboloShannon> simbolos) throws IOException {
		String aux, auxLetra;
		int j;
		Escritura escribir = new Escritura();
		/*Scanner scan = new Scanner(new File("imagen.raw"));
		scan.nextLine();
		scan.nextLine();*/
		Scanner scan = new Scanner(new File("Argentina.txt"));
		// Scanner scan = new Scanner(new File("Chino.txt"));
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
		escribir.cierraArchivo();

	}
	
	public double getEntropia(List<SimboloShannon> simbolos) {
		double auxEntropia=0,auxProb;
		for (int i=0;i<simbolos.size();i++) {
			auxProb=simbolos.get(i).getProbabilidad();
			auxEntropia+=auxProb*Math.log(1/auxProb)/Math.log(2);
		}
		return auxEntropia;
	}
	
	public double getLongMedia(List<SimboloShannon> simbolos) {
		double auxLM=0;
		for (int i=0;i<simbolos.size();i++) {
			auxLM+=simbolos.get(i).getProbabilidad()*simbolos.get(i).getCodigoShannon().length();
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
	

}
