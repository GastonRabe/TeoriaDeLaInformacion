package modelo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Escritura {
	private File f;
	private File f2;
	private FileWriter w;
	private FileWriter w2;
	private BufferedWriter bw;
	private BufferedWriter bw2;
	private PrintWriter wr;
	private PrintWriter wr2;
	
	public Escritura(int arch) throws IOException {
		if (arch==1) {
			this.f=new File("AuxiliarArgentinoShannonFano.Fan");
			this.f2=new File("ResultadosArgentinoShannonFano.txt");
		}
		else if(arch==2) {
			this.f=new File("AuxiliarChinoShannonFano.Fan");
			this.f2=new File("ResultadosChinoShannonFano.txt");
		}
		else {
			this.f=new File("AuxiliarImagenShannonFano.Fan");
			this.f2=new File("ResultadosImagenShannonFano.txt");
		}
		this.w = new FileWriter(this.f);
		this.bw= new BufferedWriter(this.w);
		this.wr= new PrintWriter(this.bw);		
		this.w2 = new FileWriter(this.f2);
		this.bw2= new BufferedWriter(this.w2);
		this.wr2= new PrintWriter(this.bw2);	
	}
	

	public void agregaLinea(String linea) {
		this.wr.write(linea);
	}
	
	public void agregaResultado(String linea) {
		this.wr2.write(linea+"\n");
	}
	public void cierraArchivo1() throws IOException {
		this.wr.close();
		this.bw.close();
		
	}
	public void cierraArchivo2() throws IOException {
		this.wr2.close();
		this.bw2.close();
	}
	
}
