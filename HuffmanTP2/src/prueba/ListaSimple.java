package prueba;

import java.util.List;

/** EN ESTA CLASE SE VA A GENERAR UNA LISTA SIMPLE Y A SU VEZ EL ARBOL DE HUFFMAN, ESTE ARBOL VA A ESTAR ALMACENADO EN UN NODO DE LISTA SIMPLE
 * @author usuario
 *
 */
/**
 * @author usuario
 *
 */
public class ListaSimple {
	
	private NodoLista cabecera;
	
	public ListaSimple() {
		this.cabecera=null;
	}

	public NodoLista getCabecera() {
		return this.cabecera;
	}

	public void setCabecera(NodoLista cabecera) {
		this.cabecera = cabecera;
	}
	
	public NodoLista generaLista(List<Simbolo> arreglo) {
		int i;
		char auxSimb;
		double auxProb;
		NodoLista listaArbol;
		NodoLista nuevo,act=this.cabecera;
		NodoArbol nuevoArbol;
		for(i=0;i<arreglo.size();i++) {
		nuevo=new NodoLista();
		nuevo.setSimbolo(arreglo.get(i).getSimbolo());
		nuevo.setProb(arreglo.get(i).getProbabilidad());
		nuevoArbol=new NodoArbol();
		nuevoArbol.setProb(nuevo.getProb());
		nuevoArbol.setSimbolo(nuevo.getSimbolo());
		nuevoArbol.setCodigoBinario("");
		nuevo.setArbol(nuevoArbol);
			if(this.getCabecera()==null) {
				nuevo.setSiguiente(this.getCabecera());
				this.setCabecera(nuevo);
			}
			else 
				act.setSiguiente(nuevo);
			act=nuevo;
		}

		listaArbol=this.generaArbol(this.cabecera);
		return listaArbol;
	
	}
	
	
	/**METODO PARA FUSIONAR LOS NODOS DE LISTA QUE TIENEN LA MENOR PROBABILIDAD EN TAL MOMENTO
	 * @param ant
	 * @param act
	 * @return
	 */
	public NodoArbol fusionNodo(NodoLista ant,NodoLista act) {
		NodoArbol aux = new NodoArbol();
		aux.setSimbolo(ant.getSimbolo()+act.getSimbolo());
		aux.setCodigoBinario("");
		aux.setIzq(ant.getArbol());
		aux.setDer(act.getArbol());
		aux.setProb(ant.getProb()+act.getProb());
		act.getArbol().setPadre(aux);
		ant.getArbol().setPadre(aux);
		return aux;
	}
	
	public NodoLista generaArbol(NodoLista cabecera) {
		NodoLista ant=cabecera,act=ant.getSiguiente(),elim1,elim2,nuevo;
		NodoArbol nuevoArbol;
		if(act==null) {
			ant.getArbol().setCodigoBinario("0");
		}
		else {
			while(ant!=null) {
			elim1=ant;
			elim2=act;
			nuevoArbol=fusionNodo(ant,act);
			if(act.getSiguiente()!=null)
				cabecera=act.getSiguiente();
			nuevo = new NodoLista();
			nuevo.setArbol(nuevoArbol);
			nuevo.setProb(nuevoArbol.getProb());
			nuevo.setSimbolo(nuevoArbol.getSimbolo());
			if(cabecera.getProb()>nuevo.getProb()) {
				nuevo.setSiguiente(cabecera);
				cabecera=nuevo;
			}
			else {
				ant=cabecera;
				act=ant.getSiguiente();
				while(act!=null && act.getProb()<nuevo.getProb()) {
					ant=act;
					act=act.getSiguiente();
				}
				ant.setSiguiente(nuevo);
				nuevo.setSiguiente(act);
			}
			elim1=null;elim2=null;
			if(nuevo.getSiguiente()==null && (nuevo.getProb()==1 || Math.abs(1-nuevo.getProb())<0.00005)) {
			ant=null;
			cabecera=nuevo;
			}
			else {
				ant=cabecera;
				act=ant.getSiguiente();
			}
		}
		
		}

		return cabecera;
	}
	
	
}
