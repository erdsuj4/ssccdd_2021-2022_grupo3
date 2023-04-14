/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uja.ssccdd.curso2122.sesion2;

import static es.uja.ssccdd.curso2122.sesion2.Constantes.AMPLIADO;
import static es.uja.ssccdd.curso2122.sesion2.Constantes.ASIGNADO;
import static es.uja.ssccdd.curso2122.sesion2.Constantes.COMPLETO;
import static es.uja.ssccdd.curso2122.sesion2.Constantes.D100;
import es.uja.ssccdd.curso2122.sesion2.Constantes.TipoComponente;
import static es.uja.ssccdd.curso2122.sesion2.Constantes.aleatorio;
import static es.uja.ssccdd.curso2122.sesion2.Constantes.componentes;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author pedroj
 */
public class Ordenador {
    private final String ID;
    private final ArrayList<Componente[]> componentesOrdenador;
    private boolean completo;
    
    public Ordenador(String ID) {
        this.ID = ID;
        
        this.componentesOrdenador = new ArrayList();
        if( aleatorio.nextInt(D100) > AMPLIADO )
            for(TipoComponente tipo : componentes) 
                this.componentesOrdenador.add(new Componente[tipo.getMinimoComponente()]);
        else
            for(TipoComponente tipo : componentes) 
                this.componentesOrdenador.add(new Componente[tipo.getMinimoComponente() * 2]);
        
        this.completo = !COMPLETO;
    }

    public String getID() {
        return ID;
    }

    public boolean isCompleto() {
        for(TipoComponente tipo : componentes)
            for(Componente componente : this.componentesOrdenador.get(tipo.ordinal()))
                if( componente == null )
                    return !COMPLETO;
        
        return COMPLETO;
    }
    
    public boolean addComponente(Componente componente) {
        boolean asignado = !ASIGNADO;
        int indice = 0;
        Componente[] listaComponentes;
        
        listaComponentes = this.componentesOrdenador.get(componente.getTipo().ordinal());
        while( (indice < listaComponentes.length) && !asignado )
            if( listaComponentes[indice] == null ) {
                listaComponentes[indice] = componente;
                asignado = ASIGNADO;
            } else
                indice++;
        
        this.completo = this.isCompleto();
                
        
        return asignado;
    }

    @Override
    public String toString() {
        String resultado = "Ordenador{" +
                           "\n\tID = " + ID;
        
        if( this.completo )
            resultado = resultado + "\n\t**** ORDENADOR COMPLETO ****";
        else
            resultado = resultado + "\n\t**** ORDENADOR INCOMPLETO ****";
        
        for(TipoComponente tipo : componentes)
            resultado = resultado + "\n\t" + tipo.name() + " = " +
                        Arrays.toString(this.componentesOrdenador.get(tipo.ordinal()));
        
        resultado = resultado + "\n}";
        
        return resultado;   
    }
}
