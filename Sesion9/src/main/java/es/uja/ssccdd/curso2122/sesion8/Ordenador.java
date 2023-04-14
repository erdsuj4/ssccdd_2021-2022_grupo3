/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uja.ssccdd.curso2122.sesion8;

import static es.uja.ssccdd.curso2122.sesion8.Constantes.AMPLIADO;
import static es.uja.ssccdd.curso2122.sesion8.Constantes.ASIGNADO;
import static es.uja.ssccdd.curso2122.sesion8.Constantes.COMPLETO;
import static es.uja.ssccdd.curso2122.sesion8.Constantes.D100;
import es.uja.ssccdd.curso2122.sesion8.Constantes.TipoComponente;
import static es.uja.ssccdd.curso2122.sesion8.Constantes.aleatorio;
import static es.uja.ssccdd.curso2122.sesion8.Constantes.componentes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author pedroj
 */
public class Ordenador implements Delayed {
    private final String ID;
    private final ArrayList<Componente[]> componentesOrdenador;
    private boolean completo;
    private final Date disponible;
    
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

    @Override
    public long getDelay(TimeUnit unit) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int compareTo(Delayed o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
