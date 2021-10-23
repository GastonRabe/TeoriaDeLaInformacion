package prueba;

public class Simbolo implements Comparable <Simbolo>{
	private String simbolo;
	private double probabilidad;
	
	public Simbolo(String simbolo, double cont,double frec) {
		this.simbolo=simbolo;
		this.probabilidad=frec/cont;
	}

	@Override
	public String toString() {
		return "Simbolo [simbolo=" + simbolo + ", probabilidad=" + probabilidad + "]";
	}

	public String getSimbolo() {
		return simbolo;
	}

	public double getProbabilidad() {
		return probabilidad;
	}

	@Override
	public int compareTo(Simbolo o) {
		if(this.probabilidad>o.getProbabilidad())
			return 1;
		else
			if(this.probabilidad==o.getProbabilidad())
				return 0;
			else
				return -1;
	}

	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}

	public void setProbabilidad(double probabilidad) {
		this.probabilidad = probabilidad;
	}
	
	
}
