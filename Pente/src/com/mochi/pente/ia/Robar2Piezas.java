package com.mochi.pente.ia;

import com.mochi.pente.entity.Jugada;
import com.mochi.pente.entity.Partida;

public class Robar2Piezas extends Objetivo {

	public Robar2Piezas(int peso) {
		super(peso);
	}

	@Override
	public int evaluar(Partida partida, Jugada pos) {
		
		partida.marcarPosicion(pos);
		boolean robar2 = partida.comprobarRobar2(pos);
		partida.liberarPosicion(pos);
		
		return (robar2?this.peso:0);
	}

}
