/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uja.ssccdd.curso2122.sesion12;

import java.util.Random;

/**
 *
 * @author pedroj
 */
public interface Constantes {
    // Generador aleatorio
    public static final Random aleatorio = new Random();
    
    public enum TipoComponente {
        CPU(20,1), MEMORIA(40,4), SENSOR(60,3), AUDIO(80,2), E_S(100,1);
        
        private final int valor;
        private final int minimoComponente;

        private TipoComponente(int valor, int minimoComponente) {
            this.valor = valor;
            this.minimoComponente = minimoComponente;
        }
        
        /**
         * Genera aleatoriamente uno de los tipos de componente disponibles
         * @return el tipo de componente generado
         */
        public static TipoComponente getTipoComponente() {
            TipoComponente resultado = null;
            int valor = aleatorio.nextInt(D100);
            
            int i = 0;
            
            while( (i < componentes.length) && (resultado == null) ) {
                if( componentes[i].valor > valor)
                    resultado = componentes[i];
                
                i++;
            }
            return resultado;
        }

        /**
         * Indica el minimoComponente para ese tipo de proceso
         * @return el minimoComponente
         */
        public int getMinimoComponente() {
            return minimoComponente;
        }
    }
    
    // Constantes de la sesión
    public static final int D100 = 100; // Tirada de dato de 100 caras
    public static final TipoComponente[] componentes = TipoComponente.values();
    public static final boolean COMPLETO = true;
    public static final boolean ASIGNADO = true;
    public static final int AMPLIADO = 50; // para decidir si es un ordenador normal o ampliado
}
