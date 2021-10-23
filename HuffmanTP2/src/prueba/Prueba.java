package prueba;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Prueba {

	/**RECOMENDACIÓN, SI EL ENCODING POR DEFECTO NO ES UTF-8, APLICARLO PARA PODER MOSTRAR TANTO LOS CARACTERES ESPECIALES EN ESPAÑOL COMO LOS CARACTERES EN CHINO
	 * WINDOW -> PREFERENCES -> GENERAL -> CONTENT TYPE -> PARARSE SOBRE TEXT -> DEBAJO EN DEFAULT ENCODING TIPEAR UTF-8 -> APLICAR Y CERRAR
	 * OTRA OPCION: ALT+ENTER Y DE APARECER OTRO TIPO DE ENCODING, PINCHAR EN OTHER Y SELECCIONAR UTF-8
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		ArrayList<String> caracteres = new ArrayList();
		ArrayList<Frecuencia> frecuencia = new ArrayList();
		List<Simbolo> simbolos = new ArrayList<Simbolo>();
		ListaSimple l=new ListaSimple();
		NodoLista listaOrig,listaArbol;
		Huffman h =new Huffman();
		int pos;
		double cont = 0;
		String aux;
		//Scanner scan = new Scanner(new File("imagen.raw"));
		//Scanner scan = new Scanner(new File("Argentina.txt"));
		Scanner scan = new Scanner(new File("Chino.txt"));
		while (scan.hasNext()) {
			aux = scan.nextLine();
			for (int i = 0; i < aux.length(); i++) {
				cont++;
				if (caracteres.contains(String.valueOf(aux.charAt(i)))) {
					pos = busqueda(caracteres, String.valueOf(aux.charAt(i)));
					frecuencia.get(pos).aumentaFrec();
				} else {
					caracteres.add(String.valueOf(aux.charAt(i)));
					frecuencia.add(new Frecuencia());
				}

			}

		}
		for (int i = 0; i < caracteres.size(); i++) {
			
			simbolos.add(new Simbolo(caracteres.get(i),cont,frecuencia.get(i).getFrecuencia()));	
		}
		Collections.sort(simbolos);
		listaArbol=l.generaLista(simbolos);
		h.getHuffman(listaArbol.getArbol(),"");
			
	}

	private static int busqueda(ArrayList caracteres, String carac) {
		int i = 0;
		while (i < caracteres.size() && !caracteres.get(i).equals(carac))
			i++;
		return i;
	}

}
