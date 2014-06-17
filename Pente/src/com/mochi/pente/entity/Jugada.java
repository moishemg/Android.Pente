package com.mochi.pente.entity;

public class Jugada {
	public int fila;
	public int columna;
	
	public Jugada() { 
		this(0,0);
	}
	
	public Jugada(int fila,int columna){
		this.fila = fila;
		this.columna = columna;
	}
}
