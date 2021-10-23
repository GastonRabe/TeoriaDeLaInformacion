package Codigo;

public class Codigo {
	
	private static float[][] Canal = new float[10][10];
	private static float[] Prob = new float[10];

	//public void canal2(float[][] Canal; float[] Prob)
	public void canal2(float[][] Canal,float[] Prob)
		{

	/*private*/ float suma = 0;
	/*private*/ float ProbabilidadError = 0;
	/*private*/ Integer N = 3; /* Filas */
	/*private*/ Integer M = 3; /* Columnas */
	/*private*/ Integer posicion_Maximo_columna = 0;
	/*private*/ float maximo_columna = -99999;
	for(int j = 1;j<=M;j++)
	{
		suma = 0;
		posicion_Maximo_columna = 0;
		maximo_columna = -99999;
		for (int i = 1; i <= N; i++) {
			if (Canal[i][j] > maximo_columna) {
				maximo_columna = Canal[i][j];
				posicion_Maximo_columna = i;
			}
		}
		for (int i = 1; i <= N; i++) {
			if (i != posicion_Maximo_columna) {
				suma = suma + Canal[i][j] * Prob[i];
			}
		}
		ProbabilidadError = ProbabilidadError + suma;
	}System.out.println(ProbabilidadError);
}

}