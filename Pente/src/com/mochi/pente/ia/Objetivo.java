package com.mochi.pente.ia;

import com.mochi.pente.entity.Jugada;

public abstract class Objetivo {
	public final int MAX_PUNTUACION = 1000;
	public final int STEP = 10;
	protected int puntuacion = 0;
	
	public int evaluar(int[][] tablero,Jugada pos) {
		return puntuacion;
	}
}
