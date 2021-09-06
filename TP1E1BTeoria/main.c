#include <stdio.h>
#include <stdlib.h>

void muestraMat(float mat[][4],char simbolos[][3]);
void divideMatriz(float mat[][4],float frecuencias[]);

int main()
{
    char simbolos[4][3]={"00","01","10","11"},n[3];
    float frecuencia[4]={0},cantPalabras=0,probabilidades[4]={0},informacion=0,entropia,matrizCondicional [4][4]={0};
    int i,posUltSimbolo,bandera=0;
    FILE *arch;
    arch = fopen("anexo1.txt","r");
    fscanf(arch,"%2s",n);
        i=0;
        while(i<4 && !bandera){
            if(!strcmp(simbolos[i],n)){
                frecuencia[i]++;
                bandera=1;
                posUltSimbolo=i; //ESTA VARIABLE SIRVE PARA SABER CUAL FUE EL ULTIMO SIMBOLO EN APARECER (YA QUE TENGO LA MATRIZ Y LOS VECTORES CON INDICES CON SIGNIFICADO)
                // LUEGO MAS ADELANTE EN EL CODIGO (LINEA 33) SE VE BIEN EL USO
            }
            i++;
        }
     cantPalabras=1;
    while(!feof(arch)){
        fscanf(arch,"%2s",n);
        i=bandera=0;
        while(i<4 && !bandera){
           if(!strcmp(simbolos[i],n)){
                frecuencia[i]++;
                bandera=1;
                matrizCondicional[i][posUltSimbolo]++;
                posUltSimbolo=i;
            }
            i++;
        }
        cantPalabras++;
    }
    for (i=0;i<4;i++){
        probabilidades[i]=frecuencia[i]/cantPalabras;
        printf("Probabilidad palabra %s: %5.2f\n",simbolos[i],probabilidades[i]);
        informacion=log(1/probabilidades[i])/log(2);
        entropia+= probabilidades[i]*informacion;
    }
    printf("Entropia Total de la fuente: %5.2f\n",entropia);
    fclose(arch);
    divideMatriz(matrizCondicional,frecuencia);
    printf("\n");
    muestraMat(matrizCondicional,simbolos);
    return 0;
}



//PROCEDIMIENTO DONDE SE MUESTRA LA MATRIZ DE TRANSICIÓN Y TAMBIÉN SE CALCULA Y MUESTRA LA SUMA DE LAS COLUMNAS A VER SI DAN 1 (POR LO QUE VIMOS EN LA TEORIA)
void muestraMat(float mat[][4],char simbolos[][3]){
    int i,j;
    float sumaCol;
    printf("Matriz de Transicion:\n");
    printf("          00      01      10      11\n");
    for(i=0;i<4;i++){
        printf("%s\t",simbolos[i]);
        for(j=0;j<4;j++)
          printf("%5.3f\t",mat[i][j]);
        printf("\n");
    }
    printf("\nSumatoria Columnas:|");
    for(j=0;j<4;j++){
        sumaCol=0;
        for(i=0;i<4;i++)
            sumaCol+=mat[i][j];
        printf("%4.4f|",sumaCol);
    }
    printf("\n");

}

//PROCEDIMIENTO DONDE CALCULA LAS PROBABILIDADES CONDICIONALES, ES DECIR, POR EJEMPLO LA POSICION [0][1], PARA ESTE EJERCICIO SIGNIFICA LA PROBABILIDAD DE QUE OCURRA "00" (FILA 0) SI OCURRIÓ "01" (COLUMNA 1)


void divideMatriz(float mat[][4],float frecuencias[]){
   int i,j;
   for(i=0;i<4;i++)
     for(j=0;j<4;j++)
       mat[i][j]=(mat[i][j]/frecuencias[i]);
}
