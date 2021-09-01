package prueba;

import java.util.ArrayList;

public class Kraft {
	
	private int[] vectorR = new int[5];
	
	
	public Kraft() {
		for (int i=0;i<5;i++) {
			vectorR[i]=0;
		}
	}
	
	public String verifKraft(ArrayList simbolos) {
	String aux;
	int auxString,r=0;
	double rtaKraft=0;
	for(int i=0; i<simbolos.size();i++) {
		aux = (String) simbolos.get(i);
		for(int j=0; j<aux.length();j++) {
			auxString=Integer.parseInt(aux.substring(j, j+1)); // con esto voy tomando caracter a caracter y lo paso a int porque se que es un numero
			if(vectorR[auxString]==0) {
				vectorR[auxString]=1;
				r++;
			}
		}	
	}
	//LUEGO Q ES EL SIMBOLOS SIZE, R ES r, Y LAS LONGITUDES LAS TOMO OTRA VEZ, YA PUEDO SACAR KRAFT
	for(int i=0; i<simbolos.size();i++) {
		aux= (String)simbolos.get(i);
		rtaKraft+= Math.pow(r, -aux.length());
	}
	if(rtaKraft<=1) {
		return "Cumple con la desigualdad de Kraft: "+rtaKraft;
	}
	else
		return "No cumple con la desigualdad de Kraft: "+rtaKraft+" >= 1" ;
	
	}
	
	public String esInstantaneo(ArrayList simbolos) {
		String aux,aux2;
		int i=0,j=0,k=0;
	    boolean instantaneo = true;
	    if(instantaneo) {
		for(i=0; i<simbolos.size();i++) {
			if(instantaneo) {
			aux=(String)simbolos.get(i); // aux tiene una palabra la cual se va a comparar con los prefijos de las demas
			for(j=0;j<simbolos.size();j++) {
				aux2=(String)simbolos.get(j); // aux2 tiene la palabra con la cual se va a comparar aux
					if(instantaneo) {
						for(k=0;k<aux2.length();k++) {
							String prefijo = aux2.substring(0, Math.min(aux2.length(), aux2.length()-k)); // con esto voy tomando los prefijos de aux2
							if(aux.equalsIgnoreCase(prefijo) && i!=j) { // si son iguales y i!=j es decir que no son la misma palabra es decir aux=aux, ya esto me da falso
								instantaneo = false;
							}
						}
					}
				
				
			}
		}
	   }
	}
	    if(instantaneo) 
	    	return "El código es instantáneo";
	    else
	    	return "El código no es instantáneo";
	}

}
