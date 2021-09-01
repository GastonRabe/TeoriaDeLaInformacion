package prueba;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
import ej25.EJ25;

public class Prueba {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner scan = new Scanner(System.in);
		ArrayList <String> simbolos = new ArrayList<String>();
		Kraft k1 = new Kraft();
		EJ25 rta= new EJ25();
		DecimalFormat df = new DecimalFormat("#.000");
		double entropia,longMedia;
		
		System.out.println("Ingrese cantidad de símbolos");
		int cantSimbolos = scan.nextInt();
		scan.nextLine();
		for(int i=0;i<cantSimbolos;i++) {
			System.out.println("Ingrese secuencia para armar la palabra");
			String aux = scan.nextLine();
			simbolos.add(aux);
		}
		System.out.println(k1.verifKraft(simbolos));
		System.out.println(k1.esInstantaneo(simbolos));
		double[] probabilidades = new double[cantSimbolos];
		for(int i=0; i<cantSimbolos;i++) {
			System.out.println("Ingrese probabilidad");
			probabilidades[i]= scan.nextDouble();
		}
		
		entropia = rta.getEntropia(probabilidades,cantSimbolos);
		longMedia = rta.getLongitudMedia(probabilidades, simbolos);
		System.out.println("Entropia del código: "+entropia);
		System.out.println("Longitud Media del código: "+df.format(longMedia));
		//YO NO PUEDO ASEGURAR QUE UN CÓDIGO ES COMPACTO PORQUE NO PUEDO ASEGURAR QUE EL CÓDIGO INGRESADO ES EL ÚNICO PERO PUEDO VER LOS CANDIDATOS 
		//EN EL CASO DE QUE SE CUMPLA QUE LA ENTROPIA ES <= A LA LONGITUD MEDIA
		if(entropia <=longMedia) 
			System.out.println("El código es candidato a ser compacto");
		else
			System.out.println("El código no es candidato a ser compacto");
		
		
	}

}
