package ej25;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class EJ25 {
	private double entropia=0,longMedia=0;
	
	public double getEntropia(double[] probabilidades,int tamano) {
		for(int i=0; i<tamano;i++) {
			entropia+= (probabilidades[i]*(Math.log(probabilidades[i])/Math.log(2)));
		}
		
		return Math.abs(this.entropia);
		
	}
	public double getLongitudMedia(double[] probabilidades,ArrayList<String> simbolos) {
		String aux;
		for (int i=0; i<simbolos.size();i++) {
			aux= (String)simbolos.get(i);
			this.longMedia+= (probabilidades[i]*aux.length());
		}
		return this.longMedia;
	}
	
}
