#include <stdio.h>
#include <stdlib.h>
#define ascii 48

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




void buscaPalabras(char palabras[][10],int *pos,char cadena[],int *cantPalabrasDist);
void procesoPalabras(int nroDigitos,Tlista *l);
void huffman(arbol a,char binario[],int longitud,FILE *arch);
void fusionNodo(Tlista izq,Tlista der,arbol *nuevoArbol);
void longitudMedia(float *rta,float probabilidades[],int longitud,int cantPalabras);
int potencia(int base, int pot);



int main()
{
    Tlista l;
    FILE *arch5,*arch7,*arch9;

    arch5=fopen("5digitos.txt","w");
    procesoPalabras(5,&l);
    huffman(l->a,"",5,arch5);
    fclose(arch5);
    free(l);

    arch7=fopen("7digitos.txt","w");
    procesoPalabras(7,&l);
    huffman(l->a,"",7,arch7);
    fclose(arch7);
    free(l);

    arch9=fopen("9digitos.txt","w");
    procesoPalabras(9,&l);
    huffman(l->a,"",9,arch9);
    fclose(arch9);
    return 0;
}



void huffman(arbol a,char binario[],int longitud,FILE *arch){
  char auxBinario[20]="";
    if(a){
        if(a->padre!=NULL){
        strcpy(auxBinario,a->padre->codigoBinario);
        strcat(auxBinario,binario);
        strcpy(a->codigoBinario,auxBinario);
        }
        huffman(a->izq,"0",longitud,arch);
        if(strlen(a->simbolo)==longitud)
            fprintf(arch,"Simbolo: %s / Codigo Huffman: %s\n",a->simbolo,a->codigoBinario);
        huffman(a->der,"1",longitud,arch);
    }
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




void procesoPalabras(int nroDigitos,Tlista *l){
    int cantidadBase=potencia(2,nroDigitos),i;//2**nroDigitos;
    char n[10],palabras[cantidadBase][10];
    float frecuenciaPalabras[cantidadBase],probPalabras[cantidadBase],totalPalabras=0,informacion=0,entropia=0,rtaLongMedia=0,rendimiento,redundancia;
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
    fclose(arch);
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
