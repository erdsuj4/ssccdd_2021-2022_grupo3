Proceso P0(id,semaforo){
	semaforo.signal()
}

````
Proceso P(id,semaforo){
	semaforo.signal()
}
````

````
void ejecutar(Proceso proc){
//codigo
}

````


ejecutar{
	Semaforo p0:0
	Semaforo p1:0
	Semaforo p2:0
	Semaforo p3:0
	Semaforo p4:0
	Semaforo p5:0
	boolean procesosCompletados[NUM_PROC]=false
	P poceso
	
	proceso=P(0,p0)
	p0.wait()
	ejecutar(proceso)
	procesosCompletados[0]=true
	
	if(procesosCompletados[0]){
		proceso1=P(1,p1)
		proceso2=P(2,p2)
		p1.wait()
		ejecutar(proceso)
		procesosCompletados[1]=true
		
		proceso=P(2,p3)
		p3.wait()
		ejecutar(proceso)
		
		proceso=P(3,p4)
		p4.wait()
		ejecutar(proceso)
	}
	
	

}

## Segundo Problema
Los buscadores se pueden ejecutar concurrentemente con otros buscadores e insertores

Los insertores no pueden ejecutarse concurrentemente entre ellos, pero sí con buscadores

Los borradores no se pueden ejecutar concurrentemente con ningun otro proceso (ni siquiera consigo mismos)


**Variables:**
**ArrayList lista** Lista de elementos
**Semaforo exmInsertor=1**  Sirve para que los insertores se excluyan entre si, los insertores se ejecutan uno a uno
**Semaforo exmBorrar=1** Sirve para acceder de forma segura al booleano borrando y a la variable bloqueados mediante exclusión mutua
**int bloqueados=0** Numero de procesos (buscador o insertores) que se han quedado bloqueados ya que el borrador se esta ejecutando, una vez el borrador borre, los liberara
**Semaforo esperaBorrar=0** Se encarga de que los buscadores y los insertores se bloqueen en caso de que se esté borrando, al acabar de borrar se liberaran (se cuantos procesos debo liberar gracias a la variable bloqueados)
**boolean borrando=false** Si esta a true significa que un borrador está borrando, si está a false no esta borrando
**Semaforo exmInsertor=1** Sirve para que los insertores no se ejecuten concurrentemente entre sí, los insertores se ejecutan de 1 en uno

 **Proceso Buscador**

     exmBorrador.wait() // si se está borrando no puedo buscar, pero si no hay borradores si luego llega uno hasta que no termine de buscar no podra borrar
     exmBorrar.wait() //accedo a borrando y modifico bloqueados en exmutua
     if(borrando){ //se esta borrando ya, debo esperar
    	bloqueados++ //añado 1 para luego liberar en el borrador
    	exmBorrar.signal()
    	esperaBorrar.wait() //cuando se borre me liberarán
    }
    lista.buscar() //busco
    exmBorrador.signal()


**Proceso Insertor**

    exmInsertor.wait() //Insertores de 1 en 1
    exmBorrar.wait()
    if(borrando){ //se esta borrando ya, debo esperar
    	bloqueados++ //añado 1 para luego liberar en el borrador
    	exmBorrar.signal()
    	esperaBorrar.wait() //cuando se borre me liberarán
    }
    lista.add() //inserto
    exmInsertor.signal()

**Proceso Borrador**

    exmInsertor.wait() // si estoy insertando el borrador se bloquea y si no, bloqueo a los insertores
    exmBorrador.wait() //borradores de 1 en 1
    exmBorrar.wait() //accedo a borrando en exclusion mutua
    borrando=true //pongo esta variable a true cuanto antes pueda mejor
    exmBorrar.signal()
    lista.remove() //borro
    exmBorrar.wait() //reseteo borrando en exMutua y accedo a bloqueados también en exMutua
    Desde(i=0 hasta bloqueados){ //Ya he borrado, libero a los insertores y buscadores bloqueados
    	esperaBorrar.signal()
    }
    borrando=false //ya he terminado de borrar
    bloqueados=0 //he terminado de liberar, reseteo la variable
    exmBorrar.signal()
    exmBorrador.signal()
    exmInsertor.signal()



