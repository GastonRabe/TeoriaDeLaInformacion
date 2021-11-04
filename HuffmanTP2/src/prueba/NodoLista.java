package prueba;

public class NodoLista {
	
	private String simbolo;
	private double prob;
	private NodoLista siguiente;
	private NodoArbol arbol;
	
	public NodoLista() {
		this.setSiguiente(null);
	}
	public void setSiguiente (NodoLista siguiente) {
		this.siguiente=siguiente;
	}

	public NodoLista getSiguiente() {
		return this.siguiente;
	}

	public NodoArbol getArbol() {
		return arbol;
	}
	public void setArbol(NodoArbol arbol) {
		this.arbol = arbol;
	}
	public String getSimbolo() {
		return simbolo;
	}
	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}
	public double getProb() {
		return prob;
	}
	public void setProb(double prob) {
		this.prob = prob;
	}
	
	
}
