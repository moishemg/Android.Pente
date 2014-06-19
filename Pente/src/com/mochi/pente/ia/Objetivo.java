package com.mochi.pente.ia;

import com.mochi.pente.entity.Jugada;
import com.mochi.pente.entity.Partida;

public abstract class Objetivo {
	protected int peso = 0;
	
	public Objetivo(int peso) {
		this.peso = peso;
	}
	
	public abstract int evaluar(Partida partida,Jugada pos);
}
