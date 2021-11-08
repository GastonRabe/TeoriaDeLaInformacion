package modelo;

import java.util.List;

public class SimboloShannon implements Comparable <SimboloShannon>{
	
	private String simbolo,codigoShannon;
	private double probabilidad;
	
	public SimboloShannon(String simbolo, double cont,double frec) {
		this.simbolo=simbolo;
		this.probabilidad=frec/cont;
		this.codigoShannon="";
	}
	

	public String getSimbolo() {
		return this.simbolo;
	}

	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}

	public String getCodigoShannon() {
		return codigoShannon;
	}

	public void setCodigoShannon(String codigoShannon) {
		this.codigoShannon = codigoShannon;
	}

	public double getProbabilidad() {
		return probabilidad;
	}

	public void setProbabilidad(double probabilidad) {
		this.probabilidad = probabilidad;
	}

	@Override
	public int compareTo(SimboloShannon o) {
		if(this.probabilidad>o.getProbabilidad())
			return 1;
		else
			if(this.probabilidad==o.getProbabilidad())
				return 0;
			else
				return -1;
	}


	@Override
	public String toString() {
		return "SimboloShannon [simbolo=" + simbolo + ", codigoShannon=" + codigoShannon + "]";
	}




	
	
}
