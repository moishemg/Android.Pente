package com.mochi.pente.entity;

public class Partida {
	final int MAX_FILAS = 19;
	final int MAX_COLUMNAS = 19;
	final int VACIO = 0;
	final int FICHA_ROJO = 1;
	final int FICHA_AMARILLO = 2;
	final int FICHA_AZUL = 3;
	final int FICHA_VERDE = 4;
	
	private boolean fin;
	private boolean finGanador;
	private int robadosJug1;
	private int robadosJug2;
	
	private Jugador jug1;
	private Jugador jug2;
	private Jugador turno;
	
	private int[][] tablero;
	
	public Partida(Jugador jug1,Jugador jug2){
		this.jug1 = jug1;
		this.jug2 = jug2;
		this.iniciar();
	}
	
	private void iniciar(){
		this.tablero = new int[this.MAX_FILAS][this.MAX_COLUMNAS];
		for (int iFila=0;iFila<this.MAX_FILAS;iFila++) {
			for (int iCol=0;iCol<this.MAX_COLUMNAS;iCol++) {
				this.tablero[iFila][iCol] = this.VACIO;
			}
		}
		this.fin = false;
		this.finGanador = false;
		this.robadosJug1 = 0;
		this.robadosJug2 = 0;
		if (((Math.random() * 100) % 2)==0) {
			this.turno = this.jug1;
		} else {
			this.turno = this.jug2;
		}
	}
	
	public void siguienteTurno(){
		if (this.turno.equals(this.jug1)) {
			this.turno = this.jug2;
		} else {
			this.turno = this.jug2;
		}
	}
	
	
}
