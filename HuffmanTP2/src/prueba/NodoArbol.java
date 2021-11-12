package prueba;

/** CLASE NODOARBOL YA QUE SE VA A TRASPASAR LO HECHO EN EL TP1 A JAVA, CON EL USO DE LISTAS Y ARBOLES TAL CUAL IMPLEMENTADO EN C
 * @author usuario
 *
 */
public class NodoArbol {
	private String codigoBinario,simbolo;
	double prob;
	private NodoArbol izq,der,padre;
	
	public NodoArbol() {
		this.izq = null;
		this.der = null;
		this.padre = null;
	}

	public String getSimbolo() {
		return this.simbolo;
	}

	public void setSimbolo(String string) {
		this.simbolo = string;
	}

	public String getCodigoBinario() {
		return codigoBinario;
	}

	public void setCodigoBinario(String codigoBinario) {
		this.codigoBinario = codigoBinario;
	}

	public double getProb() {
		return prob;
	}

	public void setProb(double prob) {
		this.prob = prob;
	}

	public NodoArbol getIzq() {
		return izq;
	}

	public void setIzq(NodoArbol izq) {
		this.izq = izq;
	}

	public NodoArbol getDer() {
		return der;
	}

	public void setDer(NodoArbol der) {
		this.der = der;
	}

	public NodoArbol getPadre() {
		return padre;
	}

	public void setPadre(NodoArbol padre) {
		this.padre = padre;
	}
	
	
}
