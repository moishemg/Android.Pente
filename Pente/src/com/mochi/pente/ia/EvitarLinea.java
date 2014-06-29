package com.mochi.pente.ia;

import com.mochi.pente.entity.Jugada;
import com.mochi.pente.entity.Partida;

public class EvitarLinea extends Objetivo {
	protected int numLinea;
	protected boolean borde;
	public EvitarLinea(int peso, int numLinea, boolean borde) {
		super(peso);
		this.numLinea = numLinea;
		this.borde = borde;
	}

	@Override
	public int evaluar(Partida partida, Jugada pos) {
		// debemos buscar una linea de fichas de longitud numLinea
		partida.marcarPosicion(pos);
		partida.comprobarLineaDeFichasContrario(this.numLinea, pos);
		partida.liberarPosicion(pos);
		
		return (partida.getLineaFichas().size()==this.numLinea?this.peso:0);
	}

}
