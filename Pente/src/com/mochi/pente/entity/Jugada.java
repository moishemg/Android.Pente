package com.mochi.pente.entity;

public class Jugada {
	public int fila;
	public int columna;
	
	public Jugada() { 
		this(-1,-1);
	}
	
	public Jugada(int fila,int columna){
		this.fila = fila;
		this.columna = columna;
	}
	
	public boolean valida(){
		return (this.fila>=0 && this.fila<Partida.MAX_FILAS && this.columna>=0 && this.columna<Partida.MAX_COLUMNAS);
	}
	
	public boolean equals(Object o){
		return ((o instanceof Jugada) && ((Jugada)o).fila==this.fila && ((Jugada)o).columna==this.columna);
	}
	
	public String toString(){
		return "["+this.fila+"-"+this.columna+"]";
	}
}
