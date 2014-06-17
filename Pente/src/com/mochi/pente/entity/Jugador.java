package com.mochi.pente.entity;

public class Jugador {
	private String nombre;
	
	public Jugador(){}
	
	public void setNombre(String valor){
		this.nombre = valor;
	}
	public String getNombre(){
		return this.nombre;
	}
	
	public boolean equals(Object o) {
		return (o instanceof Jugador) && ((Jugador)o).getNombre().equals(this.getNombre());
	}
}
