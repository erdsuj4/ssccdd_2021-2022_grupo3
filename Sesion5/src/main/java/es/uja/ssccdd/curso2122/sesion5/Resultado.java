/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uja.ssccdd.curso2122.sesion5;

import java.util.List;

/**
 *
 * @author pedroj
 */
public class Resultado {
    private final String iD;
    private final List<Ordenador> listaOrdenadores;
    private final List<Componente> componentesNoAsignados;

    public Resultado(String iD, List<Ordenador> listaOrdenadores, List<Componente> componentesNoAsignados) {
        this.iD = iD;
        this.listaOrdenadores = listaOrdenadores;
        this.componentesNoAsignados = componentesNoAsignados;
    }

    @Override
    public String toString() {
        String resultado  = "************ Lista Ordenadores " + iD + " *******************";
        
        for(Ordenador ordenador : listaOrdenadores)
            resultado += "\n\t" + ordenador;
        
        resultado += "\n Componentes no asignados a ningún ordenador\n\t" + componentesNoAsignados;
        resultado += "\n***************************************************************";
        
        return resultado;
    }
}
