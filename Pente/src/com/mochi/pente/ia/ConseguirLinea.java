package com.mochi.pente.ia;

import com.mochi.pente.entity.Jugada;
import com.mochi.pente.entity.Partida;

public class ConseguirLinea extends Objetivo {
	protected int numLinea;
	protected boolean borde;
	public ConseguirLinea(int peso, int numLinea, boolean borde) {
		super(peso);
		this.numLinea = numLinea;
		this.borde = borde;
	}

	@Override
	public int evaluar(Partida partida, Jugada pos) {
		// debemos buscar una linea de fichas de longitud numLinea
		partida.comprobarLineaDeFichasPropia(this.numLinea, pos);
		
		return (partida.getLineaFichas().size()==this.numLinea?this.peso:0);
	}

}
