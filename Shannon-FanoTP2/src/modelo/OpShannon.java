package modelo;

import java.util.List;

public class OpShannon {

	public void iniciaShannon(int limiteInf,int limiteSup,List<SimboloShannon> simbolos) {
		if(limiteInf!=limiteSup){
			int i,j,corte=0;
			float inf=0,sup =0,dif = 0,probDif = 0;
			for(i=limiteInf;i<=limiteSup;i++)
			{
				sup+=simbolos.get(i).getProbabilidad();
				for(j=i+1;j<=limiteSup;j++){
					inf+=simbolos.get(j).getProbabilidad();
				}
				dif = (sup-inf)*(sup-inf);
				if(inf==sup){
					break;
				}
				if(i ==limiteInf){
					probDif  = dif;
				}
				else{
					if(dif>=probDif)
						{
						i=i-1;
						break;
						}
					probDif = dif;
					}
				inf=0;
			}
			corte = i;
	        this.shannonFano(limiteInf,limiteSup,corte,simbolos);
			iniciaShannon(limiteInf,i,simbolos);
			iniciaShannon(i+1,limiteSup,simbolos);
		}
		
	}
	public void shannonFano(int limiteInf,int limiteSup,int corte,List<SimboloShannon> simbolos) {
		int i;
		for(i=limiteInf;i<=limiteSup;i++){
			if(i<=corte)
	            simbolos.get(i).setCodigoShannon(simbolos.get(i).getCodigoShannon()+"1");
			else
				simbolos.get(i).setCodigoShannon(simbolos.get(i).getCodigoShannon()+"0");
		}
	}
}
