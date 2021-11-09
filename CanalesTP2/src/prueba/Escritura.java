package prueba;

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

	
	public Escritura(int arch) throws IOException {
		if (arch==1) {
			this.f=new File("ResultadosCanal1.txt");
		
		}
		else if(arch==2) {
			this.f=new File("ResultadosCanal2.txt");
		}
		else {
			this.f=new File("ResultadosCanal3.txt");
		}
		this.w = new FileWriter(this.f);
		this.bw= new BufferedWriter(this.w);
		this.wr= new PrintWriter(this.bw);		
	}
	

	public void agregaLinea(String linea) {
		this.wr.write(linea+"\n");
	}
	public void agregaMat(String linea) {
		this.wr.write(linea);
	}
	

	public void cierraArchivo() throws IOException {
		this.wr.close();
		this.bw.close();
		
	}

}
