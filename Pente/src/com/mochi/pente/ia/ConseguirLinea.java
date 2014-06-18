package com.mochi.pente.ia;

import com.mochi.pente.entity.Jugada;

public class ConseguirLinea extends Objetivo {
	protected int numLinea;
	protected boolean borde;
	public ConseguirLinea(int peso, int numLinea, boolean borde) {
		super(peso);
		this.numLinea = numLinea;
		this.borde = borde;
	}

	@Override
	public int evaluar(int[][] tablero, Jugada pos) {
		// TODO Auto-generated method stub
		return 0;
	}

}
