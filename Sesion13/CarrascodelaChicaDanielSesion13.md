# Sesion 13 Daniel Carrasco de la Chica
## Primer problema


**Proceso A(id)**
```
send(solicitudBDM[id],any) // canal de solicitud de entrada BD M
send(solicitudA_BDO[id],any) //canal de solicitud de entrada BD O
receive(confirmarA[id],any)
send(salirA_BDO[id],any)
```
**Proceso B(id)**
```
send(solicitudBDN[id],any)
send(solicitudB_BDO[id],any)
receive(confirmarB[id],any)
send(salirB_BDO[id],any)
```

**Controlador**

*Variables:*
numProcA: Numero de procesos A en la BBDD M
numProcB: Numero de procesos B en la BBDD N
bufferAO: Buffer de elementos A esperando a entrar en BBDD O
bufferBO: Buffer de elementos B esperando a entrar en BDD O
numProcA_BDO: Numero de procesos A en la BBDD O
numProcB_BDO: Numero de procesos B en la BBDD O
primeraVez: Booleano que me sirve para saber cuantos procesos deben de hacer en la BD_O en cada momento (inicializado a true), la primera vez que se "llene" deberán haber 4 procesos como mínimo, posteriormente la misma cantidad de ambos
```
	while(true){
		select
			for i to L replicate
				when(numProcA<4) // si no se cumple el tope el proceso se insertara en la BBDD
					receive(solicitudBDM[i],any)
					numProcA++
		or
			for i to K replicate
				when(numProcB<5)
					receive(solicitudBDN[i],any)
					numProcB++
		or
			for i to L replicate
				when(primeraVez) //hay que diferenciar de si se llena por primera vez o no el buffer
					primeraVez=false
					receive(solicitudA_BDO[i],any)
					bufferAO.add(i)
					numProcA--
					numProcA_BDO++
					if((numProcA_BDO-numProcB_BDO)==0 AND (numProcA_BDO+numprocB_BDO>=4)){
						while(!bufferAO.Empty()){
							send(confirmarA[bufferAO.remove()],any)
						}
					}
					
					
		or
			for i to K replicate
				when(primeraVez)
					primeraVez=false
					receive(solicitudB_BDO[i],any)
					bufferBO.add(i)
					numProcB--
					numProcB_BDO++
					if((numProcA_BDO-numProcB_BDO)==0 AND (numProcA_BDO+numprocB_BDO>=4)){
						while(!bufferBO.Empty()){
							send(confirmarB[bufferBO.remove()],any)
						}
					}
		or
			for i to L replicate
				when(!primeraVez)
					receive(solicitudA_BDO[i],any)
					bufferAO.add(i)
					numProcA--
					numProcA_BDO++
					if((numProcA_BDO-numProcB_BDO)==0){
						while(!bufferAO.Empty()){
							send(confirmarA[bufferAO.remove()],any)
						}
					}
		or
			for i to K replicate
				when(!primeraVez)
					receive(solicitudB_BDO[i],any)
					bufferBO.add(i)
					numProcB--
					numProcB_BDO++
					if((numProcA_BDO-numProcB_BDO)==0){
						while(!bufferBO.Empty()){
							send(confirmarB[bufferBO.remove()],any)
						}
					}
		or
			for i to L replicate
				receive(salirA_BDO[i],any)	
				send(solicitudSalida,any) // mediante este canal solicito la salida de la BD_O ya que este proceso A ha acabado y puede salir
		or
			for i to L replicate
				receive(salirB_BDO[i],any)	
				solicitudSalidaB++
		or
			when(solicitudSalidaB > 0) //si existe un proceso B que quiera salir, podrá salir junto con el proceso A que envio la solivitud (no hace falta saber si solicitudSalidaA>0 ya que un proceso A estableció este mensaje)
				receive(solicitudSalida,any)
				numProcA_BDO--
				numProcB_BDO--
				solicitudSalidaB--
			
	}

}
```

## Segundo Problema

### Buscador(id)
```
send(solicitarBuscar,id)
receive(confirmarBuscar[id],any)

//buscador esta buscando...

send(fin,buscar)
```
### Insertor(id)
```
send(solicitarInsertar,id)
receive(confirmarInsertar[id],any)

//insertor esta insertando...

send(fin,insertar)
```
### Borrador(id)
```
send(solicitarBorrar,id)
receive(confirmarBorrar[id],any)

//borrador esta borrando...

send(fin,borrar)
```

### Controlador
*Variables:*

 - numBus: Numero de buscadores que estan buscando
 - numBorr: Numero de borradores que estan borrando
 - numIns: Numero de insertores que estan insertando

```
while(true){
	select
		when(numBorr==0)
			receive(solicitarBuscar,id)
			send(confirmarBuscar[id],any)
	or
		when(numIns==0 AND numBorr==0)
			receive(solicitarInsertar,id)
			send(confirmarInsertar[id],any)
	or
		when(numBus==0 AND numIns==0 AND numBorr==0)
			receive(solicitarBorrar,id)
			send(confirmarBorrar[id],any)
	or
		receive(fin,tipo)
		if(tipo==buscar){
			numBus--
		}else if(tipo==insertar){
			numIns--
		}else{
			numBorr--
		}
}
```
