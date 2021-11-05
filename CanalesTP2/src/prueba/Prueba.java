package prueba;

import java.io.IOException;

public class Prueba {

	public static void main(String[] args) throws CanalInexistenteException, IOException {

		Canal c = new Canal();
		c.inicializaAtributos();
		Escritura escribir=new Escritura(1);
		c.cargaDatos(1);
		c.getMatCondicional();
		c.getMatSucSimult();
		c.getEntropiaAfin();
		c.muestras(escribir);
		c.getResultados(escribir);		
		c.getResultados2(escribir);
		System.out.println("--------------------------------------------------------------------------------------------------");
		c.inicializaAtributos();
		escribir=new Escritura(2);
		c.cargaDatos(2);
		c.getMatCondicional();
		c.getMatSucSimult();
		c.getEntropiaAfin();
		c.muestras(escribir);
		c.getResultados(escribir);		
		c.getResultados2(escribir);
		System.out.println("--------------------------------------------------------------------------------------------------");
		c.inicializaAtributos();
		escribir=new Escritura(3);
		c.cargaDatos(3);
		c.getMatCondicional();
		c.getMatSucSimult();
		c.getEntropiaAfin();
		c.muestras(escribir);
		c.getResultados(escribir);		
		c.getResultados2(escribir);


	}

}
