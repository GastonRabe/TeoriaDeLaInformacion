#include <stdio.h>
#include <stdlib.h>

void muestraMat(float mat[][4],char simbolos[][3]);
void divideMatriz(float mat[][4],float frecuencias[]);
void devuelveValores(float matOriginal[][4],float matCalculos[][4]);
void muestraMat2(float mat[][4]);
void vectorEstacionario(float mat[][4],int paso,float vecEstacionario[]);
void muestraVector(float vec[]);
int ergodica(float mat[][4]);


int main()
{
    char simbolos[4][3]={"00","01","10","11"},n[3];
    float frecuencia[4]={0},cantPalabras=0,probabilidades[4]={0},informacion=0,entropia,matrizCondicional [4][4]={0},vecEstacionario[4];
    int i,posUltSimbolo,bandera=0,paso;
    FILE *arch;
    arch = fopen("anexo1.txt","r");
    fscanf(arch,"%2s",n);
        i=0;
        while(!bandera){
            if(!strcmp(simbolos[i],n)){
                frecuencia[i]++;
                bandera=1;
                posUltSimbolo=i; //ESTA VARIABLE SIRVE PARA SABER CUAL FUE EL ULTIMO SIMBOLO EN APARECER (YA QUE TENGO LA MATRIZ Y LOS VECTORES CON INDICES CON SIGNIFICADO)
                // LUEGO MAS ADELANTE EN EL CODIGO SE VE BIEN EL USO
            }
            i++;
        }
     cantPalabras=1;
    while(!feof(arch)){
        fscanf(arch,"%2s",n);
        i=bandera=0;
        while(!bandera){
           if(!strcmp(simbolos[i],n)){
                frecuencia[i]++;
                bandera=1;
                //matrizCondicional[i][posUltSimbolo]++;
                matrizCondicional[posUltSimbolo][i]++;
                posUltSimbolo=i;
            }
            i++;
        }
        cantPalabras++;
    }
    for (i=0;i<4;i++){
        probabilidades[i]=frecuencia[i]/cantPalabras;
        printf("Probabilidad palabra %s: %5.3f\n",simbolos[i],probabilidades[i]);
        informacion=log(1/probabilidades[i])/log(2);
        entropia+= probabilidades[i]*informacion;
    }
    fclose(arch);
    divideMatriz(matrizCondicional,frecuencia);
    printf("\n");
    muestraMat(matrizCondicional,simbolos);
    printf("\n");
    if(ergodica(matrizCondicional)){
        printf("Es una fuente markoviana ergodica \n");
        printf("Ingrese pasos de iteracion para el vector estacionario\n");
        scanf("%d",&paso);
        vectorEstacionario(matrizCondicional,paso,vecEstacionario);
        muestraVector(vecEstacionario);
    }
    printf("Entropia Total de la fuente: %5.3f\n",entropia);
    return 0;
}

//UNA VEZ HECHOS TODOS LOS CALCULOS HASTA UN PASO SE GUARDAN LOS VALORES EN LA MATRIZ ORIGINAL

void devuelveValores(float matOriginal[][4],float matCalculos[][4]){
    int i,j;
    for (i=0;i<4;i++)
        for(j=0;j<4;j++)
          matOriginal[i][j]= matCalculos[i][j];
}

//ACA SE MUESTRA EL VECTOR ESTACIONARIO YA ARMADO

void muestraVector(float vec[]){
    int i;
    printf("Vector Estacionario V*: |");
    for(i=0;i<4;i++)
        printf("%5.4f|",vec[i]);
    printf("\n");

}

//ACA SE ELEVA LA MATRIZ TANTOS PASOS COMO SE INGRESO

void vectorEstacionario(float mat[][4],int paso,float vecEstacionario[]){
    float matAux[4][4]={0},suma=0;
    int i,j,l,m,k,n;
        for(m=0;m<paso;m++){
            for(i=0;i<4;i++){
                for(j=0;j<4;j++){
                    for(l=0;l<4;l++){
                        suma+=mat[i][l]*mat[l][j];
                    }
                 matAux[i][j]=suma;
                 suma=0;
                }
            }
            devuelveValores(mat,matAux);
        }
    for (j=0;j<4;j++)
        vecEstacionario[j]=mat[j][0];
}

// PROCEDIMIENTO PARA VER SI ESTABA BIEN LA MATRIZ ELEVADA
void muestraMat2(float mat[][4]){
   int i,j;
    for(i=0;i<4;i++){
        for(j=0;j<4;j++)
          printf("%5.4f\t",mat[i][j]);
        printf("\n");
    }
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
          printf("%5.4f\t",mat[i][j]);
        printf("\n");
    }
    printf("\nSumatoria Columnas:|");
    for(j=0;j<4;j++){
        sumaCol=0;
        for(i=0;i<4;i++)
            sumaCol+=mat[i][j];
        printf("%4.3f|",sumaCol);
    }
    printf("\n");

}

//PROCEDIMIENTO DONDE CALCULA LAS PROBABILIDADES CONDICIONALES


void divideMatriz(float mat[][4],float frecuencias[]){
   int i,j;
   for(i=0;i<4;i++)
     for(j=0;j<4;j++)
       //mat[i][j]=(mat[i][j]/frecuencias[i]);
       mat[j][i]=(mat[j][i]/frecuencias[i]);
}

//VERIFICA QUE SEA UNA FUENTE MARKOVIANA ERGODICA
int ergodica(float mat[][4]){
    int i=0,j,resp=0;
    float aux;
    while(!resp && i<4){
        if(mat[i][i]==1){
            resp=1;
        }
        i++;
    }
    if(!resp){
        i=0;
        while(!resp && i<4){
            aux=0;
            j=0;
            while(j<4){
                if(j!=i)
                    aux+=mat[i][j];
                j++;
            }
            if(aux==0)
                resp=1;
            i++;
        }
        if(!resp)
            return 1;
        else
            return 0;
    }
    else
        return 0;
}
