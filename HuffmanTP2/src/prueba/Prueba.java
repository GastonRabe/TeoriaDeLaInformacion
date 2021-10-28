package prueba;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Prueba {

	/**RECOMENDACIÓN, SI EL ENCODING POR DEFECTO NO ES UTF-8, APLICARLO PARA PODER MOSTRAR TANTO LOS CARACTERES ESPECIALES EN ESPAÑOL COMO LOS CARACTERES EN CHINO
	 * WINDOW -> PREFERENCES -> GENERAL -> CONTENT TYPE -> PARARSE SOBRE TEXT -> DEBAJO EN DEFAULT ENCODING TIPEAR UTF-8 -> APLICAR Y CERRAR
	 * OTRA OPCION: ALT+ENTER Y DE APARECER OTRO TIPO DE ENCODING, PINCHAR EN OTHER Y SELECCIONAR UTF-8
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, TipoInvalidoException {
		comprimirArchivo(1);
		System.out.println("-----------------------------------------------------------------------------------");
		comprimirArchivo(2);
		System.out.println("-----------------------------------------------------------------------------------");
		comprimirArchivo(3);
	}
	public static void comprimirArchivo(int arch) throws IOException,TipoInvalidoException {
		ArrayList<String> caracteres = new ArrayList();
		ArrayList<Frecuencia> frecuencia = new ArrayList();
		List<Simbolo> simbolos = new ArrayList<Simbolo>();
		ListaSimple l=new ListaSimple();
		NodoLista listaOrig,listaArbol;
		Huffman h =new Huffman();
		Escritura escribir;
		int pos;
		String aux;
		double contOrig = 0,entropia,longMedia,N;
		Scanner scan;
		if(arch==1) {
			 scan = new Scanner(new File("Argentina.txt"));
			 escribir = new Escritura(arch);
		}
		else if(arch==2) {
			 scan = new Scanner(new File("Chino.txt"));
			 escribir = new Escritura(arch);
		}
		else if (arch==3){
			 scan = new Scanner(new File("imagen.raw"));
			scan.nextLine();scan.nextLine();
			escribir = new Escritura(arch);
		}
		else throw new TipoInvalidoException("No existe el archivo deseado");
		while (scan.hasNext()) {
			aux = scan.nextLine();
			for (int i = 0; i < aux.length(); i++) {
				contOrig++;
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
			
			simbolos.add(new Simbolo(caracteres.get(i),contOrig,frecuencia.get(i).getFrecuencia()));	
		}
		Collections.sort(simbolos);
		listaArbol=l.generaLista(simbolos);
		h.getHuffman(listaArbol.getArbol(),"",simbolos);
		for(int i=0;i<simbolos.size();i++) {
			System.out.println(simbolos.get(i).toString());
			escribir.agregaResultado(simbolos.get(i).toString());
		}
		h.compresionHuffman(listaArbol,arch,escribir);
		
		entropia=h.getEntropia(simbolos,escribir);
		longMedia=h.getLongMedia(escribir);
		scan.close();
		
		System.out.println("Entropia del código: "+entropia);
		System.out.println("Longitud media del código: "+longMedia);
		h.getRendimientoRedundancia(entropia, longMedia,escribir);
		N=contOrig/h.getTamComprimido(arch);
		h.getTasaCompresion(N,escribir);
		System.out.println("Tasa de compresión: "+N+":1");
	}

	private static int busqueda(ArrayList caracteres, String carac) {
		int i = 0;
		while (i < caracteres.size() && !caracteres.get(i).equals(carac))
			i++;
		return i;
	}

}