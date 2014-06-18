package com.mochi.pente.entity;

import com.mochi.pente.comunicacion.Conexion;

public class JugadorRemoto extends Jugador {
	public Jugada siguienteMovimiento(){
		// se debe coger la jugada desde la base de datos
		Conexion conn = new Conexion();
		return conn.obtenerJugada(this);
	}
}
