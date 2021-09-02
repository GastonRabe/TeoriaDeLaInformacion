#include <stdio.h>
#include <stdlib.h>
#define ascii 48

void buscaPalabras_five(char palabras[][6],int *bandera,char cadena[],int *contPalabrasDist);;
void buscaPalabras_seven(char palabras[][8],int *bandera,char cadena[],int *contPalabrasDist);
void buscaPalabras_nine(char palabras[][10],int *bandera,char cadena[],int *contPalabrasDist);
void five_pals ();
void seven_pals ();
void nine_pals ();


int main()
{
    five_pals();
    seven_pals();
    nine_pals();
    return 0;
}

void buscaPalabras_five(char palabras[][6],int *bandera,char cadena[],int *contPalabrasDist){
    int i=0;
    while(strcmp(palabras[i]," ") && strcmp(palabras[i],cadena)) // mientras no se caiga y sea distinto de '' es decir que ya hay una palabra
        i++;
    if(!strcmp(palabras[i]," ")){
            strcpy(palabras[i],cadena);
            (*contPalabrasDist)++;
        }
    *bandera=i;
}
void buscaPalabras_seven(char palabras[][8],int *bandera,char cadena[],int *contPalabrasDist){
    int i=0;
    while(strcmp(palabras[i]," ") && strcmp(palabras[i],cadena)) // mientras no se caiga y sea distinto de '' es decir que ya hay una palabra
        i++;
    if(!strcmp(palabras[i]," ")){
            strcpy(palabras[i],cadena);
            (*contPalabrasDist)++;
        }
    *bandera=i;
}
void buscaPalabras_nine(char palabras[][10],int *bandera,char cadena[],int *contPalabrasDist){
    int i=0;
    while(strcmp(palabras[i]," ") && strcmp(palabras[i],cadena)) // mientras no se caiga y sea distinto de '' es decir que ya hay una palabra
        i++;
    if(!strcmp(palabras[i]," ")){
            strcpy(palabras[i],cadena);
            (*contPalabrasDist)++;
        }
    *bandera=i;
}


void five_pals(){
    char n[6],palabras[40][6];
    float frecuenciaPalabras[40]={0},probPalabras[40]={0},totalPalabras=0,informacion=0,entropia=0;
    int contPalabrasDist=0,bandera=0;
    for(int i=0; i<40;i++){
        strcpy(palabras[i]," ");
    }
    FILE *arch;
    arch=fopen("anexo1.txt","r");
    while(!feof(arch)){
        fscanf(arch,"%5s",n); //leo hasta 5 digitos
         buscaPalabras_five(palabras,&bandera,n,&contPalabrasDist); //busca la palabra, si la encuentra devuelve la posicion donde estaba para aumentar la frecuencia, si no la encuentra, la agrega y hace lo mismo
         frecuenciaPalabras[bandera]++;
         totalPalabras++;
    }
    for(int i=0;i<contPalabrasDist;i++){
        probPalabras[i]=(frecuenciaPalabras[i]/totalPalabras);
        informacion+=log(1/probPalabras[i])/log(2);
        entropia+=(probPalabras[i]*informacion);
    }
    printf("Fuente con palabras de 5 digitos:\n");
    printf("Informacion total de la fuente: %10.5f (en bits)\n",informacion);
    printf("Entropia total de la fuente: %10.5f (en bits)\n",entropia);
    printf("---------------------------------------------------------------\n");
    fclose(arch);
}

void seven_pals (){
    char n[8],palabras[130][8];
    float frecuenciaPalabras[130]={0},probPalabras[130]={0},totalPalabras=0,informacion=0,entropia=0;
    int contPalabrasDist=0,bandera=0;
    for(int i=0; i<130;i++){
        strcpy(palabras[i]," ");
    }
    FILE *arch;
    arch=fopen("anexo1.txt","r");
    while(!feof(arch)){
        fscanf(arch,"%7s",n); //leo hasta 7 digitos
         buscaPalabras_seven(palabras,&bandera,n,&contPalabrasDist); //busca la palabra, si la encuentra devuelve la posicion donde estaba para aumentar la frecuencia, si no la encuentra, la agrega y hace lo mismo
         frecuenciaPalabras[bandera]++;
         totalPalabras++;
    }
    for(int i=0;i<contPalabrasDist;i++){
        probPalabras[i]=frecuenciaPalabras[i]/totalPalabras;
        informacion+=log(1/probPalabras[i])/log(2);
        entropia+=(probPalabras[i]*informacion);
    }
    printf("Fuente con palabras de 7 digitos:\n");
    printf("Informacion total de la fuente: %10.5f (en bits)\n",informacion);
    printf("Entropia total de la fuente: %10.5f (en bits)\n",entropia);
    printf("---------------------------------------------------------------\n");
    fclose(arch);
}

void nine_pals (){
    char n[10],palabras[520][10];
    float frecuenciaPalabras[520]={0},probPalabras[520]={0},totalPalabras=0,informacion=0,entropia=0;
    int contPalabrasDist=0,bandera=0;
    for(int i=0; i<520;i++){
        strcpy(palabras[i]," ");
    }
    FILE *arch;
    arch=fopen("anexo1.txt","r");
    while(!feof(arch)){
        fscanf(arch,"%9s",n); //leo hasta 9 digitos
         buscaPalabras_nine(palabras,&bandera,n,&contPalabrasDist); //busca la palabra, si la encuentra devuelve la posicion donde estaba para aumentar la frecuencia, si no la encuentra, la agrega y hace lo mismo
         frecuenciaPalabras[bandera]++;
         totalPalabras++;
    }
    for(int i=0;i<contPalabrasDist;i++){
        probPalabras[i]=frecuenciaPalabras[i]/totalPalabras;
        informacion+=log(1/probPalabras[i])/log(2);
        entropia+=(probPalabras[i]*informacion);
    }
    printf("Fuente con palabras de 9 digitos:\n");
    printf("Informacion total de la fuente: %5.2f (en bits)\n",informacion);
    printf("Entropia total de la fuente: %5.2f (en bits)\n",entropia);
    printf("---------------------------------------------------------------\n");
    fclose(arch);
}
