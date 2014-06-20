package com.mochi.pente.entity;

public class Jugador {
	private long id;
	private String nombre;
	private int ficha;
	
	public Jugador(long id,String nombre,int ficha){
		this.id = id;
		this.ficha = ficha;
		this.nombre = nombre;
	}
	
	public long getId(){
		return this.id;
	}
	
	public String getNombre(){
		return this.nombre;
	}
	
	public int getFicha(){
		return this.ficha;
	}
	
	public boolean equals(Object o) {
		return (o instanceof Jugador) && ((Jugador)o).getNombre().equals(this.getNombre());
	}
	
	public Jugada siguienteMovimiento() {
		// se debe coger la jugada de la pantalla
		return new Jugada();
	}
	
	public String toString() {
		return this.getNombre();
	}
	
}
