#include <stdio.h>
#include <stdlib.h>
#define ascii 48

void buscaPalabras(char palabras[][10],int *pos,char cadena[],int *cantPalabrasDist);
void procesoPalabras(int);
void longitudMedia(float *rta,float probabilidades[],int longitud,int cantPalabras);
int potencia(int base, int pot);


int main()
{
    procesoPalabras(5);
    procesoPalabras(7);
    procesoPalabras(9);
    return 0;
}

int potencia(int base, int pot)
{
    int i, aux=base;
    for (i=1;i<pot;i++)
        base*=aux;
    return base;
}

void longitudMedia(float *rta,float probabilidades[],int longitud,int cantPalabras){
    int i;
    for(i=0;i<cantPalabras;i++){
        (*rta)+=probabilidades[i]*longitud;
    }
}


void buscaPalabras(char palabras[][10],int *pos,char cadena[],int *cantPalabrasDist){
    int i=0;
    while(strcmp(palabras[i]," ") && strcmp(palabras[i],cadena)) // mientras no se caiga y sea distinto de '' es decir que ya hay una palabra
        i++;
    if(!strcmp(palabras[i]," ")){
            strcpy(palabras[i],cadena);
            (*cantPalabrasDist)++;
    }
    *pos=i;
}

void procesoPalabras(int nroDigitos){
    int cantidadBase=potencia(2,nroDigitos);//2**nroDigitos;
    char n[10],palabras[cantidadBase][10];
    float frecuenciaPalabras[cantidadBase],probPalabras[cantidadBase],totalPalabras=0,informacion=0,entropia=0,rtaLongMedia=0,rendimiento,redundancia;
    int cantPalabrasDist=0,pos=0;
    memset(frecuenciaPalabras,0,cantidadBase*sizeof(frecuenciaPalabras[0])); //INICIALIZO AMBOS VECTORES EN 0
    memset(probPalabras,0,cantidadBase*sizeof(probPalabras[0]));
    for(int i=0; i<cantidadBase;i++){
        strcpy(palabras[i]," ");
    }
    FILE *arch;
    arch=fopen("anexo1.txt","r");
    while(!feof(arch)){
        if (nroDigitos==5)
            fscanf(arch,"%5s",n); //leo hasta 5 digitos
        else
            if (nroDigitos==7)
                fscanf(arch,"%7s",n);
            else
                if (nroDigitos==9)
                    fscanf(arch,"%9s",n);
         buscaPalabras(palabras,&pos,n,&cantPalabrasDist); //busca la palabra, si la encuentra devuelve la posicion donde estaba para aumentar la frecuencia, si no la encuentra, la agrega y hace lo mismo
         frecuenciaPalabras[pos]++;
         totalPalabras++;
    }
    for(int i=0;i<cantPalabrasDist;i++){
        probPalabras[i]=(frecuenciaPalabras[i]/totalPalabras);
        informacion+=(log(1/probPalabras[i])/log(2));
        entropia+=(probPalabras[i]*log(1/probPalabras[i])/log(2));
    }
    printf("Fuente con palabras de %d digitos:\n",nroDigitos);
    printf("Informacion total de la fuente: %5.1f (en bits)\n",informacion);
    printf("Entropia total de la fuente: %5.1f (en bits)\n",entropia);
    longitudMedia(&rtaLongMedia,probPalabras,nroDigitos,cantPalabrasDist);
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
