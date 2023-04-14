
# Ejercicios monitores Daniel Carrasco de la Chica
## Primer problema
Variables:
	- numProcA: Numero de procesos A
	- numProcB: Numero de procesos B
	- TiempoA: Tiempo de espera que tardara en ejecutarse el proceso A
	- TiempoB: Tiempo de espera que tardara en ejecutarse B
	- Condicion esperaA: Condicion para bloquear un proceso de tipo A cuando ya no quepa en la base de datos M
	- Condicion esperaB: Condicion para bloquear un proceso de tipo B cuando no quepa en la base de datos N
	- Condicion esperaBDO: Condicion que simula la cola previa para entrar a la base de datos O, hasta que no existan los procesos requeridos, se quedarán esperando

Explicacion: Cada proceso realiza la siguiente metodología, primero llama al metodo de añadir al monitor, si existen huecos en el buffer M/N respectivo, entendemos como que se ha almacenado y procede a esperar un tiempo (equivalente a su ejecución). Posteriormente tras ejecutarse lo eliminamos del buffer correspondiente decrementando la variable numProc (ya que vamos a eliminar un proceso del buffer).
Finalmente llamamos al metodo del monitor añadirBDO

Este método tiene una primera parte en la cual aun NO ha entrado el proceso al buffer, si es la primera vez que se llena necesita que existan 4 procesos totales y que a su vez hayan el mismo numero de procesos A y B, Como los procesos se van a ir acumulando de 1 en uno, al principio no se cumplirán estas condiciones por lo que se quedan a la espera de entrar al buffer (quedan bloqueados en esperaBDO)
Cuando finalmente entren 4 procesos como mínimo y haya igualdad entre tipo A y tipo B, el ultimo proceso que entró desbloquea a los que estaban bloqueados en cascada, así tras salir los N procesos del bloqueo junto el que los desbloqueó, se considera que finalmente han entrado a la BDM y por lo tanto, se van a ejecutar .

Posteriormente, conforme vayan entrando
	
### ProcesoA(monitor)
	monitor.añadirA()
	espera(TiempoA)
	monitor.añadirBDO()
	monitor.liberaA()
	ejecutarBDO(TiempoA) //ejecuta durante un tiempo el proceso
### ProcesoB(monitor)
	monitor.añadirB()
	espera(TiempoB)
	monitor.añadirBDO()
	monitor.liberaB()
	ejecutarBDO(TiempoB) //ejecuta durante un tiempo el proceso

### Monitor
```
metodo añadirA{
	Si (numProcA==4) entonces
		esperaA.delay()
	FinSi
	numProcA++
}
```

```
metodo añadirB{
	Si (numProcB==5) entonces
		esperaB.delay()
	FinSi
	numProcB++
}
```

```
metodo liberaA{
	Si(NO esperaA.Empty) entonces
		esperaA.resume()
		numProcA--
	FinSi
}
```

```
metodo liberaB{
	Si(NO esperaB.Empty) entonces
		esperaB.resume()
		numProcB--
	FinSi
}
```

```
metodo añadirBDO{
	//OJO, EL PROCESO ESTA EN LA COLA ESPERANDO A ENTRAR

	Si(primeraVez) entonces
		Si (numProcA-numProcB)!=0 o (numProcA+numprocB>=4) entonces
			esperaBDO.delay()
		FinSi
	SiNo Si (numProcA-numProcB!=0) entonces
		esperaBDO.delay()
	FinSi
	
	//SI LLEGA AQUI UN PROCESO ES QUE HA ENTRADO A LA BASE DE DATOS O
	
	Si (primeraVez) entonces
		primeraVez=false
	FinSi
	
	esperaBDO.resume()

}
```

## Segundo Problema

Variables:
- Condicion insertar: Condición para bloquear a insertor
- Condicion buscar: Condición para bloquear a buscador
- Condicion borrar: Condición para bloquear a borrador
- numIns: entero nos dice el numero de insertores
- numBus: entero numero e buscadores
- numBorr: entero numero de borradores

### Monitor
```
metodo iniciarBusqueda(){
	Si(numBorr>0) entonces
		busqueda.delay()
	FinSi
	numBus++
	buscar.resume()
}
```

```
metodo finalizarBusqueda(){
	numBus--
	Si ((numIns==0) y (numBus==0)) entonces
		borrar.resume()
	FinSi
}
```
```
metodo iniciarInsertar(){
	Si(numIns>0 o numBorrar>0) entonces
		insertar.delay()
	FinSi
	numIns++
}
```
```
metodo finalizarInsertar(){
	numIns--
	Si(NO insertar.Empty) entonces
		insertar.resume()
	SiNo Si(numBus==0) entonces
		borrar.resume()
	FinSi
}

```
```
metodo iniciarBorrar(){
	Si((numBus>0) o (numIns>0) o (numBorr>0)) entonces
		borrar.delay()
	FinSi
	numBorr++
}
```

```
metodo finalizarBorrar(){
	numBorr--
	Si(NO buscar.Empty) entonces
		buscar.resume()
	SiNo Si (NO insertar.Empty) entonces
		insertar.resume()
	SiNo
		borrar.resume()
	FinSi
}

```
