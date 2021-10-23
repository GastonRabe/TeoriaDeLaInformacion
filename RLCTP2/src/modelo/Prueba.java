package modelo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Prueba {

	public static void main(String[] args) throws FileNotFoundException {
		String letraActual="",rlc="",caracter,aux;
		int i=0,cont=0;
		Scanner scan = new Scanner(new File("arch.txt"));
		aux = String.valueOf(scan.nextLine());
		letraActual=String.valueOf(aux.charAt(0));
		cont=1;
		for(i=1;i<aux.length();i++) {
			caracter=String.valueOf(aux.charAt(i));
			if(caracter.toLowerCase().equalsIgnoreCase(letraActual.toLowerCase()))
				cont++;
			else {
				rlc+=(letraActual.toLowerCase()+String.valueOf(cont));
				letraActual=caracter;
				cont=1;
			}
		}
		while (scan.hasNext()) {
			aux = scan.nextLine();
			for (i = 0; i < aux.length(); i++) {
				caracter=String.valueOf(aux.charAt(i));
				caracter.toLowerCase();
				if(caracter.toLowerCase()==letraActual.toLowerCase())
					cont++;
				else {
					rlc+=(letraActual.toLowerCase()+String.valueOf(cont));
					letraActual=caracter;
					cont=1;
				}

			}

		}
		if(cont>0) {
			rlc+=(letraActual.toLowerCase()+String.valueOf(cont));
		}
		System.out.println("Compresión RLC: "+rlc);

	}

}
