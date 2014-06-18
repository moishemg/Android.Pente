package com.mochi.pente.ia;

import com.mochi.pente.entity.Jugada;

public abstract class Objetivo {
	protected int peso = 0;
	
	public Objetivo(int peso) {
		this.peso = peso;
	}
	
	public abstract int evaluar(int[][] tablero,Jugada pos);
}
