#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define ascii 48
#include <locale.h>
typedef struct nodoArbol{
char simbolo[10],codigoBinario[100];
float prob;
struct nodoArbol *izq;
struct nodoArbol *der;
struct nodoArbol *padre;} nodoArbol;

typedef struct nodoArbol *arbol;

typedef struct nodoLista{
char simbolo[10];
float prob;
arbol a;
struct nodoLista *sig;}nodoLista;

typedef struct nodoLista *Tlista;

void getEntropiaInfo(int cantPalabrasDist,float *entropia,float *informacion,float probPalabras[],float frecuenciaPalabras[],float totalPalabras,char palabras[][10]);
void buscaSimbolo(arbol a,char auxSimbolo[],arbol *auxNodo,int longitud);
void buscaPalabras(char palabras[][10],int *pos,char cadena[],int *cantPalabrasDist);
void procesoPalabras(int nroDigitos,Tlista *l);
void huffman(arbol a,char binario[],int longitud);
void fusionNodo(Tlista izq,Tlista der,arbol *nuevoArbol);
void longitudMedia(float *rta,float probabilidades[],int longitud,int cantPalabras);
int potencia(int base, int pot);
float potenciaNegativa(float base, int pot);
float kraftMcMillan(char palabras[][10],int n);
void compresionHuffman(Tlista l,int longitud,FILE *arch);


int main()
{
    Tlista l;
    int cantSimbolos;
    setlocale(LC_ALL, "spanish");
    FILE *arch5_2,*arch7_2,*arch9_2;

    procesoPalabras(5,&l);
    huffman(l->a,"",5);
    arch5_2=fopen("huffman5.txt","w");
    compresionHuffman(l,5,arch5_2);
    fclose(arch5_2);
    free(l);


    procesoPalabras(7,&l);
    huffman(l->a,"",7);
    arch7_2=fopen("huffman7.txt","w");
    compresionHuffman(l,7,arch7_2);
    fclose(arch7_2);
    free(l);

    procesoPalabras(9,&l);
    huffman(l->a,"",9);
    arch9_2=fopen("huffman9.txt","w");
    compresionHuffman(l,9,arch9_2);
    fclose(arch9_2);
    free(l);
    return 0;
}

void huffman(arbol a,char binario[],int longitud){
  char auxBinario[20]="";
    if(a){
        if(a->padre!=NULL){
        strcpy(auxBinario,a->padre->codigoBinario);
        strcat(auxBinario,binario);
        strcpy(a->codigoBinario,auxBinario);
        }
        huffman(a->izq,"0",longitud);
        if(strlen(a->simbolo)==longitud)
            printf("Símbolo: %s / Código Huffman: %s\n",a->simbolo,a->codigoBinario);
        huffman(a->der,"1",longitud);
    }
}

void buscaSimbolo(arbol a,char auxSimbolo[],arbol *auxNodo,int longitud){
    if(a){
        buscaSimbolo(a->izq,auxSimbolo,auxNodo,longitud);
        if(strlen(a->simbolo)==longitud)
            if(!strcmp(a->simbolo,auxSimbolo))
                strcpy((*auxNodo)->codigoBinario,a->codigoBinario);
       buscaSimbolo(a->der,auxSimbolo,auxNodo,longitud);
  }

}

void compresionHuffman(Tlista l,int longitud,FILE *archEscritura){
    char auxSimbolo[10];
    FILE *archLectura;
    archLectura=fopen("anexo1.txt","r");
    arbol auxNodo;
    if(longitud==5)
            fscanf(archLectura,"%5s",auxSimbolo);
    else if (longitud==7)
            fscanf(archLectura,"%7s",auxSimbolo);
    else
            fscanf(archLectura,"%9s",auxSimbolo);
    while(!feof(archLectura)){
        auxNodo=(arbol)malloc(sizeof(nodoArbol));
        strcpy(auxNodo->codigoBinario,"");
        buscaSimbolo(l->a,auxSimbolo,&auxNodo,longitud);
        fprintf(archEscritura,"%s",auxNodo->codigoBinario);
        if(longitud==5)
            fscanf(archLectura,"%5s",auxSimbolo);
        else if (longitud==7)
            fscanf(archLectura,"%7s",auxSimbolo);
        else
            fscanf(archLectura,"%9s",auxSimbolo);
    }
        fclose(archEscritura);
        fclose(archLectura);

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



void fusionNodo(Tlista izq,Tlista der,arbol *nuevoArbol){
    (*nuevoArbol)=(arbol)malloc(sizeof(nodoArbol));
    (*nuevoArbol)->izq=NULL;
    (*nuevoArbol)->der=NULL;
    (*nuevoArbol)->padre=NULL;
    char auxSimbolo[10];
    strcpy(auxSimbolo,izq->simbolo);
    strcpy((*nuevoArbol)->simbolo,"");
    strcpy((*nuevoArbol)->codigoBinario,"");
    (*nuevoArbol)->izq=izq->a;
    (*nuevoArbol)->prob=izq->prob+der->prob;
    (*nuevoArbol)->der=der->a;
    der->a->padre=(*nuevoArbol);
    izq->a->padre=(*nuevoArbol);
}


float kraftMcMillan(char palabras[][10],int n){
    float suma=0;
    int i,r=2;
    for(i=0;i<n;i++)
        suma=suma+potenciaNegativa(r,strlen(palabras[i]));
    return suma;


}

float potenciaNegativa(float base, int pot)
{
    int i;
    float aux=base;
    for (i=0;i<pot+1;i++)
        base=(base/aux);
    return base;
}



void getEntropiaInfo(int cantPalabrasDist,float *entropia,float *informacion,float probPalabras[],float frecuenciaPalabras[],float totalPalabras,char palabras[][10]){
    int i;
    float auxInformacion;
    for(i=0;i<cantPalabrasDist;i++){
        probPalabras[i]=(frecuenciaPalabras[i]/totalPalabras);
        auxInformacion=(log(1/probPalabras[i])/log(2));
        printf("Símbolo: %s / Cantidad de información del símbolo: %4.2f\n",palabras[i],auxInformacion);
        (*informacion)+=auxInformacion;
        (*entropia)+=(probPalabras[i]*log(1/probPalabras[i])/log(2));
    }

}


//PROCEDIMIENTO EN DONDE SE ARMAN LOS VECTORES, SE CONFECCIONA UNA LISTA Y A PARTIR DE LA MISMA SE ARMA EL ARBOL DE HUFFMAN

void procesoPalabras(int nroDigitos,Tlista *l){
    int cantidadBase=potencia(2,nroDigitos),i;//2**nroDigitos;
    char n[10],palabras[cantidadBase][10];
    float frecuenciaPalabras[cantidadBase],probPalabras[cantidadBase],totalPalabras=0,informacion=0,entropia=0,rtaLongMedia=0,rendimiento,redundancia,kraft;
    int cantPalabrasDist=0,pos=0;
    memset(frecuenciaPalabras,0,cantidadBase*sizeof(frecuenciaPalabras[0])); //INICIALIZO AMBOS VECTORES EN 0
    memset(probPalabras,0,cantidadBase*sizeof(probPalabras[0]));
    Tlista nuevo,ant,act,elim1,elim2;
    arbol nuevoArbol;
    *l=NULL;
    for(i=0; i<cantidadBase;i++){
        strcpy(palabras[i]," ");
    }
    FILE *arch;
    arch=fopen("anexo1.txt","r");
    if (nroDigitos==5)
            fscanf(arch,"%5s",n); //leo hasta 5 digitos
        else
            if (nroDigitos==7)
                fscanf(arch,"%7s",n);
            else
                if (nroDigitos==9)
                    fscanf(arch,"%9s",n);
    while(!feof(arch)){
         buscaPalabras(palabras,&pos,n,&cantPalabrasDist); //busca la palabra, si la encuentra devuelve la posicion donde estaba para aumentar la frecuencia, si no la encuentra, la agrega y hace lo mismo
         frecuenciaPalabras[pos]++;
         totalPalabras++;
           if (nroDigitos==5)
            fscanf(arch,"%5s",n); //leo hasta 5 digitos
        else
            if (nroDigitos==7)
                fscanf(arch,"%7s",n);
            else
                if (nroDigitos==9)
                    fscanf(arch,"%9s",n);
    }
    printf("Fuente con palabras de %d dígitos:\n",nroDigitos);
    getEntropiaInfo(cantPalabrasDist,&entropia,&informacion,probPalabras,frecuenciaPalabras,totalPalabras,palabras);
    kraft=kraftMcMillan(palabras,cantPalabrasDist);
    if(kraft<=1.0)
        printf("Cumple con la inecuación de Kraft/McMillan: %2.1f\n",kraft);
    else
        printf("No cumple con la inecuación de Kraft/McMillan: %2.1f >= 1\n",kraft);
    printf("Información total de la fuente: %5.1f (en bits)\n",informacion);
    printf("Entropía total de la fuente: %5.1f (en bits)\n",entropia);
    longitudMedia(&rtaLongMedia,probPalabras,nroDigitos,cantPalabrasDist);
    printf("Longitud media del código: %4.2f\n",rtaLongMedia);
    if(entropia <= rtaLongMedia)
        printf("El código es candidato a ser compacto\n");
    else
        printf("El código no es candidato a ser compacto\n");
    rendimiento= entropia/rtaLongMedia;
    redundancia=1-rendimiento;
    printf("Rendimiento del código: %f / Redundancia del código: %f\n",rendimiento,redundancia);
    printf("---------------------------------------------------------------\n");
    fclose(arch);

//DESDE ACÁ SE ARMA EL ARBOL
     for(i=0;i<cantPalabrasDist;i++){
        nuevo=(Tlista)malloc(sizeof(nodoLista));
        strcpy(nuevo->simbolo,palabras[i]);
        nuevo->prob=probPalabras[i];
        nuevoArbol=(arbol)malloc(sizeof(nodoArbol));
        nuevoArbol->der=NULL;
        nuevoArbol->izq=NULL;
        nuevoArbol->prob=nuevo->prob;
        strcpy(nuevoArbol->simbolo,nuevo->simbolo);
        strcpy(nuevoArbol->codigoBinario,"");
        nuevo->a=nuevoArbol;
        if(!*l || (*l)->prob>nuevo->prob){
            nuevo->sig=*l;
            *l=nuevo;
        }
        else{
            ant=*l;
            act=ant->sig;
            while(act && act->prob<nuevo->prob){
                ant=act;
                act=act->sig;
            }
            ant->sig=nuevo;
            nuevo->sig=act;
        }
    }
    ant=*l;
    act=ant->sig;
    if(act){
    while(ant){
        fusionNodo(ant,act,&nuevoArbol);
        elim1=ant;
        elim2=act;
        if(act->sig)
         *l=act->sig;
        nuevo=(Tlista)malloc(sizeof(nodoLista));
        nuevo->a=nuevoArbol;
        nuevo->prob=nuevoArbol->prob;
        strcpy(nuevo->simbolo,nuevoArbol->simbolo);
        if((*l)->prob>nuevo->prob){
            nuevo->sig=*l;
            *l=nuevo;
        }else{
        ant=*l;
        act=(*l)->sig;
        while(act && act->prob<nuevo->prob){
            ant=act;
            act=act->sig;
        }
        ant->sig=nuevo;
        nuevo->sig=act;
        }
        free(elim1); free(elim2);
        if(nuevo->sig==NULL && nuevo->prob==1){
        ant=NULL;
        *l=nuevo;}
        else{
         ant=*l;
         act=ant->sig;
        }
    }
  }
  else{
    (strcpy(ant->a->codigoBinario,"0"));
    ant->a->padre=NULL;
    ant->a->der=NULL;
    ant->a->izq=NULL;
    }
}
