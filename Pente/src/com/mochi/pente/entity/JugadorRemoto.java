package com.mochi.pente.entity;

public class JugadorRemoto extends Jugador {
	public boolean esRemoto() {
		return true;
	}
	
	public Jugada siguienteMovimiento(){
		return super.siguienteMovimiento();
	}
}
