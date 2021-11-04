package prueba;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Canal {

	private float[][] matP = new float[7][7];
	private float[][] matCondicional = new float[7][7];
	private float[][] matSucesoSim = new float[7][7];
	private float[] entrada = new float[7];
	private float[] salida = new float[7];
	private float entropiaPriori, equivocacion, infoMutua, entropiaSalida;
	private float entropiaCondicional, infoMutuaSimetrica, entropiaAfin;
	private int n = 0, m = 0;
	private boolean propiedadSimetrica;

	public void inicializaAtributos() {
		this.entropiaPriori = 0;
		this.equivocacion = 0;
		this.infoMutua = 0;
		this.entropiaSalida = 0;
		this.entropiaCondicional = 0;
		this.infoMutuaSimetrica = 0;
		this.entropiaAfin = 0;
		this.propiedadSimetrica = false;
		for (int i = 0; i < 7; i++) {
			this.salida[i] = 0;
			this.entrada[i] = 0;
		}
		this.n = 0;
		this.m = 0;
	}

	public void cargaDatos(int canal) throws CanalInexistenteException, IOException {

		Scanner scanner = null;
		try {
			if (canal == 1) {
				scanner = new Scanner(new File("Canal1.txt"));
			} else if (canal == 2) {
				scanner = new Scanner(new File("Canal2.txt"));
			} else if (canal == 3) {
				scanner = new Scanner(new File("Canal3.txt"));
			} else
				throw new CanalInexistenteException("No existe el canal deseado");
			this.n = scanner.nextInt();
			this.m = scanner.nextInt();
			for (int i = 0; i < this.n; i++) {
				for (int j = 0; j < this.m; j++)
					this.matP[i][j] = scanner.nextFloat();
			}

			for (int i = 0; i < this.n; i++)
				this.entrada[i] = scanner.nextFloat();

			for (int j = 0; j < this.m; j++) {
				for (int i = 0; i < this.n; i++)
					this.salida[j] += this.matP[i][j] * this.entrada[i];
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getMatCondicional() {
		int i, j;
		for (i = 0; i < this.n; i++)
			for (j = 0; j < this.m; j++)
				this.matCondicional[i][j] = this.matP[i][j] * this.entrada[i] / this.salida[j];

	}

	public void getEntropiaAfin() {

		int i, j;
		for (i = 0; i < this.n; i++)
			for (j = 0; j < this.m; j++) {
				if (this.matSucesoSim[i][j] != 0)
					this.entropiaAfin += this.matSucesoSim[i][j] * Math.log(1 / this.matSucesoSim[i][j]) / Math.log(2);
			}

	}

	public void getResultados2(Escritura escribir) throws IOException {
		int i, j;
		for (j = 0; j < this.m; j++)
			this.entropiaSalida += this.salida[j] * Math.log(1 / this.salida[j]) / Math.log(2);
		for (i = 0; i < this.n; i++)
			for (j = 0; j < this.m; j++) {
				if (this.matP[i][j] != 0)
					this.entropiaCondicional += this.entrada[i] * this.matP[i][j] * Math.log(1 / this.matP[i][j])
							/ Math.log(2);
			}
		this.infoMutuaSimetrica = this.entropiaSalida - this.entropiaCondicional;
		System.out.println("H(B) del canal: " + this.entropiaSalida);
		System.out.println("H(B/A) del canal: " + this.entropiaCondicional);
		System.out.println("I(B,A) del canal: " + this.infoMutuaSimetrica);

		escribir.agregaLinea("H(B) del canal: " + this.entropiaSalida);
		escribir.agregaLinea("H(B/A) del canal: " + this.entropiaCondicional);
		escribir.agregaLinea("I(B,A) del canal: " + this.infoMutuaSimetrica);

		// PROPIEDADES DEL CANAL
		if (this.infoMutua >= 0) {
			System.out.println("Cumple con la propiedad de p�rdida no nula de informaci�n I(A,B)>=0");
			escribir.agregaLinea("Cumple con la propiedad de p�rdida no nula de informaci�n I(A,B)>=0");
		}

		else {
			System.out.println("No cumple con la propiedad de p�rdida no nula de informaci�n I(A,B)>=0");
			escribir.agregaLinea("No cumple con la propiedad de p�rdida no nula de informaci�n I(A,B)>=0");
		}

		if (this.infoMutua == this.infoMutuaSimetrica || Math.abs(this.infoMutua - this.infoMutuaSimetrica) < 0.005) {
			System.out.println("Se cumple con la propiedad simetrica I(A,B)=I(B,A)");
			this.propiedadSimetrica = true;
			escribir.agregaLinea("Se cumple con la propiedad simetrica I(A,B)=I(B,A)");
		} else {
			System.out.println("No cumple con la propiedad simetrica I(A,B)=I(B,A)");
			escribir.agregaLinea("No cumple con la propiedad simetrica I(A,B)=I(B,A)");
		}

		if (this.propiedadSimetrica) { // ya al entrar en este if, a los else no deber�an de entrar nunca
			if (this.infoMutua == this.entropiaSalida - this.entropiaCondicional
					|| Math.abs(this.infoMutua - this.entropiaSalida + this.entropiaCondicional) < 0.005) {
				System.out.println("Se cumple I(A,B)=H(B)-H(B/A)");
				escribir.agregaLinea("Se cumple I(A,B)=H(B)-H(B/A)");
			}
			if (this.entropiaAfin == this.entropiaPriori + this.entropiaSalida - this.infoMutua || Math
					.abs(this.entropiaAfin - this.entropiaPriori - this.entropiaSalida + this.infoMutua) < 0.005) {
				System.out.println("Se cumple H(A,B)=H(A)+H(B)-I(A,B)");
				escribir.agregaLinea("Se cumple H(A,B)=H(A)+H(B)-I(A,B)");
			}
		}
		escribir.cierraArchivo();
	}

	public void getResultados(Escritura escribir) {
		int i, j;
		for (i = 0; i < this.n; i++)
			this.entropiaPriori += this.entrada[i] * Math.log(1 / this.entrada[i]) / Math.log(2);
		for (i = 0; i < this.n; i++)
			for (j = 0; j < this.m; j++) {
				if (this.matCondicional[i][j] != 0)
					this.equivocacion += this.matSucesoSim[i][j] * Math.log(1 / this.matCondicional[i][j])
							/ Math.log(2);
			}
		this.infoMutua = this.entropiaPriori - this.equivocacion;
		System.out.println("Entrop�a a priori del canal H(A): " + this.entropiaPriori);
		System.out.println("Equivocaci�n del canal H(A/B): " + this.equivocacion);
		System.out.println("Informaci�n mutua del canal I(A,B): " + this.infoMutua);
		System.out.println("Entrop�a Af�n del canal H(A,B): " + this.entropiaAfin);

		escribir.agregaLinea("Entrop�a a priori del canal H(A): " + this.entropiaPriori);
		escribir.agregaLinea("Equivocaci�n del canal H(A/B): " + this.equivocacion);
		escribir.agregaLinea("Informaci�n mutua del canal I(A,B): " + this.infoMutua);
		escribir.agregaLinea("Entrop�a Af�n del canal H(A,B): " + this.entropiaAfin);

	}

	public void getMatSucSimult() {

		int i, j;
		for (i = 0; i < this.n; i++)
			for (j = 0; j < this.m; j++)
				this.matSucesoSim[i][j] = this.matCondicional[i][j] * this.salida[j];
	}

}
