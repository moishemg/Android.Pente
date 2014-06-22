package com.mochi.pente.entity;

public class FichaPosicion {
	public int fila;
	public int columna;
	public int color;
	
	public FichaPosicion(int fila, int columna, int color) {
		this.fila = fila;
		this.columna = columna;
		this.color = color;
	}
	
	public boolean equals(Object o) {
		return ((o instanceof FichaPosicion) && ((FichaPosicion)o).fila==(this.fila) && ((FichaPosicion)o).columna==(this.columna));
	}
	
	public boolean equals(Jugada jug) {
		return (jug.fila==(this.fila) && jug.columna==(this.columna));
	}
}
