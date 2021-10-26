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
			this.f=new File("ArgentinoRLC.RLC");
			this.f2=new File("ArgentinoRLC.txt");
		}
		else if(arch==2) {
			this.f=new File("ChinoRLC.RLC");
			this.f2=new File("ChinoRLC.txt");
		}
		else {
			this.f=new File("ImagenRLC.RLC");
			this.f2=new File("ImagenRLC.txt");
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
		this.wr2.write(linea);
	}
	public void cierraArchivo() throws IOException {
		this.wr.close();
		this.bw.close();
		this.wr2.close();
		this.bw2.close();
	}
}
