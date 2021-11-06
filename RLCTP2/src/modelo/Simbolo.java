package modelo;

public class Simbolo {
	
	private String simbolo;
	private int frecuencia;
	private double probabilidad;
	
	public Simbolo (String simbolo) {
		this.simbolo=simbolo;
		this.frecuencia=1;
	}
	
	public void aumentaFrecuencia() {
		this.frecuencia++;
	}
	public void aumentaFrecUlt(int cantidad) {
		this.frecuencia+=cantidad;
	}

	public String getSimbolo() {
		return this.simbolo;
	}

	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}

	public int getFrecuencia() {
		return this.frecuencia;
	}

	public void setFrecuencia(int frecuencia) {
		this.frecuencia = frecuencia;
	}

	public double getProbabilidad() {
		return this.probabilidad;
	}

	public void setProbabilidad(double probabilidad) {
		this.probabilidad = probabilidad;
	}

	@Override
	public String toString() {
		return "Simbolo [simbolo=" + simbolo + ", frecuencia=" + frecuencia + ", probabilidad=" + probabilidad + "]";
	}
	
	
	
	
}
