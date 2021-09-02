#include <stdio.h>
#include <stdlib.h>
#define ascii 48

void five_pals (float probabilidades[]);
void seven_pals (float probabilidades[]);
void nine_pals (float probabilidades[]);

int main()
{
    float cont0=0,cont1=0,cont=0,probabilidades[2]={0};
    char n;
    FILE *arch;
    arch=fopen("anexo1.txt","r");
    while(!feof(arch)){
       fscanf(arch,"%c",&n);
       cont++;
       if(n-ascii==0) //convierto char en int
         cont0++;
       else
        cont1++;
    }
    fclose(arch);
    probabilidades[0]=cont0/cont;
    probabilidades[1]=cont1/cont;
    five_pals(probabilidades);
    seven_pals(probabilidades);
    nine_pals(probabilidades);
    return 0;
}


void five_pals (float probabilidades[]){
    char n[6],aux;
    int cont=0;
    float probTotal,informacion=0,entropia=0;
    FILE *arch;
    arch=fopen("anexo1.txt","r");
    while(!feof(arch)){
        fscanf(arch,"%5s",n); //leo hasta 5 digitos
        probTotal=0;
        for(int i = 0;i<strlen(n);i++){
            aux=n[i];
            if(!probTotal)
               probTotal=probabilidades[aux-ascii];
            else
               probTotal*=probabilidades[aux-ascii];

        }
        informacion+=log(1/probTotal)/log(2);
        entropia+=probTotal*informacion;
    }

    printf("Fuente con palabras de 5 digitos:\n");
    printf("Informacion total de la fuente: %5.2f (en bits)\n",informacion);
    printf("Entropia total de la fuente: %5.2f (en bits)\n",entropia);
    printf("---------------------------------------------------------------\n");
    fclose(arch);
}

void seven_pals (float probabilidades[]){
    char n[8],aux;
    int cont=0;
    float probTotal,informacion=0,entropia=0;
    FILE *arch;
    arch=fopen("anexo1.txt","r");
    while(!feof(arch)){
        fscanf(arch,"%7s",n);//leo hasta 7 digitos
         probTotal=0;
        for(int i = 0;i<strlen(n);i++){
            aux=n[i];
            if(!probTotal)
               probTotal=probabilidades[aux-ascii];
            else
               probTotal*=probabilidades[aux-ascii];

        }
        informacion+=log(1/probTotal)/log(2);
        entropia+=probTotal*informacion;
    }
    printf("Fuente con palabras de 7 digitos:\n");
    printf("Informacion total de la fuente: %5.2f (en bits)\n",informacion);
    printf("Entropia total de la fuente: %5.2f (en bits)\n",entropia);
    printf("---------------------------------------------------------------\n");
    fclose(arch);
}

void nine_pals (float probabilidades[]){
    char n[10],aux;
    int cont=0;
    float probTotal,informacion=0,entropia=0;
    FILE *arch;
    arch=fopen("anexo1.txt","r");
   while(!feof(arch)){
        fscanf(arch,"%9s",n);//leo hasta 9 digitos
         probTotal=0;
        for(int i = 0;i<strlen(n);i++){
            aux=n[i];
            if(!probTotal)
               probTotal=probabilidades[aux-ascii];
            else
               probTotal*=probabilidades[aux-ascii];

        }
        informacion+=log(1/probTotal)/log(2);
        entropia+=probTotal*informacion;
    }
    printf("Fuente con palabras de 9 digitos:\n");
    printf("Informacion total de la fuente: %5.2f (en bits)\n",informacion);
    printf("Entropia total de la fuente: %5.2f (en bits)\n",entropia);
    printf("---------------------------------------------------------------\n");
    fclose(arch);
}
