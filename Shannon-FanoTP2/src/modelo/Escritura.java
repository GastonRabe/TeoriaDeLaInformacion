package modelo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Escritura {
	
	private File f;
	private FileWriter w;
	private BufferedWriter bw;
	private PrintWriter wr;
	
	public Escritura() throws IOException {
		this.f=new File("ArgentinoShannonFano.txt");
		//this.f=new File("ChinoShannonFano.txt");
		//this.f=new File("ImagenShannonFano.txt");
		this.w = new FileWriter(this.f);
		this.bw= new BufferedWriter(this.w);
		this.wr= new PrintWriter(this.bw);		
	}

	public void agregaLinea(String linea) {
		this.wr.write(linea);
	}
	public void cierraArchivo() throws IOException {
		this.wr.close();
		this.bw.close();
	}
}
