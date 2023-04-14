/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uja.ssccdd.curso2122.sesion4;

import es.uja.ssccdd.curso2122.sesion4.Constantes.TipoComponente;


/**
 *
 * @author pedroj
 */
public class Componente {
    private final String ID;
    private final TipoComponente tipo;

    public Componente(String ID, TipoComponente tipo) {
        this.ID = ID;
        this.tipo = tipo;
    }

    public String getID() {
        return ID;
    }

    public TipoComponente getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return "Componente{" + "ID=" + ID + ", tipo=" + tipo + '}';
    }
}
