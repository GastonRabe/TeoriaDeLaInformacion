#include <stdio.h>
#include <stdlib.h>
#define ascii 48


void buscaPalabras(char palabras[][10],int *bandera,char cadena[],int *cantPalabrasDist);
void five_pals ();
void seven_pals ();
void nine_pals ();
void longitudMedia(float *rta,float probabilidades[],int longitud,int cantPalabras);


int main()
{
    five_pals();
    seven_pals();
    nine_pals();
    return 0;
}

void longitudMedia(float *rta,float probabilidades[],int longitud,int cantPalabras){
    int i;
    for(i=0;i<cantPalabras;i++){
        (*rta)+=probabilidades[i]*longitud;
    }
}



void buscaPalabras(char palabras[][10],int *bandera,char cadena[],int *cantPalabrasDist){
    int i=0;
    while(strcmp(palabras[i]," ") && strcmp(palabras[i],cadena)) // mientras no se caiga y sea distinto de '' es decir que ya hay una palabra
        i++;
    if(!strcmp(palabras[i]," ")){
            strcpy(palabras[i],cadena);
            (*cantPalabrasDist)++;
        }
    *bandera=i;
}


void five_pals(){
    char n[10],palabras[32][10];
    float frecuenciaPalabras[32]={0},probPalabras[32]={0},totalPalabras=0,informacion=0,entropia=0,rtaLongMedia=0,rendimiento,redundancia;
    int cantPalabrasDist=0,bandera=0;
    for(int i=0; i<32;i++){
        strcpy(palabras[i]," ");
    }
    FILE *arch;
    arch=fopen("anexo1.txt","r");
    while(!feof(arch)){
        fscanf(arch,"%5s",n); //leo hasta 5 digitos
         buscaPalabras(palabras,&bandera,n,&cantPalabrasDist); //busca la palabra, si la encuentra devuelve la posicion donde estaba para aumentar la frecuencia, si no la encuentra, la agrega y hace lo mismo
         frecuenciaPalabras[bandera]++;
         totalPalabras++;
    }
    for(int i=0;i<cantPalabrasDist;i++){
        probPalabras[i]=(frecuenciaPalabras[i]/totalPalabras);
        informacion+=(log(1/probPalabras[i])/log(2));
        entropia+=(probPalabras[i]*log(1/probPalabras[i])/log(2));
    }
    printf("Fuente con palabras de 5 digitos:\n");
    printf("Informacion total de la fuente: %5.1f (en bits)\n",informacion);
    printf("Entropia total de la fuente: %5.1f (en bits)\n",entropia);
    longitudMedia(&rtaLongMedia,probPalabras,5,cantPalabrasDist);
    printf("Longitud media del codigo: %4.2f\n",rtaLongMedia);
    if(entropia <= rtaLongMedia)
        printf("El codigo es candidato a ser compacto\n");
    else
        printf("El codigo no es candidato a ser compacto\n");
    rendimiento= entropia/rtaLongMedia;
    redundancia=1-rendimiento;
    printf("Rendimiento del codigo: %f / Redundancia del codigo: %f\n",rendimiento,redundancia);
    printf("---------------------------------------------------------------\n");
    fclose(arch);
}

void seven_pals (){
    char n[10],palabras[128][10];
    float frecuenciaPalabras[128]={0},probPalabras[128]={0},totalPalabras=0,informacion=0,entropia=0,rtaLongMedia=0,rendimiento,redundancia;
    int cantPalabrasDist=0,bandera=0;
    for(int i=0; i<128;i++){
        strcpy(palabras[i]," ");
    }
    FILE *arch;
    arch=fopen("anexo1.txt","r");
    while(!feof(arch)){
        fscanf(arch,"%7s",n); //leo hasta 7 digitos
         buscaPalabras(palabras,&bandera,n,&cantPalabrasDist); //busca la palabra, si la encuentra devuelve la posicion donde estaba para aumentar la frecuencia, si no la encuentra, la agrega y hace lo mismo
         frecuenciaPalabras[bandera]++;
         totalPalabras++;
    }
    for(int i=0;i<cantPalabrasDist;i++){
        probPalabras[i]=frecuenciaPalabras[i]/totalPalabras;
        informacion+=(log(1/probPalabras[i])/log(2));
        entropia+=(probPalabras[i]*log(1/probPalabras[i])/log(2));
    }
    printf("Fuente con palabras de 7 digitos:\n");
    printf("Informacion total de la fuente: %5.1f (en bits)\n",informacion);
    printf("Entropia total de la fuente: %5.1f (en bits)\n",entropia);
       longitudMedia(&rtaLongMedia,probPalabras,7,cantPalabrasDist);
    printf("Longitud media del codigo: %4.2f\n",rtaLongMedia);
    if(entropia <= rtaLongMedia)
        printf("El codigo es candidato a ser compacto\n");
    else
        printf("El codigo no es candidato a ser compacto\n");
    rendimiento= entropia/rtaLongMedia;
    redundancia=1-rendimiento;
    printf("Rendimiento del codigo: %f / Redundancia del codigo: %f\n",rendimiento,redundancia);
    printf("---------------------------------------------------------------\n");
    fclose(arch);
}

void nine_pals (){
    char n[10],palabras[512][10];
    float frecuenciaPalabras[512]={0},probPalabras[512]={0},totalPalabras=0,informacion=0,entropia=0,rtaLongMedia=0,rendimiento,redundancia;
    int cantPalabrasDist=0,bandera=0;
    for(int i=0; i<512;i++){
        strcpy(palabras[i]," ");
    }
    FILE *arch;
    arch=fopen("anexo1.txt","r");
    while(!feof(arch)){
        fscanf(arch,"%9s",n); //leo hasta 9 digitos
         buscaPalabras(palabras,&bandera,n,&cantPalabrasDist); //busca la palabra, si la encuentra devuelve la posicion donde estaba para aumentar la frecuencia, si no la encuentra, la agrega y hace lo mismo
         frecuenciaPalabras[bandera]++;
         totalPalabras++;
    }
    for(int i=0;i<cantPalabrasDist;i++){
        probPalabras[i]=frecuenciaPalabras[i]/totalPalabras;
        informacion+=(log(1/probPalabras[i])/log(2));
        entropia+=(probPalabras[i]*log(1/probPalabras[i])/log(2));
    }
    printf("Fuente con palabras de 9 digitos:\n");
    printf("Informacion total de la fuente: %5.1f (en bits)\n",informacion);
    printf("Entropia total de la fuente: %5.1f (en bits)\n",entropia);

    longitudMedia(&rtaLongMedia,probPalabras,9,cantPalabrasDist);
    printf("Longitud media del codigo: %4.2f\n",rtaLongMedia);
    if(entropia <= rtaLongMedia)
        printf("El codigo es candidato a ser compacto\n");
    else
        printf("El codigo no es candidato a ser compacto\n");
    rendimiento= entropia/rtaLongMedia;
    redundancia=1-rendimiento;
    printf("Rendimiento del codigo: %f / Redundancia del codigo: %f\n",rendimiento,redundancia);
    printf("---------------------------------------------------------------\n");
    fclose(arch);
}
