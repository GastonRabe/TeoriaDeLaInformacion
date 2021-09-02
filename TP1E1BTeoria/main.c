#include <stdio.h>
#include <stdlib.h>

int main()
{
    char simbolos[4][3]={"00","01","10","11"},n[3];
    float frecuencia[4]={0},cantPalabras=0,probabilidades[4]={0},informacion=0,entropia,entropiaTot;
    int i;
    FILE *arch;
    arch = fopen("anexo1.txt","r");
    while(!feof(arch)){
        fscanf(arch,"%2s",n);
        i=0;
        while(i<4){
            if(!strcmp(simbolos[i],n))
                frecuencia[i]++;
            i++;
        }
        cantPalabras++;
    }
    for (i=0;i<4;i++){
        probabilidades[i]=frecuencia[i]/cantPalabras;
        printf("Probabilidad palabra %s: %5.2f\n",simbolos[i],probabilidades[i]);
        informacion=log(1/probabilidades[i])/log(2);
        entropia= probabilidades[i]*informacion;
        printf("Informacion palabra %s: %5.2f\n",simbolos[i],informacion);
        printf("Entropia palabra %s: %5.2f\n",simbolos[i],entropia);
        entropiaTot+=entropia;
    }
    printf("Entropia Total de la fuente: %5.2f\n",entropiaTot);
    fclose(arch);
    return 0;
}
