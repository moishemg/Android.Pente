package com.mochi.pente.entity;

public class Jugador {
	private long id;
	private String nombre;
	private int ficha;
	
	public Jugador(){}
	
	public void setId(long valor) {
		this.id = valor;
	}
	public long getId(){
		return this.id;
	}
	
	public void setNombre(String valor){
		this.nombre = valor;
	}
	public String getNombre(){
		return this.nombre;
	}
	
	public void setFicha(int valor){
		this.ficha = valor;
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
}
