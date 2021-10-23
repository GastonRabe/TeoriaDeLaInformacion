package prueba;

public class Huffman {

	
	
	public void getHuffman(NodoArbol arbol,String binario) {
		String auxBinario="";
		if(arbol!=null) {
			if(arbol.getPadre()!=null) {
				auxBinario=arbol.getPadre().getCodigoBinario()+binario;
				arbol.setCodigoBinario(auxBinario);
			}
			this.getHuffman(arbol.getIzq(),"0");
			if(arbol.getSimbolo().length()==1) {
			 if(arbol.getSimbolo().equals(" "))
					arbol.setSimbolo("espacio");
			System.out.println("Simbolo: "+arbol.getSimbolo()+" Código Huffman: "+arbol.getCodigoBinario());
			}
			this.getHuffman(arbol.getDer(), "1");
		}
		
	}
}
