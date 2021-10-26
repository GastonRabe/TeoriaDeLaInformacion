package modelo;

import java.io.File;
import java.io.FileNotFoundException;
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
		Scanner compresion = new Scanner(System.in);
		int arch;
		System.out.println("Ingrese archivo a comprimir 1(Argentino) / 2(Chino) / 3 (Imagen)");
		arch=compresion.nextInt();
		comprimirArchivo(arch);
		compresion.close();
	}
	public static void comprimirArchivo(int arch) throws TipoInvalidoException, IOException {
		ArrayList<String> caracteres = new ArrayList();
		ArrayList<Frecuencia> frecuencia = new ArrayList();
		List<SimboloShannon> simbolos = new ArrayList<SimboloShannon>();
		OpShannon o = new OpShannon(); //aca se invocan los metodos para obtener shannon
		int pos;
		double contOrig = 0,entropia,longMedia,tasaCompresion;
		String aux;
		Scanner scan;
		if(arch==1) {
			 scan = new Scanner(new File("Argentina.txt"));
		}
		else if(arch==2) {
			 scan = new Scanner(new File("Chino.txt"));
		}
		else if (arch==3){
			 scan = new Scanner(new File("imagen.raw"));
			scan.nextLine();scan.nextLine();
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
			
			simbolos.add(new SimboloShannon(caracteres.get(i),contOrig,frecuencia.get(i).getFrecuencia()));	
		}
		Collections.sort(simbolos);
		o.iniciaShannon(0,simbolos.size()-1,simbolos);
		o.muestraShannon(simbolos);
		o.compresionShannonFano(simbolos,arch);
		entropia=o.getEntropia(simbolos);
		longMedia=o.getLongMedia(simbolos);
		System.out.println("Entropia del código: "+entropia);
		System.out.println("Longitud media del código: "+longMedia);
		o.getRendimientoRedundancia(entropia, longMedia);
		tasaCompresion=contOrig/o.getTamComprimido(arch);
		System.out.println("Tasa de compresión: "+tasaCompresion+":1");
	}

	private static int busqueda(ArrayList caracteres, String carac) {
		int i = 0;
		while (i < caracteres.size() && !caracteres.get(i).equals(carac))
			i++;
		return i;
	}
}